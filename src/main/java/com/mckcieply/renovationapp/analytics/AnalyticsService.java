package com.mckcieply.renovationapp.analytics;

import com.mckcieply.renovationapp.analytics.dto.CostAnalyticsDto;
import com.mckcieply.renovationapp.work.WorkRepository;
import org.springframework.stereotype.Service;

@Service
public class AnalyticsService {
    private final WorkRepository workRepository;

    public AnalyticsService(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    public CostAnalyticsDto getCostData() {
        return workRepository.getCostAnalytics();
    }
}
