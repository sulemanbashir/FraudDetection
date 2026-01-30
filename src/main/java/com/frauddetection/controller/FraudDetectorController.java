package com.frauddetection.controller;

import java.text.ParseException;

import com.frauddetection.exception.EntityPreprocessingException;
import com.frauddetection.exception.InvalidInfoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.service.fraud.FraudDetectionService;

@RestController
@RequestMapping("/")
public class FraudDetectorController {

    private final FraudDetectionService fraudDetectionService;

    @Autowired
    public FraudDetectorController(FraudDetectionService fraudDetectionService) {
        this.fraudDetectionService = fraudDetectionService;
    }

    @PostMapping("evaluateFraud")
    @ResponseBody
    public ResponseInfo evaluateFraud(@RequestBody TransactionInfo transactionInfo)
            throws ParseException, InvalidInfoException, EntityPreprocessingException {
        return fraudDetectionService.evaluateFraud(transactionInfo);
    }

}
