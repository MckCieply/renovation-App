package com.mckcieply.renovationapp.work;

import com.mckcieply.renovationapp.enumerable.EnumWorkState;
import com.mckcieply.renovationapp.room.Room;
import com.mckcieply.renovationapp.workType.WorkType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "works")
public class Work {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "description")
    private String description;

    @Column(name = "est_material_cost")
    private double estMaterialCost;

    @Column(name = "est_labor_cost")
    private double estLaborCost;

    @Column(name = "final_material_cost")
    private double finalMaterialCost;

    @Column(name = "final_labor_cost")
    private double finalLaborCost;

    @Enumerated(EnumType.STRING)
    private EnumWorkState state;

    @Column(name = "paid")
    private boolean paid;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @ManyToOne
    private Room room;

    @ManyToOne
    private WorkType workType;
}
