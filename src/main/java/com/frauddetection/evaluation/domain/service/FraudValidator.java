package com.frauddetection.evaluation.domain.service;

import com.frauddetection.evaluation.domain.model.FraudRule;
import com.frauddetection.evaluation.domain.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class FraudValidator {
    private static final Logger log = LogManager.getLogger(FraudValidator.class);

    public boolean shouldEvaluate(FraudRule rule, Transaction transaction) {

        log.info("Validating terminal threat score for rule [{}]", rule.name());

        boolean ruleHasThreshold = rule.terminalThreatThreshold() != null;
        boolean requestHasScore = transaction.terminalThreatScore() != null
                && !transaction.terminalThreatScore().isBlank();

        if (!ruleHasThreshold || !requestHasScore) {
            log.info("Skipping threshold check: rule or request missing threat score");
            return true;
        }

        int requestScore = Integer.parseInt(transaction.terminalThreatScore());
        if (requestScore >= rule.terminalThreatThreshold()) {
            log.info("Threshold exceeded: {} >= {}", requestScore, rule.terminalThreatThreshold());
            return true;
        }

        log.info("Threshold not met: {} < {}, skipping rule [{}]",
                requestScore, rule.terminalThreatThreshold(), rule.name());
        return false;
    }
}
