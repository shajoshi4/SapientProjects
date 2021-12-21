package com.sapient;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.sapient.model.FetchMarketValueInfo;
import com.sapient.model.FundHoldingInfo;
import com.sapient.model.Investor;
import com.sapient.model.InvestorFundInfo;
import com.sapient.service.InvestmentGraphApplicationService;
import com.sapient.service.InvestmentService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class InvestmentGraphApplicationTests {

	  @Autowired
	  private RestTemplate restTemplate;
	  
	  @Autowired
	  private Gson gson;
	  
	  @Autowired
	  private InvestmentGraphApplicationService service;
	  
	  @Autowired
	  private  InvestmentService investmentService;
	  
	@BeforeTestExecution
	void before() {
		service.createInvestorfundRelation(restTemplate, "inv1", "fun1");
		service.createInvestorfundRelation(restTemplate, "inv1", "fun2");
		service.createInvestorfundRelation(restTemplate, "inv2", "fun2");
		service.createInvestorfundRelation(restTemplate, "inv3", "fun3");
		service.createInvestorfundRelation(restTemplate, "inv1", "fun3");

		service.createFundHoldingRelation(restTemplate, "fun4", "hol1", 100, 20);
		service.createFundHoldingRelation(restTemplate, "fun4", "hol2", 50, 10);
		service.createFundHoldingRelation(restTemplate, "fun5", "hol1", 20, 20);
		service.createFundHoldingRelation(restTemplate, "fun6", "hol3", 10, 30);
		service.createFundHoldingRelation(restTemplate, "fun4", "hol3", 10, 30);

		service.createInvestorfundRelation(restTemplate, "inv1", "fun4");
		service.createInvestorfundRelation(restTemplate, "inv2", "fun6");
	}
	  
	@Test
	void contextLoads() {
	}

	@Test
	void testGetInvestors() {
		Investor[] response = restTemplate.getForObject("http://localhost:8080/app/investors", Investor[].class);
		assertEquals(3, response.length);
	}
	
	@Test
	void testCreateInvestorFundRelation() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String investorName = "dummy_investor";
		String fundName = "dummy_fund";
		
		InvestorFundInfo investorFundInfo = new InvestorFundInfo(investorName, fundName);
		HttpEntity<String> request = new HttpEntity<String>(gson.toJson(investorFundInfo), headers);

		InvestorFundInfo ifInfo = restTemplate.postForObject("http://localhost:8080/app/investorFundInfo", request,
				InvestorFundInfo.class);
		
		assertEquals(investorName, ifInfo.getInvestorName());
		assertEquals(fundName, ifInfo.getFundName());

	}
	
	@Test
	void testCreateFundHoldingRelation() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		String fundName = "dummy_fundName";
		String holdingName = "dummy_holdingName";
		Integer quantity = 10;
		Integer marketValue = 20;
		
		FundHoldingInfo fundHoldingInfo = new FundHoldingInfo(fundName, holdingName, quantity, marketValue);
		HttpEntity<String> request = new HttpEntity<String>(gson.toJson(fundHoldingInfo), headers);

		FundHoldingInfo fhInfo = restTemplate.postForObject("http://localhost:8080/app/fundHoldingInfo", request,FundHoldingInfo.class);
		
		assertEquals(holdingName, fhInfo.getHoldingName());
		assertEquals(fundName, fhInfo.getFundName());
		assertEquals(quantity, fhInfo.getQuantity());
		assertEquals(marketValue, fhInfo.getMarketValue());
	}
	
	@Test
	void testGetMarketValueFromInvestor() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		List<String> excludedHoldings = new ArrayList<>();
		excludedHoldings.add("hol2");
		excludedHoldings.add("hol1");
		
		String investorName = "inv1";
		FetchMarketValueInfo fetchMarketValueInfo = new FetchMarketValueInfo(investorName, null, excludedHoldings);
		HttpEntity<String> request = new HttpEntity<String>(gson.toJson(fetchMarketValueInfo), headers);

		Integer result = restTemplate.postForObject("http://localhost:8080/app/marketValue", request, Integer.class);
		
		assertEquals(300, result);

	}
	
	@Test
	void testGetMarketValueFromFund() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		List<String> excludedHoldings = new ArrayList<>();
		excludedHoldings.add("hol2");
		excludedHoldings.add("hol1");
		
		String fundName = "fun4";
		FetchMarketValueInfo fetchMarketValueInfo = new FetchMarketValueInfo(null, fundName, excludedHoldings);
		HttpEntity<String> request = new HttpEntity<String>(gson.toJson(fetchMarketValueInfo), headers);

		Integer result = restTemplate.postForObject("http://localhost:8080/app/marketValue", request, Integer.class);
		
		assertEquals(300, result);

	}
	
}
