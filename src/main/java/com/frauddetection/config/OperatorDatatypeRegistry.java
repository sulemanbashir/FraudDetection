package com.frauddetection.config;

import com.frauddetection.enums.DataTypeEnum;
import com.frauddetection.enums.OperatorType;
import com.frauddetection.service.operator.OperatorDatatype;
import com.frauddetection.service.operator.OperatorDataTypeKey;
import com.frauddetection.service.operator.impl.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
@Log4j2
public class OperatorDatatypeRegistry {

    @Bean(name = "operatorDatatypeMap")
    public Map<OperatorDataTypeKey, OperatorDatatype> operatorDatatypeMap(
            GreaterThanOperatorDouble greaterThanOperatorDouble,
            EqualOperatorString equalOperatorString,
            InOperatorString inOperatorString,
            NotInOperatorString notInOperatorString,
            LikeOperatorString likeOperatorString) {

        log.info("Initializing OperatorDatatype Registry with ENUM-based keys");

        Map<OperatorDataTypeKey, OperatorDatatype> registry = new HashMap<>();

        registry.put(
                new OperatorDataTypeKey(OperatorType.GREATER_THAN, DataTypeEnum.DOUBLE),
                greaterThanOperatorDouble
        );
        log.info("Registered: {} + {} → GreaterThanOperatorDouble",
                OperatorType.GREATER_THAN, DataTypeEnum.DOUBLE);

        registry.put(
                new OperatorDataTypeKey(OperatorType.EQUAL, DataTypeEnum.STRING),
                equalOperatorString
        );
        log.info("Registered: {} + {} → EqualOperatorString",
                OperatorType.EQUAL, DataTypeEnum.STRING);

        registry.put(
                new OperatorDataTypeKey(OperatorType.IN, DataTypeEnum.STRING),
                inOperatorString
        );
        log.info("Registered: {} + {} → InOperatorString",
                OperatorType.IN, DataTypeEnum.STRING);

        registry.put(
                new OperatorDataTypeKey(OperatorType.NOT_IN, DataTypeEnum.STRING),
                notInOperatorString
        );
        log.info("Registered: {} + {} → NotInOperatorString",
                OperatorType.NOT_IN, DataTypeEnum.STRING);

        registry.put(
                new OperatorDataTypeKey(OperatorType.LIKE, DataTypeEnum.STRING),
                likeOperatorString
        );
        log.info("Registered: {} + {} → LikeOperatorString",
                OperatorType.LIKE, DataTypeEnum.STRING);

        log.info("OperatorDatatype Registry initialized successfully with {} operators", registry.size());

        return registry;
    }
}
