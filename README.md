# Fraud Detection Service

A rule-based fraud detection system built with Spring Boot. Transactions come in, get evaluated against configurable per-merchant fraud rules, and receive a risk score (Low / Medium / High).

The system uses the Strategy pattern for operators, preprocessors, and evaluators, making it straightforward to add new fraud rule types without touching existing code.

## Tech Stack

- Java 21 (LTS)
- Spring Boot 3.4.4
- H2 In-Memory Database
- Liquibase (database migrations)
- Spring Data JPA / Hibernate
- Log4j2
- Springdoc OpenAPI (Swagger UI)
- Maven

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────────────┐
│                         REST Controllers                            │
│  FraudDetectorController          OperationalRuleController         │
└──────────┬──────────────────────────────────┬───────────────────────┘
           │                                  │
           ▼                                  ▼
┌─────────────────────┐           ┌───────────────────────┐
│ FraudDetectionService│           │ OperationalRuleService │
│  (orchestrator)      │           │  (CRUD for rules)      │
└──────┬───────────────┘           └───────────────────────┘
       │
       ├──► FraudValidator (threshold gating)
       │
       ├──► EntityPreprocessorService ──► AmountPreprocessor (currency conversion)
       │                               └► DefaultPreprocessor (reflection-based)
       │
       ├──► FraudRuleEvaluatorService ──► OperationalFraudEvaluatorImpl
       │                               └► (future: Behavioral, Velocity, Geo)
       │
       ├──► OperatorDatatypeService ──► GreaterThanOperatorDouble
       │                             ├► EqualOperatorString
       │                             ├► InOperatorString
       │                             ├► NotInOperatorString
       │                             └► LikeOperatorString
       │
       └──► FraudScoreEvaluatorService (score → risk level mapping)
```

## Fraud Evaluation Flow

This is the core flow. A transaction comes in, gets evaluated against all matching rules for that merchant, and returns a risk assessment.

```mermaid
sequenceDiagram
    participant Client
    participant Controller as FraudDetectorController
    participant Service as FraudDetectionService
    participant Repo as FraudParamDetailsRepository
    participant Validator as FraudValidator
    participant Preprocessor as EntityPreprocessorService
    participant Evaluator as FraudRuleEvaluatorService
    participant Operator as OperatorDatatypeService
    participant ScoreService as FraudScoreEvaluatorService

    Client->>Controller: POST /evaluateFraud
    Controller->>Service: evaluateFraud(TransactionInfo)

    Service->>Repo: findByMerchantName("KFC")
    Repo-->>Service: List<FraudParameterDetails>

    loop For each fraud rule
        Service->>Validator: validate terminal threat score >= rule threshold
        alt Threshold not met
            Validator-->>Service: skip rule
        else Threshold met
            Service->>Preprocessor: preprocess entity value
            Note over Preprocessor: Amount → currency conversion<br/>Others → reflection-based extraction
            Preprocessor-->>Service: actualCriteriaValue

            Service->>Evaluator: evaluate(rule, actualValue)
            Evaluator->>Operator: compare(actualValue, ruleValue)
            Note over Operator: e.g. GreaterThan + Double<br/>or NotIn + String
            Operator-->>Evaluator: match = true/false
            Evaluator-->>Service: fraudScore (or 0 if no match)
        end
    end

    Service->>Service: aggregate scores (sum)
    Service->>ScoreService: evaluate(totalScore)
    Note over ScoreService: 0-39 → Low<br/>40-59 → Medium<br/>60-100 → High
    ScoreService-->>Service: ResponseInfo(status, message, score)

    Service-->>Controller: ResponseInfo
    Controller-->>Client: 200 OK + JSON response
```

## Rule Management Flow

Fraud rules are stored per merchant and can be managed through the operational rules API.

```mermaid
sequenceDiagram
    participant Client
    participant Controller as OperationalRuleController
    participant Validator as Spring Validator
    participant Service as OperationalRuleService
    participant Repo as FraudParamDetailsRepository
    participant DB as H2 Database

    Client->>Controller: POST /operationalrules/addFraudRule
    Controller->>Validator: validate request body
    alt Validation fails
        Validator-->>Controller: ConstraintViolationException
        Controller-->>Client: 400 Bad Request + details
    else Validation passes
        Controller->>Service: addFraudRule(request)
        Service->>Repo: save(FraudParameterDetails)
        Repo->>DB: INSERT
        DB-->>Repo: saved entity
        Repo-->>Service: FraudParameterDetails
        Service-->>Controller: success response
        Controller-->>Client: 200 OK
    end
