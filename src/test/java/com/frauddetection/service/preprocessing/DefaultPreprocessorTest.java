package com.frauddetection.service.preprocessing;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.exception.EntityPreprocessingException;
import com.frauddetection.model.Entities;
import com.frauddetection.model.FraudParameterDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class DefaultPreprocessorTest {

    @InjectMocks
    private DefaultPreprocessor defaultPreprocessor;

    private TransactionInfo transactionInfo;

    @BeforeEach
    void setUp() {
        transactionInfo = new TransactionInfo(null, "EUR", "123", "KFC", "80", "Copenhagen");
    }

    @Test
    void normalizeEntity_terminalId_setsValue() throws EntityPreprocessingException {
        FraudParameterDetails rule = buildRuleWithEntityName("terminalId");

        defaultPreprocessor.normalizeEntity(transactionInfo, rule);

        Assertions.assertEquals("123", rule.getActualCriteriaValue());
    }

    @Test
    void normalizeEntity_city_setsValue() throws EntityPreprocessingException {
        FraudParameterDetails rule = buildRuleWithEntityName("city");

        defaultPreprocessor.normalizeEntity(transactionInfo, rule);

        Assertions.assertEquals("Copenhagen", rule.getActualCriteriaValue());
    }

    @Test
    void normalizeEntity_unknownField_throwsException() {
        FraudParameterDetails rule = buildRuleWithEntityName("unknownField");

        Assertions.assertThrows(EntityPreprocessingException.class,
                () -> defaultPreprocessor.normalizeEntity(transactionInfo, rule));
    }

    @Test
    void normalizeEntity_nullInput_throwsException() {
        FraudParameterDetails rule = buildRuleWithEntityName("terminalId");

        Assertions.assertThrows(EntityPreprocessingException.class,
                () -> defaultPreprocessor.normalizeEntity(null, rule));
    }

    private FraudParameterDetails buildRuleWithEntityName(String entityName) {
        Entities entity = new Entities();
        entity.setName(entityName);

        FraudParameterDetails rule = new FraudParameterDetails();
        rule.setEntity(entity);
        return rule;
    }
}
