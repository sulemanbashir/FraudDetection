package com.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frauddetection.model.FraudParameterApi;

public interface FraudParameterApiRepository extends JpaRepository<FraudParameterApi, Long> {

}
