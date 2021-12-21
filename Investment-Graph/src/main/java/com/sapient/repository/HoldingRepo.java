package com.sapient.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sapient.model.Holding;

public interface HoldingRepo extends JpaRepository<Holding, Integer> {

	Holding findFirstByName(String name);
	
	@Query("SELECT h from Holding h where h.name IN (:validHolding) AND h.name NOT IN (:excludedHoldings)")
	List<Holding> findHoldings(@Param("validHolding")List<String> validHolding, @Param("excludedHoldings")List<String> excludedHoldings);

}
