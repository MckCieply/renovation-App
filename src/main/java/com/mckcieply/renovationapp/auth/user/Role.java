package com.mckcieply.renovationapp.auth.user;

import com.mckcieply.core.BaseEntity;
import com.mckcieply.renovationapp.enumerable.EnumRole;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "roles")
public class Role extends BaseEntity {

    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> appUsers;

    @Override
    public void setName(String name) {
        EnumRole role = EnumRole.fromString(name);
        super.setName(role.toString());
    }
}
