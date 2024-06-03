package com.mckcieply.renovationapp.auth.user.role;

import com.mckcieply.renovationapp.enumerable.EnumRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    RoleRepository roleRepository;

    public void rolesInit() {
        for (EnumRole role : EnumRole.values()) {
            if (roleRepository.findByName(role.toString()) != null)
                continue;

            Role newRole = Role.builder().name(role.toString()).build();
            roleRepository.save(newRole);
        }
    }

}
