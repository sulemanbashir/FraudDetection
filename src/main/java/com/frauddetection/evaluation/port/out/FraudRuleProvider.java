package com.frauddetection.evaluation.port.out;

import com.frauddetection.evaluation.domain.model.FraudRule;

import java.util.List;

public interface FraudRuleProvider {

    List<FraudRule> findRulesByMerchant(String merchant);
}
