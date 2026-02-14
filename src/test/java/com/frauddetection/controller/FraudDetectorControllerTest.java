package com.frauddetection.controller;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.service.fraud.FraudDetectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FraudDetectorController.class)
public class FraudDetectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FraudDetectionService fraudDetectionService;

    private static final String REQUEST_BODY = "{\"amount\":600,\"currency\":\"EUR\",\"terminalId\":\"123\",\"merchant\":\"KFC\",\"terminalThreatScore\":\"80\"}";

    @Test
    void evaluateFraud_validRequest_returns200() throws Exception {
        ResponseInfo responseInfo = new ResponseInfo("L", "Low", "20.0", null);

        when(fraudDetectionService.evaluateFraud(any()))
                .thenReturn(responseInfo);

        mockMvc.perform(post("/evaluateFraud")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("L"))
                .andExpect(jsonPath("$.message").value("Low"))
                .andExpect(jsonPath("$.fraudScore").value("20.0"));
    }

    @Test
    void evaluateFraud_highRisk_returnsHighStatus() throws Exception {
        ResponseInfo responseInfo = new ResponseInfo("H", "High", "100", null);

        when(fraudDetectionService.evaluateFraud(any()))
                .thenReturn(responseInfo);

        mockMvc.perform(post("/evaluateFraud")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("H"))
                .andExpect(jsonPath("$.message").value("High"))
                .andExpect(jsonPath("$.fraudScore").value("100"));
    }
}
