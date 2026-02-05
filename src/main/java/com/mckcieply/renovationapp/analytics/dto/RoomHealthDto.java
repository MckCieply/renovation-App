package com.mckcieply.renovationapp.analytics.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomHealthDto {
    private String roomName;
    private Double budgetPlanned;
    private Double estimatedCost;
    private Double paidCost;

    public RoomHealthDto(String roomName, Number budgetPlanned, Number estimatedCost, Number paidCost) {
        this.roomName = roomName;
        this.budgetPlanned = budgetPlanned != null ? budgetPlanned.doubleValue() : 0.0;
        this.estimatedCost = estimatedCost != null ? estimatedCost.doubleValue() : 0.0;
        this.paidCost = paidCost != null ? paidCost.doubleValue() : 0.0;
    }
}
