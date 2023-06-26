package com.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frauddetection.model.Merchant;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

}
