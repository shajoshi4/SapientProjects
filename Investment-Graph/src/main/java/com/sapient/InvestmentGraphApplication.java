package com.sapient;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.sapient.service.InvestmentGraphApplicationService;

@SpringBootApplication
public class InvestmentGraphApplication {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	InvestmentGraphApplicationService service;

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(InvestmentGraphApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		return args -> {

			logger.debug("Starting CommandLineRunner");

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

			service.getAllInvestors(restTemplate);

			List<String> excludedHoldings = new ArrayList<>();
			excludedHoldings.add("hol2");
			excludedHoldings.add("hol1");
			service.getMarketValueFromInvestor(restTemplate, "inv1", excludedHoldings);

			service.getMarketValueFromFund(restTemplate, "fun4", null);

			service.getMarketValueFromFund(restTemplate, "fun6", excludedHoldings);

			try {
				service.getMarketValueFromInvestor(restTemplate, null, excludedHoldings);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

		};
	}

}
