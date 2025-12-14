package com.mckcieply.renovationapp.analytics.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CostAnalyticsDto {
    private Double laborTotal;
    private Double materialTotal;

    public CostAnalyticsDto(Double laborTotal, Double materialTotal) {
        this.laborTotal = laborTotal != null ? laborTotal : 0.0;
        this.materialTotal = materialTotal != null ? materialTotal : 0.0;
    }
}
