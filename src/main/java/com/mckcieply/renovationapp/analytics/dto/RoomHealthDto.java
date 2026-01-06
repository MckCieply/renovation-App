package com.mckcieply.renovationapp.analytics.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RoomHealthDto {
    private String roomName;
    private Double budgetPlanned;
    private Double actualSpent;

    // This constructor now matches perfectly: (String, Double, Double)
    public RoomHealthDto(String roomName, Number budgetPlanned, Number actualSpent) {
        this.roomName = roomName;
        this.budgetPlanned = budgetPlanned != null ? budgetPlanned.doubleValue() : 0.0;
        this.actualSpent = actualSpent != null ? actualSpent.doubleValue() : 0.0;
    }
}
