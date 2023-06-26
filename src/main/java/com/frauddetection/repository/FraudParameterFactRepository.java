package com.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frauddetection.model.FraudParameterFact;

public interface FraudParameterFactRepository extends JpaRepository<FraudParameterFact, Long> {

}
