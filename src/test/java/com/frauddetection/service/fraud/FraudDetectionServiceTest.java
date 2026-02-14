package com.frauddetection.service.fraud;

import java.text.ParseException;
import java.util.Collections;
import java.util.List;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.exception.EntityPreprocessingException;
import com.frauddetection.exception.InvalidInfoException;
import com.frauddetection.model.Entities;
import com.frauddetection.model.FraudParameterApi;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.model.FraudType;
import com.frauddetection.repository.FraudParameterDetailsRepository;
import com.frauddetection.service.preprocessing.EntityPreprocessor;
import com.frauddetection.service.preprocessing.EntityPreprocessorService;
import com.frauddetection.validation.FraudValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FraudDetectionServiceTest {

    @Mock
    private FraudParameterDetailsRepository fraudParameterDetailsRepository;

    @Mock
    private FraudValidator fraudValidator;

    @Mock
    private FraudScoreEvaluatorService fraudScoreEvaluatorService;

    @Mock
    private EntityPreprocessorService entityPreprocessorService;

    @Mock
    private FraudRuleEvaluatorService fraudRuleEvaluatorService;

    @InjectMocks
    private FraudDetectionService fraudDetectionService;

    private TransactionInfo transactionInfo;

    @BeforeEach
    void setUp() {
        transactionInfo = new TransactionInfo(600.0, "EUR", "123", "KFC", "80", null);
    }

    @Test
    void evaluateFraud_withNoRules_returnsLowScore() throws ParseException, InvalidInfoException, EntityPreprocessingException {
        when(fraudParameterDetailsRepository.findByMerchantName("KFC"))
                .thenReturn(Collections.emptyList());

        ResponseInfo expectedResponse = new ResponseInfo("L", "Low", "0.0", null);
        when(fraudScoreEvaluatorService.evaluateFraudScore(0.0))
                .thenReturn(expectedResponse);

        ResponseInfo result = fraudDetectionService.evaluateFraud(transactionInfo);

        Assertions.assertEquals("L", result.status());
        Assertions.assertEquals("Low", result.message());
        Assertions.assertEquals("0.0", result.fraudScore());
        verify(fraudValidator, never()).validateFraudScore(any(), any());
    }

    @Test
    void evaluateFraud_withMatchingRule_returnsCorrectScore() throws ParseException, InvalidInfoException, EntityPreprocessingException {
        FraudParameterDetails rule = buildFraudRule();

        when(fraudParameterDetailsRepository.findByMerchantName("KFC"))
                .thenReturn(List.of(rule));
        when(fraudValidator.validateFraudScore(rule, transactionInfo))
                .thenReturn(true);

        EntityPreprocessor preprocessor = mock(EntityPreprocessor.class);
        when(entityPreprocessorService.getPreprocessorByDatabaseValue("amount"))
                .thenReturn(preprocessor);

        FraudRuleEvaluator evaluator = mock(FraudRuleEvaluator.class);
        when(fraudRuleEvaluatorService.getEvaluatorByFraudTypeCode("OPR"))
                .thenReturn(evaluator);

        ResponseInfo evaluatorResponse = new ResponseInfo(null, null, "40", null);
        when(evaluator.applyFraudRules(transactionInfo, rule))
                .thenReturn(evaluatorResponse);

        ResponseInfo expectedResponse = new ResponseInfo("M", "Medium", "40.0", null);
        when(fraudScoreEvaluatorService.evaluateFraudScore(40.0))
                .thenReturn(expectedResponse);

        ResponseInfo result = fraudDetectionService.evaluateFraud(transactionInfo);

        Assertions.assertEquals("M", result.status());
        Assertions.assertEquals("Medium", result.message());
        Assertions.assertEquals("40.0", result.fraudScore());
        verify(preprocessor).normalizeEntity(transactionInfo, rule);
    }

    @Test
    void evaluateFraud_whenThresholdNotMet_skipsRule() throws ParseException, InvalidInfoException, EntityPreprocessingException {
        FraudParameterDetails rule = buildFraudRule();

        when(fraudParameterDetailsRepository.findByMerchantName("KFC"))
                .thenReturn(List.of(rule));
        when(fraudValidator.validateFraudScore(rule, transactionInfo))
                .thenReturn(false);

        ResponseInfo expectedResponse = new ResponseInfo("L", "Low", "0.0", null);
        when(fraudScoreEvaluatorService.evaluateFraudScore(0.0))
                .thenReturn(expectedResponse);

        ResponseInfo result = fraudDetectionService.evaluateFraud(transactionInfo);

        Assertions.assertEquals("L", result.status());
        verify(fraudRuleEvaluatorService, never()).getEvaluatorByFraudTypeCode(anyString());
        verify(entityPreprocessorService, never()).getPreprocessorByDatabaseValue(anyString());
    }

    private FraudParameterDetails buildFraudRule() {
        Entities entity = new Entities();
        entity.setId(1L);
        entity.setName("amount");

        FraudType fraudType = new FraudType();
        fraudType.setTypeCode("OPR");

        FraudParameterApi fraudParameterApi = new FraudParameterApi();
        fraudParameterApi.setTypeCode(fraudType);

        FraudParameterDetails rule = new FraudParameterDetails();
        rule.setId(1L);
        rule.setFraudRuleName("amount-check");
        rule.setEntity(entity);
        rule.setFraudParameterApi(fraudParameterApi);
        rule.setFraudScore(40.0);

        return rule;
    }
}
