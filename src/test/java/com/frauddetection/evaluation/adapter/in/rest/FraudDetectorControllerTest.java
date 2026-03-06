package com.frauddetection.evaluation.adapter.in.rest;

import com.frauddetection.evaluation.domain.valueobject.EvaluationResult;
import com.frauddetection.evaluation.domain.valueobject.RiskLevel;
import com.frauddetection.evaluation.port.in.EvaluateTransactionUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

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
    private EvaluateTransactionUseCase evaluateTransactionUseCase;

    private static final String REQUEST_BODY = "{\"amount\":600,\"currency\":\"EUR\",\"terminalId\":\"123\",\"merchant\":\"KFC\",\"terminalThreatScore\":\"80\"}";

    @Test
    void evaluateFraud_validRequest_returns200() throws Exception {
        EvaluationResult result = new EvaluationResult(RiskLevel.LOW, 20.0, List.of());

        when(evaluateTransactionUseCase.evaluate(any()))
                .thenReturn(result);

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
        EvaluationResult result = new EvaluationResult(RiskLevel.HIGH, 100.0, List.of());

        when(evaluateTransactionUseCase.evaluate(any()))
                .thenReturn(result);

        mockMvc.perform(post("/evaluateFraud")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(REQUEST_BODY))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("H"))
                .andExpect(jsonPath("$.message").value("High"))
                .andExpect(jsonPath("$.fraudScore").value("100.0"));
    }
}
