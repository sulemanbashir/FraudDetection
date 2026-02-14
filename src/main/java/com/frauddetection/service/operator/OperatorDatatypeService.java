package com.frauddetection.service.operator;

import com.frauddetection.enums.DataTypeEnum;
import com.frauddetection.enums.OperatorType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OperatorDatatypeService {
    private static final Logger log = LogManager.getLogger(OperatorDatatypeService.class);

    private final Map<OperatorDataTypeKey, OperatorDatatype> operatorRegistry;

    public OperatorDatatypeService(
            @Qualifier("operatorDatatypeMap") Map<OperatorDataTypeKey, OperatorDatatype> operatorRegistry) {
        this.operatorRegistry = operatorRegistry;
        log.info("OperatorDatatypeService initialized with {} registered operators", operatorRegistry.size());
    }

    public OperatorDatatype getOperator(OperatorType operatorType, DataTypeEnum dataType) {
        OperatorDataTypeKey key = new OperatorDataTypeKey(operatorType, dataType);
        OperatorDatatype operator = operatorRegistry.get(key);

        if (operator == null) {
            log.error("No operator found for operatorType={} and dataType={}", operatorType, dataType);
            throw new IllegalArgumentException(
                    String.format("No operator registered for operatorType=%s and dataType=%s",
                            operatorType, dataType)
            );
        }

        log.debug("Retrieved operator for {}+{}: {}",
                operatorType, dataType, operator.getClass().getSimpleName());
        return operator;
    }

    public OperatorDatatype getOperatorByDatabaseValues(String operatorName, String dataTypeName) {
        OperatorDataTypeKey key = OperatorDataTypeKey.fromDatabaseValues(operatorName, dataTypeName);
        OperatorDatatype operator = operatorRegistry.get(key);

        if (operator == null) {
            log.error("No operator found for operatorName='{}' and dataTypeName='{}'",
                    operatorName, dataTypeName);
            throw new IllegalArgumentException(
                    String.format("No operator registered for operatorName='%s' and dataTypeName='%s'",
                            operatorName, dataTypeName)
            );
        }

        log.debug("Retrieved operator for '{}'+'{}':{}", operatorName, dataTypeName, operator.getClass().getSimpleName());
        return operator;
    }
}
