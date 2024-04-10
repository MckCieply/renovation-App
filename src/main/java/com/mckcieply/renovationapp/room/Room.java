package com.mckcieply.renovationapp.room;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(min=3, max = 100, message = "Name must be between 3 and 100 characters long")
    private String name;

    private Long budgetPlanned;
}
