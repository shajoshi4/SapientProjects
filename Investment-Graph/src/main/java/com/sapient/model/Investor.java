package com.sapient.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@JsonIdentityInfo(
		generator = ObjectIdGenerators.PropertyGenerator.class,
		property = "id")
@Entity
public class Investor{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Column
	private String name ;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "investor_fund", 
	joinColumns = {@JoinColumn(referencedColumnName = "id")},
	inverseJoinColumns = {@JoinColumn(referencedColumnName = "id")})
	private List<Fund> funds ;
	
	@Column
	private Integer level;

	public Investor() {
		super();
	}

	public Investor(String name, List<Fund> funds, Integer level) {
		super();
		this.name = name;
		this.funds = funds;
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

	public List<Fund> getFunds() {
		return funds;
	}

	public void setFunds(List<Fund> funds) {
		this.funds = funds;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Override
	public String toString() {
		return "Investor [id=" + id + ", name=" + name + ", level=" + level + "]";
	}
	
	
}
