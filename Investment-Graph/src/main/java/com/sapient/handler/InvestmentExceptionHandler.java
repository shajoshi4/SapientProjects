package com.sapient.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class InvestmentExceptionHandler {

	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	public InvestmentExceptionHandler() {
	}

	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<Object> throwInvestorNotFoundException(NotFoundException exception) {
		logger.error(exception.getMessage());
		return new ResponseEntity<Object>(exception.getMessage(), HttpStatus.NOT_FOUND);
	}

}
