package com.frauddetection.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frauddetection.model.FraudParameterDetails;

public interface FraudParameterDetailsRepository extends JpaRepository<FraudParameterDetails, Long> {

    List<FraudParameterDetails> findByMerchantName(String name);

    Optional<FraudParameterDetails> findByMerchantNameAndFraudRuleName(String name, String fname);
}
