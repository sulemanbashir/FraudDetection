package com.frauddetection.service.fraud.operational;

import java.util.Optional;

import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.frauddetection.dto.FraudParameterDetailsDto;
import com.frauddetection.exception.ResourceNotFoundException;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.repository.FraudParameterDetailsRepository;

@Service
public class OperationalRuleService {

    private final FraudParameterDetailsRepository fraudParameterDetailsRepository;
    private final ModelMapper modelMapper;

    public OperationalRuleService(
            FraudParameterDetailsRepository fraudParameterDetailsRepository,
            ModelMapper modelMapper) {
        this.fraudParameterDetailsRepository = fraudParameterDetailsRepository;
        this.modelMapper = modelMapper;
    }

    public FraudParameterDetails saveFraudRule(FraudParameterDetailsDto fraudParameterDetailsDto) {
        return fraudParameterDetailsRepository.save(modelMapper.map(fraudParameterDetailsDto, FraudParameterDetails.class));
    }

    public FraudParameterDetails updateFraudRule(FraudParameterDetailsDto fraudParameterDetailsDto) {
        Optional<FraudParameterDetails> fraudParameterDetailsDb = fraudParameterDetailsRepository.findByMerchantNameAndFraudRuleName(fraudParameterDetailsDto.merchant().name(), fraudParameterDetailsDto.fraudRuleName());
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        if (!fraudParameterDetailsDb.isPresent()) {
            throw new ResourceNotFoundException("merchantName", fraudParameterDetailsDto.merchant().name());
        }
        modelMapper.map(fraudParameterDetailsDto, fraudParameterDetailsDb.get());
        return fraudParameterDetailsRepository.save(fraudParameterDetailsDb.get());
    }

    public void deleteFraudRule(FraudParameterDetailsDto fraudParameterDetailsDto) {
        Optional<FraudParameterDetails> fraudParameterDetailsDb = fraudParameterDetailsRepository.findByMerchantNameAndFraudRuleName(fraudParameterDetailsDto.merchant().name(), fraudParameterDetailsDto.fraudRuleName());
        modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        if (!fraudParameterDetailsDb.isPresent()) {
            throw new ResourceNotFoundException("merchantName", fraudParameterDetailsDto.merchant().name());
        }

        fraudParameterDetailsRepository.deleteById(fraudParameterDetailsDb.get().getId());
    }

}
