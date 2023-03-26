# Fraud Detection Service

## Tools and Technology

- SpringBoot
- H2 In-Memory Database
- LiquiBase
- Log4j2
- Postman

## API Endpoints

### Evaluate Fraud

**[POST]** [http://localhost:8080/evaluateFraud]

Evaluate Fraud on Requested Transaction by Applying Fraud Rules

- Request must use ``content-type: application/json``
- The [POST] request example is mentioned below:
  ~~~json
    {
      "amount":600,
      "currency":"EUR",
      "terminalId" : "123",
      "merchand" : "KFC",
      "terminalThreatScore" : "80"
    }
    ~~~

**Response**
- ``200 OK``: a JSON response is returned containing fraud evaluation result.
  ~~~json
    {
      "status": "H",
      "message": "High",
      "fraudScore": "75.0"
    }
    ~~~

### Add Operational Fraud Rule

**[POST]** [http://localhost:8080/operationalrules/addFraudRule]

Add Fraud Rules in Database 

- Request must use ``content-type: application/json``
- The [POST] request example is mentioned below:
  ~~~json
    {
        "merchant": {
          "id": 1,
          "name": "KFC"
        },
        "fraudParameterApi": {
          "id": 1
        },
        "entity": {
          "id": 1
        },
        "operator": {
          "id": 3
        },
        "fraudRuleName": "MaxTransAmount",
        "fraudScore": 33,
        "terminalThreadThreshold": 45,
        "fraudCriteriaValue1": "30",
        "exchangeCode": "ABC",
        "toCurrCode": "USD"
    }
    ~~~

**Response**
- ``200 OK``: a JSON response is returned containing success response
- ``400 BAD REQUEST``: : a JSON response is returned containing request violation details

### Update Operational Fraud Rule

**[POST]** [http://localhost:8080/operationalrules/updateFraudRule]

Updare Fraud Rules Details from Database

- Request must use ``content-type: application/json``
- The [POST] request example is mentioned below:
  ~~~json
    {
        "merchant": {
          "id": 1,
          "name": "KFC"
        },
        "fraudRuleName": "MaxTransAmount",
        "fraudScore": 33,
    }
    ~~~

**Response**
- ``200 OK``: a JSON response is returned containing success response
- ``400 BAD REQUEST``: : a JSON response is returned containing request violation details

### Delete Operational Fraud Rule

**[POST]** [http://localhost:8080/operationalrules/deleteFraudRule]

Delete Fraud Rules from Database 

- Request must use ``content-type: application/json``
- The [POST] request example is mentioned below:
  ~~~json
    {
        "merchant": {
          "id": 1,
          "name": "KFC"
        },
        "fraudRuleName": "MaxTransAmount"
    }
    ~~~

**Response**
- ``200 OK``: a JSON response is returned containing success response
- ``400 BAD REQUEST``: : a JSON response is returned containing request violation details
