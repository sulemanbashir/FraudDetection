package com.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frauddetection.model.FraudScoreLevel;

public interface FraudScoreLevelRepository extends JpaRepository<FraudScoreLevel, Long> {

    public FraudScoreLevel findByScoreLevel(String exhangeCode);
}
