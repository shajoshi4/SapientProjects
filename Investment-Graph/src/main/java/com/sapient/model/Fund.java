package com.sapient.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
@Entity
public class Fund {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private String name ;
	
	@ManyToMany(mappedBy = "funds")
	private List<Investor> investors ;

	@OneToMany(mappedBy = "fund")
	private List<Fund_Holding> holdings ;
	
	@Column
	private Integer level;

	public Fund() {
		super();
	}

	public Fund(String name, List<Investor> investors, List<Fund_Holding> holdings, Integer level) {
		super();
		this.name = name;
		this.investors = investors;
		this.holdings = holdings;
		this.level = level;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Investor> getInvestors() {
		return investors;
	}

	public void setInvestors(List<Investor> investors) {
		this.investors = investors;
	}

	public List<Fund_Holding> getHoldings() {
		return holdings;
	}

	public void setHoldings(List<Fund_Holding> holdings) {
		this.holdings = holdings;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Fund [id=" + id + ", name=" + name + ", level="
				+ level + "]";
	}

}
