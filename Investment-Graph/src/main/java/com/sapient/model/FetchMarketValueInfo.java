package com.sapient.model;

import java.util.List;

public class FetchMarketValueInfo {

	String investorName;
	String fundName;
	List<String> excludedHolding;

	public FetchMarketValueInfo(String investorName, String fundName, List<String> excludedHolding) {
		super();
		this.investorName = investorName;
		this.fundName = fundName;
		this.excludedHolding = excludedHolding;
	}

	public String getInvestorName() {
		return investorName;
	}

	public void setInvestorName(String investorName) {
		this.investorName = investorName;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public List<String> getExcludedHolding() {
		return excludedHolding;
	}

	public void setExcludedHolding(List<String> excludedHolding) {
		this.excludedHolding = excludedHolding;
	}

	@Override
	public String toString() {
		return "FetchMarketValueInfo [investorName=" + investorName + ", fundName=" + fundName + ", excludedHolding="
				+ excludedHolding + "]";
	}

}
