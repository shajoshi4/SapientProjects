package com.sapient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapient.model.Fund_Holding;


public interface Fund_HoldingRepo extends JpaRepository<Fund_Holding, Integer> {
}
