package com.mckcieply.renovationapp.room;


import lombok.Data;

import jakarta.persistence.*;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long budgetPlanned;
}
