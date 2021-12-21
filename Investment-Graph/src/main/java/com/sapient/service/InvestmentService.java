package com.sapient.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sapient.handler.NotFoundException;
import com.sapient.model.FetchMarketValueInfo;
import com.sapient.model.Fund;
import com.sapient.model.FundHoldingInfo;
import com.sapient.model.Fund_Holding;
import com.sapient.model.Holding;
import com.sapient.model.Investor;
import com.sapient.model.InvestorFundInfo;
import com.sapient.repository.FundRepo;
import com.sapient.repository.Fund_HoldingRepo;
import com.sapient.repository.HoldingRepo;
import com.sapient.repository.InvestorRepo;
import com.sapient.util.InvestmentType;

@Service
public class InvestmentService {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	InvestorRepo investorRepo;

	@Autowired
	FundRepo fundRepo;

	@Autowired
	HoldingRepo holdingRepo;

	@Autowired
	Fund_HoldingRepo fundHolding;

	public ResponseEntity<List<Investor>> getAllInvestors() {
		
		logger.debug("getAllInvestors");
		List<Investor> list = new ArrayList<Investor>();
		investorRepo.findAll().forEach(list::add);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	public InvestorFundInfo createInvestorfundRelation(InvestorFundInfo investorFundInfo) {
		logger.debug("createInvestorfundRelation : "+ investorFundInfo);

		String investorName = investorFundInfo.getInvestorName();
		String fundName = investorFundInfo.getFundName();

		Investor investor = investorRepo.findFirstByName(investorName);
		Fund fund = fundRepo.findFirstByName(fundName);
		Optional<Fund> optionalFund = Optional.ofNullable(fundRepo.findFirstByName(fundName));
		Optional<Investor> optionalInvestor= Optional.ofNullable(investorRepo.findFirstByName(investorName));

		if (!optionalInvestor.isPresent() && !optionalFund.isPresent()) {
			ArrayList<Investor> investors = new ArrayList<Investor>();
			ArrayList<Fund> funds = new ArrayList<Fund>();

			Investor temp_investor = new Investor(investorName, null, InvestmentType.Investor.ordinal());
			investors.add(temp_investor);

			Fund temp_fund = new Fund(fundName, investors, null, InvestmentType.Fund.ordinal());
			funds.add(temp_fund);

			temp_investor.setFunds(funds);
			temp_fund.setInvestors(investors);

			investorRepo.save(temp_investor);
			fundRepo.save(temp_fund);
		} else if (!optionalInvestor.isPresent()) {
			List<Investor> investors = fund.getInvestors();
			List<Fund> funds = new ArrayList<Fund>();
			funds.add(fund);
			Investor temp_investor = new Investor(investorName, funds, InvestmentType.Investor.ordinal());
			investorRepo.save(temp_investor);
			investors.add(temp_investor);
			fund.setInvestors(investors);
			fundRepo.save(fund);

		} else if (!optionalFund.isPresent()) {
			List<Investor> investors = new ArrayList<Investor>();
			List<Fund> funds = investor.getFunds();
			investors.add(investor);
			Fund temp_fund = new Fund(fundName, investors, null, InvestmentType.Fund.ordinal());
			fundRepo.save(temp_fund);
			funds.add(temp_fund);
			investor.setFunds(funds);
			investorRepo.save(investor);
		} else {
			List<Investor> investors = fund.getInvestors();
			List<Fund> funds = investor.getFunds();

			investors.add(investor);
			funds.add(fund);

			investor.setFunds(funds);
			fund.setInvestors(investors);

			investorRepo.save(investor);
			fundRepo.save(fund);
		}

		return investorFundInfo;
	}

	public FundHoldingInfo createFundHoldingRelation(FundHoldingInfo fundHoldingInfo) {
		logger.debug("createFundHoldingRelation : "+ fundHoldingInfo);

		String fundName = fundHoldingInfo.getFundName();
		String holdingName = fundHoldingInfo.getHoldingName();
		Integer quantity = fundHoldingInfo.getQuantity();
		Integer marketValue = fundHoldingInfo.getMarketValue();

		Fund fund = fundRepo.findFirstByName(fundName);
		Holding holding=holdingRepo.findFirstByName(holdingName);
		
		Optional<Fund> optionalFund = Optional.ofNullable(fundRepo.findFirstByName(fundName));
		Optional<Holding> optionalHolding= Optional.ofNullable(holdingRepo.findFirstByName(holdingName));

		if (!optionalHolding.isPresent() && !optionalFund.isPresent()) {
			Fund temp_fund = new Fund(fundName, null, null, InvestmentType.Fund.ordinal());
			Holding temp_holding = new Holding(holdingName, null, InvestmentType.Holding.ordinal(), marketValue);

			List<Fund_Holding> fund_Holdings = new ArrayList<Fund_Holding>();
			Fund_Holding fh = new Fund_Holding(temp_fund, temp_holding, quantity);
			fund_Holdings.add(fh);

			temp_holding.setFunds(fund_Holdings);
			temp_fund.setHoldings(fund_Holdings);

			fundHolding.save(fh);
			fundRepo.save(temp_fund);
			holdingRepo.save(temp_holding);

		} else if (!optionalFund.isPresent()) {
			Fund temp_fund = new Fund(fundName, null, null, InvestmentType.Fund.ordinal());

			List<Fund_Holding> fund_Holdings = new ArrayList<Fund_Holding>();
			Fund_Holding fh = new Fund_Holding(temp_fund, holding, quantity);
			fund_Holdings.add(fh);
			temp_fund.setHoldings(fund_Holdings);

			List<Fund_Holding> fundHoldingList = holding.getFunds();
			fundHoldingList.add(fh);
			holding.setFunds(fundHoldingList);

			fundHolding.save(fh);
			fundRepo.save(temp_fund);
			holdingRepo.save(holding);

		} else if (!optionalHolding.isPresent()) {
			Holding temp_holding = new Holding(holdingName, null, InvestmentType.Holding.ordinal(), marketValue);

			List<Fund_Holding> fund_Holdings = new ArrayList<Fund_Holding>();
			Fund_Holding fh = new Fund_Holding(fund, temp_holding, quantity);
			fund_Holdings.add(fh);
			temp_holding.setFunds(fund_Holdings);

			List<Fund_Holding> fundHoldingList = fund.getHoldings();
			fundHoldingList.add(fh);
			fund.setHoldings(fund_Holdings);

			fundHolding.save(fh);
			fundRepo.save(fund);
			holdingRepo.save(temp_holding);

		} else {
			List<Fund_Holding> fundHoldingFromFund = fund.getHoldings();
			List<Fund_Holding> fundHoldingFromHolding = holding.getFunds();

			Fund_Holding fh = new Fund_Holding(fund, holding, quantity);

			fundHoldingFromFund.add(fh);
			fundHoldingFromHolding.add(fh);

			holding.setFunds(fundHoldingFromFund);
			fund.setHoldings(fundHoldingFromHolding);

			fundHolding.save(fh);
			holdingRepo.save(holding);
			fundRepo.save(fund);
		}

		return fundHoldingInfo;
	}

	public ResponseEntity<Integer> getMarketValue(FetchMarketValueInfo fetchMarketValueInfo) throws NotFoundException {
		logger.debug("getMarketValue : "+ fetchMarketValueInfo);

		System.out.println(fetchMarketValueInfo);
		String fundName = fetchMarketValueInfo.getFundName();
		String investorName = fetchMarketValueInfo.getInvestorName();
		List<String> excludedHolding = fetchMarketValueInfo.getExcludedHolding();
		Optional<String> optionalFundName = Optional.ofNullable(fetchMarketValueInfo.getFundName());
		Optional<String> optionalInvestorName = Optional.ofNullable(fetchMarketValueInfo.getInvestorName());
		Integer result = 0;

		if (!optionalInvestorName.isPresent() && !optionalFundName.isPresent()) {
			throw new NotFoundException("Investor name and Fund name not found");
		} else if (investorName != null) {
			Investor investor = investorRepo.findFirstByName(investorName);
			List<Fund> funds = investor.getFunds();

			for (Fund f : funds) {
				result = result + getMarketValueByFund(f.getName(), excludedHolding);
			}
		} else {
			result = getMarketValueByFund(fundName, excludedHolding);
		}
		return new ResponseEntity<Integer>(result, HttpStatus.OK);
	}

	public Integer getMarketValueByFund(String fundName, List<String> excludedHolding) {
		logger.debug("getMarketValueByFund : fundName="+fundName+" , excludedHolding = "+ excludedHolding );

		Integer result = 0;
		Fund fund = fundRepo.findFirstByName(fundName);
		HashMap<String, Integer> quantityMap = new HashMap<>();

		for (Fund_Holding f : fund.getHoldings()) {
			quantityMap.put(f.getHolding().getName(), f.getQuantity());
		}

		List<String> validHolding = fund.getHoldings().stream().map(h -> h.getHolding().getName())
				.collect(Collectors.toList());
		if (excludedHolding == null) {
			excludedHolding = new ArrayList<String>();
		}
		List<Holding> holdings = holdingRepo.findHoldings(validHolding, excludedHolding);
		for (Holding h : holdings) {
			result += quantityMap.get(h.getName()) * h.getMarketValue();
		}

		return result;
	}

}
