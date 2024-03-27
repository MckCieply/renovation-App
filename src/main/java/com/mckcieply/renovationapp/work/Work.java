package com.mckcieply.renovationapp.work;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "works")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
