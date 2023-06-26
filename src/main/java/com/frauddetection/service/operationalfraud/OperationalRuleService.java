package com.frauddetection.service.operationalfraud;

import java.util.Optional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.frauddetection.dto.FraudParameterDetailsDto;
import com.frauddetection.exception.ResourceNotFoundException;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.repository.FraudParameterDetailsRepository;

@Service
public class OperationalRuleService {

	@Autowired
	FraudParameterDetailsRepository fraudParameterDetailsRepository;

	ModelMapper modelMapper = new ModelMapper();

	public FraudParameterDetails saveFraudRule(FraudParameterDetailsDto fraudParameterDetailsDto) {
		return fraudParameterDetailsRepository.save(modelMapper.map(fraudParameterDetailsDto, FraudParameterDetails.class));
	}

	public FraudParameterDetails updateFraudRule(FraudParameterDetailsDto fraudParameterDetailsDto) {
		Optional<FraudParameterDetails> fraudParameterDetailsDb = fraudParameterDetailsRepository.findByMerchantNameAndFraudRuleName(fraudParameterDetailsDto.getMerchant().getName(), fraudParameterDetailsDto.getFraudRuleName());
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		if (!fraudParameterDetailsDb.isPresent()) {
			throw new ResourceNotFoundException("merchantName", fraudParameterDetailsDto.getMerchant().getName());
		}
		modelMapper.map(fraudParameterDetailsDto, fraudParameterDetailsDb.get());
		return fraudParameterDetailsRepository.save(fraudParameterDetailsDb.get());
	}

	public void deleteFraudRule(FraudParameterDetailsDto fraudParameterDetailsDto) {
		Optional<FraudParameterDetails> fraudParameterDetailsDb = fraudParameterDetailsRepository.findByMerchantNameAndFraudRuleName(fraudParameterDetailsDto.getMerchant().getName(), fraudParameterDetailsDto.getFraudRuleName());
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		if (!fraudParameterDetailsDb.isPresent()) {
			throw new ResourceNotFoundException("merchantName", fraudParameterDetailsDto.getMerchant().getName());
		}

		fraudParameterDetailsRepository.deleteById(fraudParameterDetailsDb.get().getId());
	}

}
