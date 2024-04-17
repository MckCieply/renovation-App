package com.mckcieply.renovationapp.room;


import com.mckcieply.core.BaseEntity;
import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "rooms")
public class Room extends BaseEntity {

    private Long budgetPlanned;
}
