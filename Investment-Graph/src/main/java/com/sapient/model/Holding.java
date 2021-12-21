package com.sapient.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
@Entity
public class Holding {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column
	private String name;

	@OneToMany(mappedBy = "holding")
	private List<Fund_Holding> funds;

	@Column
	private Integer level;
//
//	@Column
//	private Integer quantity;

	@Column
	private Integer marketValue;

	public Holding() {
		super();
	}

	public Holding(String name, List<Fund_Holding> funds, Integer level, Integer marketValue) {
		super();
		this.name = name;
		this.funds = funds;
		this.level = level;
//		this.quantity = quantity;
		this.marketValue = marketValue;
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

	public List<Fund_Holding> getFunds() {
		return funds;
	}

	public void setFunds(List<Fund_Holding> funds) {
		this.funds = funds;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

//	public Integer getQuantity() {
//		return quantity;
//	}
//
//	public void setQuantity(Integer quantity) {
//		this.quantity = quantity;
//	}

	public Integer getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(Integer marketValue) {
		this.marketValue = marketValue;
	}

	@Override
	public String toString() {
		return "Holding [id=" + id + ", name=" + name + ", level=" + level 
				+ ", marketValue=" + marketValue + "]";
	}

}
