package com.frauddetection.evaluation.adapter.out.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fraud-detection")
public class FraudDetectionProperties {

    private List<FraudRuleProperties> rules;
    private List<ScoreLevel> scoreLevels;
    private List<ExchangeRate> exchangeRates;

    public List<FraudRuleProperties> getRules() {
        return rules;
    }

    public void setRules(List<FraudRuleProperties> rules) {
        this.rules = rules;
    }

    public List<ScoreLevel> getScoreLevels() {
        return scoreLevels;
    }

    public void setScoreLevels(List<ScoreLevel> scoreLevels) {
        this.scoreLevels = scoreLevels;
    }

    public List<ExchangeRate> getExchangeRates() {
        return exchangeRates;
    }

    public void setExchangeRates(List<ExchangeRate> exchangeRates) {
        this.exchangeRates = exchangeRates;
    }

    public record ScoreLevel(String code, String name, double minRange, double maxRange) {}

    public record ExchangeRate(String exchangeCode, String fromCurrency, String toCurrency, double rate) {}
}
