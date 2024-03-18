package com.mckcieply.renovationapp.workType;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "work-types")
public class WorkType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

}