```

## Database Schema

```mermaid
erDiagram
    MERCHANT {
        long id PK
        string name
    }
    FRAUD_PARAM_DETAILS {
        long id PK
        long merchant_id FK
        long fraud_param_api_id FK
        long entity_id FK
        long operator_id FK
        string fraud_rule_name
        double fraud_score
        double terminal_threat_threshold
        string fraud_criteria_value1
        string exchange_code
        string to_curr_code
    }
    FRAUD_PARAM_API {
        long id PK
        long fraud_type_id FK
        string api_url
    }
    FRAUD_TYPE {
        long id PK
        string code
        string name
    }
    FRAUD_ENTITIES {
        long id PK
        string name
        long datatype_id FK
    }
    OPERATOR {
        long id PK
        string name
        string code
    }
    DATATYPES {
        long id PK
        string name
    }
    EXCHANGE_RATES {
        long id PK
        string from_curr
        string to_curr
        double rate
    }
    FRAUD_SCORE_LEVEL {
        long id PK
        string status
        string message
        double min_score
        double max_score
    }

    MERCHANT ||--o{ FRAUD_PARAM_DETAILS : "has rules"
    FRAUD_PARAM_API ||--o{ FRAUD_PARAM_DETAILS : "defines type"
    FRAUD_TYPE ||--o{ FRAUD_PARAM_API : "categorizes"
    FRAUD_ENTITIES ||--o{ FRAUD_PARAM_DETAILS : "target field"
    OPERATOR ||--o{ FRAUD_PARAM_DETAILS : "comparison"
    DATATYPES ||--o{ FRAUD_ENTITIES : "typed as"
```

## Project Structure

```
src/main/java/com/frauddetection/
├── config/                     # Spring configs, registry beans
├── controller/                 # REST endpoints
├── dto/                        # Request/response DTOs (Java records)
├── enums/                      # Type-safe enums (FraudTypeCode, EntityType, OperatorType, DataTypeEnum)
├── exception/                  # Custom exception handling
├── model/                      # JPA entities
├── repository/                 # Spring Data JPA repositories
├── service/
│   ├── fraud/                  # Core fraud detection orchestration
│   │   └── operational/        # Operational fraud evaluator
│   ├── operator/               # Operator strategy implementations
│   │   └── impl/               # GreaterThan, Equal, In, NotIn, Like
│   └── preprocessing/          # Entity value preprocessors
├── utils/                      # Utility classes
└── validation/                 # Custom validators
```

## Design Patterns

- **Strategy**: Each operator (GreaterThan, In, NotIn, etc.) and preprocessor (Amount, Default) is a separate implementation behind a common interface. New ones can be added without modifying existing code.
- **Registry**: Spring-managed maps wire `(OperatorType, DataType)` pairs to their implementations at startup. Same approach for fraud evaluators and entity preprocessors.
- **Template Method**: `BaseEntity` provides common audit fields (created_at, updated_at) across all entities.

## Getting Started

```bash
# clone the repo
git clone <repo-url>
cd FraudDetection

# build
mvn clean install

# run
mvn spring-boot:run
```

The app starts on `http://localhost:8080`. Swagger UI is at `http://localhost:8080/swagger-ui/index.html`. H2 console is at `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:frauddetection`).

Liquibase creates the schema and seeds it with sample merchants, operators, and two fraud rules for the "KFC" merchant.

## API Endpoints

### Evaluate Fraud

`POST /evaluateFraud`

Evaluates a transaction against all fraud rules configured for the given merchant.

Request:
```json
{
  "amount": 600,
  "currency": "EUR",
  "terminalId": "123",
  "merchant": "KFC",
  "terminalThreatScore": "80"
}
```

Response (`200 OK`):
```json
{
  "status": "H",
  "message": "High",
  "fraudScore": "75.0"
}
```

### Add Fraud Rule

`POST /operationalrules/addFraudRule`

```json
{
  "merchant": { "id": 1, "name": "KFC" },
  "fraudParameterApi": { "id": 1 },
  "entity": { "id": 1 },
  "operator": { "id": 3 },
  "fraudRuleName": "MaxTransAmount",
  "fraudScore": 33,
  "terminalThreadThreshold": 45,
  "fraudCriteriaValue1": "30",
  "exchangeCode": "ABC",
  "toCurrCode": "USD"
}
```

Responses: `200 OK` on success, `400 Bad Request` with validation details on failure.

### Update Fraud Rule

`POST /operationalrules/updateFraudRule`

```json
{
  "merchant": { "id": 1, "name": "KFC" },
  "fraudRuleName": "MaxTransAmount",
  "fraudScore": 33
}
```

### Delete Fraud Rule

`POST /operationalrules/deleteFraudRule`

```json
{
  "merchant": { "id": 1, "name": "KFC" },
  "fraudRuleName": "MaxTransAmount"
}
```

## Sample Rules (Seeded)

| Rule | Entity | Operator | Criteria | Score | Threshold |
|------|--------|----------|----------|-------|-----------|
| MaxTransAmount | amount (Double) | Greater Than | 500 USD | 40 | 61 |
| ValidTerminalIds | terminalId (String) | Not In | 123,456,333 | 50 | 50 |

## Score Levels

| Status | Level | Score Range |
|--------|-------|-------------|
| L | Low | 0 - 39 |
| M | Medium | 40 - 59 |
| H | High | 60 - 100 |

## Extending the System

**Add a new operator**: Implement the `OperatorDatatype` interface, then register the `(OperatorType, DataTypeEnum)` pair in `OperatorDatatypeRegistry`.

**Add a new fraud type** (e.g., Velocity): Implement `FraudRuleEvaluator`, register it in `FraudRuleEvaluatorRegistry` against the new `FraudTypeCode`.

**Add a new entity preprocessor**: Implement `EntityPreprocessor`, register it in `EntityPreprocessorRegistry` for the target `EntityType`.
