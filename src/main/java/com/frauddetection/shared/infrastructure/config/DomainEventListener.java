package com.frauddetection.shared.infrastructure.config;

import com.frauddetection.evaluation.domain.event.FraudRuleViolatedEvent;
import com.frauddetection.evaluation.domain.event.TransactionEvaluatedEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DomainEventListener {
    private static final Logger log = LogManager.getLogger(DomainEventListener.class);

    @EventListener
    public void on(TransactionEvaluatedEvent event) {
        log.info("Transaction evaluated for merchant [{}], score [{}], risk [{}]",
                event.getMerchant(), event.getTotalFraudScore(), event.getRiskLevel());
    }

    @EventListener
    public void on(FraudRuleViolatedEvent event) {
        log.info("Fraud rule violated: merchant [{}], rule [{}], score [{}]",
                event.getMerchant(), event.getRuleName(), event.getRuleScore());
    }
}
