package com.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frauddetection.model.Unit;

public interface UnitRepository extends JpaRepository<Unit, Long> {

}
