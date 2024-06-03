package com.mckcieply.renovationapp.auth.user;

import com.mckcieply.renovationapp.auth.user.dto.AppUserChangePasswordDTO;
import com.mckcieply.renovationapp.auth.user.dto.AppUserProfileDTO;
import com.mckcieply.renovationapp.auth.user.role.Role;
import com.mckcieply.renovationapp.auth.user.role.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    PasswordEncoder passwordEncoder;

    public void saveUser(AppUser user) {
        appUserRepository.save(user);
    }


    public void updateRoles(String username, List<Role> roles) {
        AppUser user = appUserRepository.findByUsername(username);
        user.setRoles(roles);
        appUserRepository.save(user);
    }

    public AppUserProfileDTO getUser(String username) {
        return mapAppUserToAppUserProfileDTO(appUserRepository.findByUsername(username));
    }

    public List<AppUserProfileDTO> getAllUsers() {
        return appUserRepository.findAll().stream()
                .map(user -> mapAppUserToAppUserProfileDTO(user))
                .toList();
    }

    public void updateUser(AppUserProfileDTO profileDTO) {
        appUserRepository.save(mapAppUserProfileDTOToAppUser(profileDTO));
    }

    public void changePassword(AppUserChangePasswordDTO changePasswordDTO) {
        AppUser user = appUserRepository.findByUsername(changePasswordDTO.getUsername());

        if (user == null)
            throw new IllegalArgumentException("User not found");


        if(passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
            appUserRepository.save(user);
        }
        else
            throw new IllegalArgumentException("Incorrect Password");

    }

    private AppUserProfileDTO mapAppUserToAppUserProfileDTO(AppUser appUser) {
        return AppUserProfileDTO.builder()
                .username(appUser.getUsername())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .email(appUser.getEmail())
                .roles(appUser.getRoles())
                .build();
    }

    private AppUser mapAppUserProfileDTOToAppUser(AppUserProfileDTO profileDTO) {
        return AppUser.builder()
                .username(profileDTO.getUsername())
                .firstName(profileDTO.getFirstName())
                .lastName(profileDTO.getLastName())
                .email(profileDTO.getEmail())
                .roles(profileDTO.getRoles())
                .build();
    }

}
