package com.mckcieply.renovationapp.auth.user;

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


    public void addRoleToUser(String username, String roleName) {
        // todo: change static user to dynamic
    }

    public AppUserProfileDTO getUser(String username) {
        return mapAppUserToAppUserProfileDTO(appUserRepository.findByUsername(username));
    }

    public List<AppUser> getAllUsers() {
        return appUserRepository.findAll();
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
                .build();
    }

    private AppUser mapAppUserProfileDTOToAppUser(AppUserProfileDTO profileDTO) {
        return AppUser.builder()
                .username(profileDTO.getUsername())
                .firstName(profileDTO.getFirstName())
                .lastName(profileDTO.getLastName())
                .email(profileDTO.getEmail())
                .build();
    }

}
