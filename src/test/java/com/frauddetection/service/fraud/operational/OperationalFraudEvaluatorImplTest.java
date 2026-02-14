package com.frauddetection.service.fraud.operational;

import java.text.ParseException;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.model.DataType;
import com.frauddetection.model.Entities;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.model.Operator;
import com.frauddetection.service.fraud.FraudScoreEvaluatorService;
import com.frauddetection.service.operator.OperatorDatatype;
import com.frauddetection.service.operator.OperatorDatatypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OperationalFraudEvaluatorImplTest {

    @Mock
    private FraudScoreEvaluatorService fraudScoreEvaluatorService;

    @Mock
    private OperatorDatatypeService operatorDatatypeService;

    @InjectMocks
    private OperationalFraudEvaluatorImpl operationalFraudEvaluator;

    private TransactionInfo transactionInfo;
    private FraudParameterDetails fraudRule;
    private OperatorDatatype operatorDatatype;

    @BeforeEach
    void setUp() {
        transactionInfo = new TransactionInfo(600.0, "EUR", null, "KFC", null, null);

        Operator operator = new Operator();
        operator.setId(1L);
        operator.setName("EQUAL");

        DataType dataType = new DataType();
        dataType.setId(1L);
        dataType.setName("STRING");

        Entities entity = new Entities();
        entity.setId(1L);
        entity.setName("city");
        entity.setDatatype(dataType);

        fraudRule = new FraudParameterDetails();
        fraudRule.setId(1L);
        fraudRule.setFraudRuleName("city-check");
        fraudRule.setOperator(operator);
        fraudRule.setEntity(entity);
        fraudRule.setActualCriteriaValue("Copenhagen");
        fraudRule.setFraudCriteriaValue1("Copenhagen");
        fraudRule.setFraudScore(30.0);

        operatorDatatype = mock(OperatorDatatype.class);
    }

    @Test
    void applyFraudRules_whenRuleHit_returnsScore() throws ParseException, InvalidInfoException {
        when(operatorDatatypeService.getOperatorByDatabaseValues("EQUAL", "STRING"))
                .thenReturn(operatorDatatype);
        when(operatorDatatype.applyOperation("Copenhagen", "Copenhagen"))
                .thenReturn(true);

        ResponseInfo expectedResponse = new ResponseInfo("L", "Low", "30.0", null);
        when(fraudScoreEvaluatorService.evaluateFraudScore(30.0))
                .thenReturn(expectedResponse);

        ResponseInfo result = operationalFraudEvaluator.applyFraudRules(transactionInfo, fraudRule);

        Assertions.assertEquals("L", result.status());
        Assertions.assertEquals("Low", result.message());
        Assertions.assertEquals("30.0", result.fraudScore());
        verify(fraudScoreEvaluatorService).evaluateFraudScore(30.0);
    }

    @Test
    void applyFraudRules_whenRuleMiss_returnsZeroScore() throws ParseException, InvalidInfoException {
        when(operatorDatatypeService.getOperatorByDatabaseValues("EQUAL", "STRING"))
                .thenReturn(operatorDatatype);
        when(operatorDatatype.applyOperation("Copenhagen", "Copenhagen"))
                .thenReturn(false);

        ResponseInfo result = operationalFraudEvaluator.applyFraudRules(transactionInfo, fraudRule);

        Assertions.assertEquals("0", result.fraudScore());
        verify(fraudScoreEvaluatorService, never()).evaluateFraudScore(30.0);
    }
}
