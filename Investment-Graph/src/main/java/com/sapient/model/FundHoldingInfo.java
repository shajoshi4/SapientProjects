package com.sapient.model;

public class FundHoldingInfo {

	private String fundName;
	private String holdingName;
	private Integer quantity;
	private Integer marketValue;
	
	public FundHoldingInfo(String fundName, String holdingName, Integer quantity, Integer marketValue) {
		super();
		this.fundName = fundName;
		this.holdingName = holdingName;
		this.quantity = quantity;
		this.marketValue = marketValue;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getHoldingName() {
		return holdingName;
	}

	public void setHoldingName(String holdingName) {
		this.holdingName = holdingName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getMarketValue() {
		return marketValue;
	}

	public void setMarketValue(Integer marketValue) {
		this.marketValue = marketValue;
	}

}
