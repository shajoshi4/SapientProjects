package com.sapient.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sapient.model.Fund;

public interface FundRepo extends JpaRepository<Fund, Integer> {
	
	Fund findFirstByName(String name);

}
