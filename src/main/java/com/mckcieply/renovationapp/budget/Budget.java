package com.mckcieply.renovationapp.budget;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a global budget with total limit.
 * Room allocations and work costs are calculated dynamically.
 */
@Data
@Entity
@Table(name = "budget")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double budgetLimit;
}
