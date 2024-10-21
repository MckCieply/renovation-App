package com.mckcieply.renovationapp.budget;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Represents a budget entry with a unique identifier and a monetary value.
 */
@Data
@Entity
@Table(name = "budget")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double value;
}
