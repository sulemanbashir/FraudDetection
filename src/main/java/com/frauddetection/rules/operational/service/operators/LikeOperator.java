package com.frauddetection.rules.operational.service.operators;

import java.text.ParseException;

import com.frauddetection.rules.operational.exception.InvalidInfoException;
import org.springframework.stereotype.Component;

import com.frauddetection.model.FraudParameterDetails;
import com.frauddetection.rules.operational.factory.enums.OperatorDatatypeFactory;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class LikeOperator implements Operator{

	@Override
	public boolean applyOpertion(FraudParameterDetails fraudRule) throws ParseException, InvalidInfoException {
		
		log.info("Going to hunt operator datatype implementation. OperatorId["+fraudRule.getOperator().getId()+"], EntityId["+fraudRule.getEntity().getId()+"], DataTypeId["+fraudRule.getEntity().getDatatype().getId()+"]");
		return OperatorDatatypeFactory.getOperatorDataTypeEnum(fraudRule.getOperator().getId() ,fraudRule.getEntity().getDatatype().getId())
									  .getOperatorDatatype()
									  .applyOpertion(fraudRule.getActualCriteriaValue(), fraudRule.getFraudCriteriaValue1());
	}

	
}