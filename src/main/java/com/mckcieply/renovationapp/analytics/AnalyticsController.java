package com.mckcieply.renovationapp.analytics;

import com.mckcieply.renovationapp.analytics.dto.CostAnalyticsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/costs")
    public ResponseEntity<CostAnalyticsDto> getCostBreakdown() {
        return ResponseEntity.ok(analyticsService.getCostData());
    }
}
