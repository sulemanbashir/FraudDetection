package com.frauddetection.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.jboss.logging.Logger;

public interface CommonUtils {

    Logger log = Logger.getLogger(CommonUtils.class);

    static List<String> getTokenizedStringData(String record, String delimeter) {

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
            log.warn("##Exception## while performing tokenization of record:- " + ex);
        }
        return validatorIdList;
    }
}
