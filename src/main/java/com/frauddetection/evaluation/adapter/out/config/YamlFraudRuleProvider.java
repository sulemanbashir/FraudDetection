package com.frauddetection.evaluation.adapter.out.config;

import java.util.List;

import com.frauddetection.evaluation.domain.model.FraudRule;
import com.frauddetection.evaluation.port.out.FraudRuleProvider;
import com.frauddetection.shared.enums.EntityType;
import com.frauddetection.shared.enums.OperatorType;
import org.springframework.stereotype.Component;

@Component
public class YamlFraudRuleProvider implements FraudRuleProvider {

    private final FraudDetectionProperties properties;

    public YamlFraudRuleProvider(FraudDetectionProperties properties) {
        this.properties = properties;
    }

    @Override
    public List<FraudRule> findRulesByMerchant(String merchant) {
        return properties.getRules().stream()
                .filter(r -> r.getMerchant().equalsIgnoreCase(merchant))
                .map(this::toDomainRule)
                .toList();
    }

    private FraudRule toDomainRule(FraudRuleProperties props) {
        return new FraudRule(
                props.getName(),
                props.getMerchant(),
                props.getFraudType(),
                EntityType.fromValue(props.getEntityType()),
                OperatorType.fromDatabaseValue(props.getOperatorType()),
                props.getFraudScore(),
                props.getTerminalThreatThreshold(),
                props.getThresholdValue(),
                props.getThresholdUpperValue(),
                props.getExchangeCode(),
                props.getToCurrencyCode());
    }
}
