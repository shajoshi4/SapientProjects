package com.sapient.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Fund_Holding {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "fund_holding_id")
	Integer id;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "fund_id")
	Fund fund;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "holding_id")
	Holding holding;

	@Column
	Integer quantity;

	public Fund_Holding() {
		super();
	}

	public Fund_Holding(Fund fund, Holding holding, Integer quantity) {
		super();
		this.fund = fund;
		this.holding = holding;
		this.quantity = quantity;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Fund getFund() {
		return fund;
	}

	public void setFund(Fund fund) {
		this.fund = fund;
	}

	public Holding getHolding() {
		return holding;
	}

	public void setHolding(Holding holding) {
		this.holding = holding;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Fund_Holding [id=" + id + ", fund=" + fund + ", holding=" + holding + ", quantity=" + quantity + "]";
	}

}
