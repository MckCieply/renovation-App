package com.mckcieply.renovationapp.auth.user;

import com.mckcieply.renovationapp.auth.role.Role;
import com.mckcieply.renovationapp.auth.role.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public void saveUser(AppUser user) {
        userRepository.save(user);
    }

    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    public void addRoleToUser(String username, String roleName) {
        AppUser user = userRepository.findByUsername(username);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
        userRepository.save(user);
    }

    public AppUser getUser(String username) {
        return userRepository.findByUsername(username);
    }

    public List<AppUser> getAllUsers() {
        return userRepository.findAll();
    }

}
