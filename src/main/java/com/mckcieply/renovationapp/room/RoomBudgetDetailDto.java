package com.mckcieply.renovationapp.room;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for detailed room budget breakdown including work allocations.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomBudgetDetailDto {
    private Long roomId;
    private String roomName;
    private double budgetPlanned;
    private double estimatedCosts;
    private double paidCosts;
    private double unallocated;
    private boolean hasActiveWork;
}
