package com.sapient.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.sapient.model.FetchMarketValueInfo;
import com.sapient.model.Fund;
import com.sapient.model.FundHoldingInfo;
import com.sapient.model.Investor;
import com.sapient.model.InvestorFundInfo;

@Service
public class InvestmentGraphApplicationService {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	Gson gson;

	public void createInvestorfundRelation(RestTemplate restTemplate, String investorName, String fundName) {
		logger.debug("createInvestorfundRelation");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		InvestorFundInfo investorFundInfo = new InvestorFundInfo(investorName, fundName);
		HttpEntity<String> request = new HttpEntity<String>(gson.toJson(investorFundInfo), headers);

		InvestorFundInfo ifInfo = restTemplate.postForObject("http://localhost:8080/app/investorFundInfo", request,
				InvestorFundInfo.class);
		logger.debug("createInvestorfundRelation with investor name ="+ investorName+" and Fund name ="+fundName+"==>"+ifInfo);
	}

	public void createFundHoldingRelation(RestTemplate restTemplate, String fundName, String holdingName,
			Integer quantity, Integer marketValue) {
		logger.debug("createFundHoldingRelation");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		FundHoldingInfo fundHoldingInfo = new FundHoldingInfo(fundName, holdingName, quantity, marketValue);
		HttpEntity<String> request = new HttpEntity<String>(gson.toJson(fundHoldingInfo), headers);

		FundHoldingInfo fhInfo = restTemplate.postForObject("http://localhost:8080/app/fundHoldingInfo", request,
				FundHoldingInfo.class);
		logger.debug("createFundHoldingRelation with holding name ="+ holdingName+" and Fund name ="+fundName+"==>"+fhInfo);
	}

	public void getAllInvestors(RestTemplate restTemplate) {
		logger.debug("getAllInvestors");
		Investor[] response = restTemplate.getForObject("http://localhost:8080/app/investors", Investor[].class);
		List<Investor> investors = Arrays.asList(response);
		for (Investor i : investors) {
			for (Fund f : i.getFunds()) {
				logger.info(i + " " + f + " " + f.getHoldings());
			}
		}
	}

	public void getMarketValue(RestTemplate restTemplate, String investorName, String fundName,
			List<String> excludedHoldings) {
		
		logger.debug("getMarketValue");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		FetchMarketValueInfo f = new FetchMarketValueInfo(investorName, fundName, excludedHoldings);
		HttpEntity<String> request = new HttpEntity<String>(gson.toJson(f), headers);

		Integer result = restTemplate.postForObject("http://localhost:8080/app/marketValue", request, Integer.class);
		logger.debug("getMarketValue with investorName ="+ investorName+" and Fund name ="+fundName+"==>"+result);
	}

	public void getMarketValueFromInvestor(RestTemplate restTemplate, String investorName,
			List<String> excludedHoldings) {
		logger.debug("getMarketValueFromInvestor");
		getMarketValue(restTemplate, investorName, null, excludedHoldings);
	}

	public void getMarketValueFromFund(RestTemplate restTemplate, String fundName, List<String> excludedHoldings) {
		logger.debug("getMarketValueFromFund");
		getMarketValue(restTemplate, null, fundName, excludedHoldings);
	}

}
