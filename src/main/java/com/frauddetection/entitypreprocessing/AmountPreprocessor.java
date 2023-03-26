package com.frauddetection.entitypreprocessing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.frauddetection.dto.TransactionInfo;
import com.frauddetection.model.ExchangeRates;
import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.repository.ExchangeRatesRepository;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class AmountPreprocessor implements EntityPreprocessor{
	
	
	@Autowired
	private ExchangeRatesRepository exchangeRatesRepository; 
	
	@Override
	public void normalizeEntity(TransactionInfo transactionInfo, FraudParameterDetails fraudParameterDetails) {
		
		//check currency code
		
		log.info("Checking request and fraud rule["+fraudParameterDetails.getFraudRuleName()+"] Currency are equal." );
		if(transactionInfo.getCurrency().equalsIgnoreCase(fraudParameterDetails.getToCurrCode())) {
			log.info("Currencies are equal. No need to preprocess Amount" );
			return;
		}
		
		log.info("Going to fetch exchangeRates!" );
		ExchangeRates exchangeRates = exchangeRatesRepository.findByExchangeCodeAndFromCurrencyCodeAndToCurrencyCode(fraudParameterDetails.getExchangeCode(), transactionInfo.getCurrency(), fraudParameterDetails.getToCurrCode());
		log.info("Fetched Details["+ exchangeRates);
		Double amount = transactionInfo.getAmount() * exchangeRates.getRate();
		log.info("Setting amount in actual criteria value");
		fraudParameterDetails.setActualCriteriaValue(amount.toString());
	}

}
