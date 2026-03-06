package com.frauddetection.evaluation.application;

import java.text.ParseException;
import java.util.List;

import com.frauddetection.evaluation.domain.model.FraudRule;
import com.frauddetection.evaluation.domain.model.ScoreLevel;
import com.frauddetection.evaluation.domain.model.Transaction;
import com.frauddetection.evaluation.domain.aggregate.FraudEvaluation;
import com.frauddetection.evaluation.domain.valueobject.EvaluationResult;
import com.frauddetection.shared.exception.EntityPreprocessingException;
import com.frauddetection.shared.exception.InvalidInfoException;
import com.frauddetection.evaluation.domain.service.FraudRuleEvaluator;
import com.frauddetection.evaluation.domain.service.FraudValidator;
import com.frauddetection.evaluation.domain.service.preprocessing.EntityPreprocessor;
import com.frauddetection.evaluation.domain.service.preprocessing.EntityPreprocessorService;
import com.frauddetection.evaluation.port.in.EvaluateTransactionUseCase;
import com.frauddetection.evaluation.port.out.DomainEventPublisher;
import com.frauddetection.evaluation.port.out.FraudRuleProvider;
import com.frauddetection.evaluation.port.out.ScoreLevelProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class FraudEvaluationService implements EvaluateTransactionUseCase {
    private static final Logger log = LogManager.getLogger(FraudEvaluationService.class);

    private final FraudRuleProvider fraudRuleProvider;
    private final FraudValidator fraudValidator;
    private final EntityPreprocessorService entityPreprocessorService;
    private final FraudRuleEvaluator fraudRuleEvaluator;
    private final ScoreLevelProvider scoreLevelProvider;
    private final DomainEventPublisher domainEventPublisher;

    public FraudEvaluationService(
            FraudRuleProvider fraudRuleProvider,
            FraudValidator fraudValidator,
            EntityPreprocessorService entityPreprocessorService,
            FraudRuleEvaluator fraudRuleEvaluator,
            ScoreLevelProvider scoreLevelProvider,
            DomainEventPublisher domainEventPublisher) {
        this.fraudRuleProvider = fraudRuleProvider;
        this.fraudValidator = fraudValidator;
        this.entityPreprocessorService = entityPreprocessorService;
        this.fraudRuleEvaluator = fraudRuleEvaluator;
        this.scoreLevelProvider = scoreLevelProvider;
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    public EvaluationResult evaluate(Transaction transaction)
            throws ParseException, InvalidInfoException, EntityPreprocessingException {

        FraudEvaluation evaluation = new FraudEvaluation(
                transaction.merchant(),
                transaction.money());

        log.info("Fetching fraud rules for merchant: {}", transaction.merchant());
        List<FraudRule> rules = fraudRuleProvider.findRulesByMerchant(transaction.merchant());
        log.info("Found {} fraud rules for merchant: {}", rules.size(), transaction.merchant());

        for (FraudRule rule : rules) {
            if (fraudValidator.shouldEvaluate(rule, transaction)) {
                log.info("Preprocessing rule: {}", rule.name());

                EntityPreprocessor preprocessor = entityPreprocessorService.getPreprocessor(rule.entityType());
                String actualValue = preprocessor.extractValue(transaction, rule);

                log.info("Evaluating rule: {}", rule.name());
                double score = fraudRuleEvaluator.evaluateRule(transaction, rule, actualValue);
                evaluation.addRuleScore(rule.name(), score);
            }
        }

        List<ScoreLevel> scoreLevels = scoreLevelProvider.getScoreLevels();
        EvaluationResult result = evaluation.evaluateRiskLevel(scoreLevels);

        log.info("Total fraud score: {}, risk level: {}", result.fraudScore(), result.riskLevel());

        evaluation.getDomainEvents().forEach(domainEventPublisher::publish);
        evaluation.clearDomainEvents();

        return result;
    }
}
