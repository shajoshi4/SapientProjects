package com.sapient.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sapient.model.Investor;

@Repository
public interface InvestorRepo extends JpaRepository<Investor, Integer>{

	Investor findFirstByName(String name);
}
