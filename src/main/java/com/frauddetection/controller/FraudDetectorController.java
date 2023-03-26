package com.frauddetection.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.rules.operational.exception.InvalidInfoException;
import com.frauddetection.service.FraudDetectionService;

@RestController
@RequestMapping("/")
public class FraudDetectorController {
	
	@Autowired
	private FraudDetectionService fraudDetectionService;
	
	
	@PostMapping("evaluateFraud")
	@ResponseBody
	public ResponseInfo evaluateFraud(@RequestBody TransactionInfo transactionInfo) throws ParseException, InvalidInfoException {
		return fraudDetectionService.evaluateFraud(transactionInfo);
	}
	

}
