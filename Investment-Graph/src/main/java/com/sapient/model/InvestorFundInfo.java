package com.sapient.model;

public class InvestorFundInfo {
	private String investorName;
	private String fundName;
	
	public InvestorFundInfo(String investorName, String fundName) {
		super();
		this.investorName = investorName;
		this.fundName = fundName;
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

	@Override
	public String toString() {
		return "InvestorFundInfo [investorName=" + investorName + ", fundName=" + fundName + "]";
	}
	
}
