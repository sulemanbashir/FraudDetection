package com.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frauddetection.model.FraudType;

public interface FraudTypeRepository extends JpaRepository<FraudType, Long> {

}
