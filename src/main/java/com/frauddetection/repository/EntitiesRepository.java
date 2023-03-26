package com.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frauddetection.model.Entities;

public interface EntitiesRepository extends JpaRepository<Entities, Long> {

}
