package com.frauddetection.rules.operational.service;

import java.util.Optional;

import com.frauddetection.repository.FraudParameterDetailsRepository;
import com.frauddetection.rules.operational.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frauddetection.model.Entities;
import com.frauddetection.model.FraudParameterApi;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.model.Merchant;
import com.frauddetection.model.Operator;
import com.frauddetection.model.Unit;
import com.frauddetection.rules.operational.dto.FraudParameterDetailsDto;

@Service
public class OperationalRuleService {
	
	@Autowired
    FraudParameterDetailsRepository fraudParameterDetailsRepository;
	
	public FraudParameterDetails saveFraudRule(FraudParameterDetailsDto fraudParameterDetailsDto) {
		return fraudParameterDetailsRepository.save(mapFraudParameterDetailsFields(fraudParameterDetailsDto));
	}
	
	public FraudParameterDetails updateFraudRule(FraudParameterDetailsDto fraudParameterDetailsDto) {
		Optional<FraudParameterDetails> fraudParameterDetailsDb = fraudParameterDetailsRepository.findByMerchantNameAndFraudRuleName(fraudParameterDetailsDto.getMerchantName(), fraudParameterDetailsDto.getFraudRuleName());

		if (!fraudParameterDetailsDb.isPresent()) {
			throw new ResourceNotFoundException("merchantName", fraudParameterDetailsDto.getMerchantName());
		}
		
		FraudParameterDetails fraudParameterDetails = mapFraudParameterDetailsFields(fraudParameterDetailsDto);
		return fraudParameterDetailsRepository.save(fraudParameterDetails);
	}
	
	private FraudParameterDetails mapFraudParameterDetailsFields(FraudParameterDetailsDto fraudParameterDetailsDto) {
		FraudParameterDetails fraudParameterDetails = new FraudParameterDetails();
		
		fraudParameterDetails.setMerchant(new Merchant());
		fraudParameterDetails.getMerchant().setName(fraudParameterDetailsDto.getMerchantName());
		fraudParameterDetails.setfraudParameterApi(new FraudParameterApi());
		fraudParameterDetails.getfraudParameterApi().setId(fraudParameterDetailsDto.getFraudParameterApiId());
		fraudParameterDetails.setEntity(new Entities());
		fraudParameterDetails.getEntity().setId(fraudParameterDetailsDto.getEntityId());
		fraudParameterDetails.setOperator(new Operator());
		fraudParameterDetails.getOperator().setId(fraudParameterDetailsDto.getOperatorId());
		fraudParameterDetails.setUnit(new Unit());
		fraudParameterDetails.getUnit().setId(fraudParameterDetailsDto.getUnitId());
		fraudParameterDetails.setFraudScore(fraudParameterDetailsDto.getFraudScore());
		fraudParameterDetails.setTerminalThreadThreshold(fraudParameterDetailsDto.getTerminalThreadThreshold());
		fraudParameterDetails.setFraudCriteriaValue1(fraudParameterDetailsDto.getFraudCriteriaValue1());
		fraudParameterDetails.setFraudCriteriaValue2(fraudParameterDetailsDto.getFraudCriteriaValue2());
		fraudParameterDetails.setSourceEntityTable(fraudParameterDetailsDto.getSourceEntityTable());
		fraudParameterDetails.setSourceEntityColumn(fraudParameterDetailsDto.getSourceEntityColumn());
		fraudParameterDetails.setTimeWindow(fraudParameterDetailsDto.getTimeWindow());
		fraudParameterDetails.setExchangeCode(fraudParameterDetailsDto.getExchangeCode());
		fraudParameterDetails.setToCurrCode(fraudParameterDetailsDto.getToCurrCode());		
		return fraudParameterDetails;
		
	}

}
