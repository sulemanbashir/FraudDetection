package com.frauddetection.service.fraud;

import java.util.List;

import com.frauddetection.dto.ResponseInfo;
import com.frauddetection.model.FraudScoreLevel;
import com.frauddetection.repository.FraudScoreLevelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FraudScoreEvaluatorServiceTest {

    @Mock
    private FraudScoreLevelRepository fraudScoreLevelRepository;

    @InjectMocks
    private FraudScoreEvaluatorService fraudScoreEvaluatorService;

    @BeforeEach
    void setUp() {
        FraudScoreLevel low = new FraudScoreLevel();
        low.setScoreLevel("L");
        low.setName("Low");
        low.setMinimunRange(0.0);
        low.setMaximumRange(39.0);

        FraudScoreLevel medium = new FraudScoreLevel();
        medium.setScoreLevel("M");
        medium.setName("Medium");
        medium.setMinimunRange(40.0);
        medium.setMaximumRange(59.0);

        FraudScoreLevel high = new FraudScoreLevel();
        high.setScoreLevel("H");
        high.setName("High");
        high.setMinimunRange(60.0);
        high.setMaximumRange(100.0);

        when(fraudScoreLevelRepository.findAll())
                .thenReturn(List.of(low, medium, high));

        fraudScoreEvaluatorService.loadScoreLevels();
    }

    @Test
    void evaluateFraudScore_lowScore_returnsLow() {
        ResponseInfo result = fraudScoreEvaluatorService.evaluateFraudScore(20.0);

        Assertions.assertEquals("L", result.status());
        Assertions.assertEquals("Low", result.message());
        Assertions.assertEquals("20.0", result.fraudScore());
    }

    @Test
    void evaluateFraudScore_mediumScore_returnsMedium() {
        ResponseInfo result = fraudScoreEvaluatorService.evaluateFraudScore(45.0);

        Assertions.assertEquals("M", result.status());
        Assertions.assertEquals("Medium", result.message());
        Assertions.assertEquals("45.0", result.fraudScore());
    }

    @Test
    void evaluateFraudScore_highScore_returnsHigh() {
        ResponseInfo result = fraudScoreEvaluatorService.evaluateFraudScore(75.0);

        Assertions.assertEquals("H", result.status());
        Assertions.assertEquals("High", result.message());
        Assertions.assertEquals("75.0", result.fraudScore());
    }

    @Test
    void evaluateFraudScore_overMaxLimit_capsAtHigh() {
        ResponseInfo result = fraudScoreEvaluatorService.evaluateFraudScore(150.0);

        Assertions.assertEquals("H", result.status());
        Assertions.assertEquals("High", result.message());
        Assertions.assertEquals("100", result.fraudScore());
    }
}
