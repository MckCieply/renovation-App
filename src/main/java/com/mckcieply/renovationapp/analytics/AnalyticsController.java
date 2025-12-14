package com.mckcieply.renovationapp.analytics;

import com.mckcieply.renovationapp.analytics.dto.CostAnalyticsDto;
import com.mckcieply.renovationapp.analytics.dto.RoomHealthDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@CrossOrigin(origins = "http://localhost:4200")
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    public AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @GetMapping("/costs")
    public ResponseEntity<CostAnalyticsDto> getCostBreakdown() {
        return ResponseEntity.ok(analyticsService.getCostData());
    }

    @GetMapping("/roomsHealth")
    public ResponseEntity<List<RoomHealthDto>> getRoomHealth() {
        return ResponseEntity.ok(analyticsService.getRoomHealth());
    }
}
