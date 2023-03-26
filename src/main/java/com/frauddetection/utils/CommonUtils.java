package com.frauddetection.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public interface CommonUtils {
	
	public static List<String> getTokenizedStringData(String record, String delimeter) {

		StringTokenizer singleRecordTokenizer = null;
		String tokenizedData = null;
		List<String> validatorIdList = new ArrayList<String>();
		try {
		    singleRecordTokenizer = new StringTokenizer(record, delimeter, false);

			while (singleRecordTokenizer.hasMoreTokens()) {
				tokenizedData = singleRecordTokenizer.nextToken();
				if (null != tokenizedData && tokenizedData.trim().length() > 0) {
					validatorIdList.add(tokenizedData.trim());
				}
			}
		} catch (Exception ex) {
			//LGR.warn("##Exception## while performing tokenization of record:- " + ex);
		}
		return validatorIdList;
	}
}
