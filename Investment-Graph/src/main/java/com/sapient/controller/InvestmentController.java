package com.sapient.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sapient.handler.NotFoundException;
import com.sapient.model.FetchMarketValueInfo;
import com.sapient.model.FundHoldingInfo;
import com.sapient.model.Investor;
import com.sapient.model.InvestorFundInfo;
import com.sapient.service.InvestmentService;

@RestController
@RequestMapping("/app")
public class InvestmentController {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	InvestmentService investmentService;

	@GetMapping("/investors")
	public ResponseEntity<List<Investor>> getAllInvestors() {
		logger.debug("getAllInvestors");
		return investmentService.getAllInvestors();
	}

	@PostMapping(value = "/investorFundInfo", consumes = "application/json", produces = "application/json")
	public InvestorFundInfo createInvestorfundRelation(@RequestBody InvestorFundInfo investorFundInfo) {
		logger.debug("createInvestorfundRelation");
		return investmentService.createInvestorfundRelation(investorFundInfo);
	}

	@PostMapping(value = "/fundHoldingInfo", consumes = "application/json", produces = "application/json")
	public FundHoldingInfo createFundHoldingRelation(@RequestBody FundHoldingInfo fundHoldingInfo) {
		logger.debug("createFundHoldingRelation");
		return investmentService.createFundHoldingRelation(fundHoldingInfo);
	}

	@PostMapping("/marketValue")
	public ResponseEntity<Integer> getMarketValue(@RequestBody FetchMarketValueInfo fetchMarketValueInfo)
			throws NotFoundException {
		logger.debug("getMarketValue");
		return investmentService.getMarketValue(fetchMarketValueInfo);
	}

}
