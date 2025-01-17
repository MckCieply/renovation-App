package com.mckcieply.renovationapp.auth.user;

import com.mckcieply.renovationapp.auth.user.dto.AppUserChangePasswordDTO;
import com.mckcieply.renovationapp.auth.user.dto.AppUserProfileDTO;
import com.mckcieply.renovationapp.auth.user.role.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing application user operations.
 */
@Service
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    PasswordEncoder passwordEncoder;

    /**
     * Saves a new user or updates an existing user in the repository.
     *
     * @param user the AppUser object to save
     */
    public void saveUser(AppUser user) {
        appUserRepository.save(user);
    }

    /**
     * Updates the roles of a user based on admin status.
     *
     * @param user  the user profile data
     * @param admin true to add ADMIN role, false to remove it
     */
    public void updateRoles(AppUserProfileDTO user, Boolean admin) {
        if (admin)
            // append ADMIN role to user roles
            user.getRoles().add(Role.builder().name("ADMIN").build());
        else
            // remove ADMIN role from user roles
            user.getRoles().removeIf(role -> role.getName().equals("ADMIN"));

        appUserRepository.save(mapAppUserProfileDTOToAppUser(user));
    }

    /**
     * Retrieves the profile of a user by username.
     *
     * @param username the username of the user
     * @return the AppUserProfileDTO containing user information
     */
    public AppUserProfileDTO getUser(String username) {
        return mapAppUserToAppUserProfileDTO(appUserRepository.findByUsername(username.toLowerCase()));
    }

    /**
     * Retrieves a list of all user profiles.
     *
     * @return a list of AppUserProfileDTO objects
     */
    public List<AppUserProfileDTO> getAllUsers() {
        return appUserRepository.findAll().stream()
                .map(user -> mapAppUserToAppUserProfileDTO(user))
                .toList();
    }

    /**
     * Updates user profile information.
     *
     * @param profileDTO the updated user profile data
     */
    public void updateUser(AppUserProfileDTO profileDTO) {
        appUserRepository.save(mapAppUserProfileDTOToAppUser(profileDTO));
    }

    /**
     * Changes the password for a user.
     *
     * @param changePasswordDTO contains username and new password information
     * @throws IllegalArgumentException if the user is not found or the old password is incorrect
     */
    public void changePassword(AppUserChangePasswordDTO changePasswordDTO) {
        AppUser user = appUserRepository.findByUsername(changePasswordDTO.getUsername());

        if (user == null)
            throw new IllegalArgumentException("User not found");


        if (passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(changePasswordDTO.getPassword()));
            appUserRepository.save(user);
        } else
            throw new IllegalArgumentException("Incorrect Password");

    }

    /**
     * Maps an AppUser to an AppUserProfileDTO.
     *
     * @param appUser the AppUser to map
     * @return the corresponding AppUserProfileDTO
     */
    private AppUserProfileDTO mapAppUserToAppUserProfileDTO(AppUser appUser) {
        return AppUserProfileDTO.builder()
                .username(appUser.getUsername())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .email(appUser.getEmail())
                .roles(appUser.getRoles())
                .build();
    }

    /**
     * Maps an AppUserProfileDTO to an AppUser.
     *
     * @param profileDTO the AppUserProfileDTO to map
     * @return the corresponding AppUser
     */
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
