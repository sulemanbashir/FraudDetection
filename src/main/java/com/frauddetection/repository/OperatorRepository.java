package com.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frauddetection.model.Operator;


public interface OperatorRepository extends JpaRepository<Operator, Long> {

}
