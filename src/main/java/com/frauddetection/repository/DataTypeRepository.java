package com.frauddetection.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.frauddetection.model.DataType;

public interface DataTypeRepository extends JpaRepository<DataType, Long> {

}
