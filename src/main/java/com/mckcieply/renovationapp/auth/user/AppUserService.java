package com.mckcieply.renovationapp.auth.user;

import com.mckcieply.renovationapp.auth.user.dto.AppUserChangePasswordDTO;
import com.mckcieply.renovationapp.auth.user.dto.AppUserProfileDTO;
import com.mckcieply.renovationapp.auth.user.role.Role;
import com.mckcieply.renovationapp.auth.user.role.RoleDTO;
import com.mckcieply.renovationapp.auth.user.role.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class for managing application user operations.
 */
@Service
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository appUserRepository;
    private final RoleRepository roleRepository;
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
        // Fetch existing user from database
        AppUser existingUser = appUserRepository.findByUsername(user.getUsername());
        if (existingUser == null) {
            throw new IllegalArgumentException("User not found: " + user.getUsername());
        }

        if (admin) {
            // Fetch ADMIN role from database and add it
            Role adminRole = roleRepository.findByName("ADMIN");
            if (adminRole != null && !existingUser.getRoles().contains(adminRole)) {
                existingUser.getRoles().add(adminRole);
            }
        } else {
            // Remove ADMIN role from user roles
            existingUser.getRoles().removeIf(role -> role.getName().equals("ADMIN"));
        }

        appUserRepository.save(existingUser);
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
        List<RoleDTO> roleDTOs = appUser.getRoles() != null
                ? appUser.getRoles().stream()
                    .map(RoleDTO::fromRole)
                    .collect(Collectors.toList())
                : null;

        return AppUserProfileDTO.builder()
                .username(appUser.getUsername())
                .firstName(appUser.getFirstName())
                .lastName(appUser.getLastName())
                .email(appUser.getEmail())
                .roles(roleDTOs)
                .build();
    }

    /**
     * Maps an AppUserProfileDTO to an AppUser.
     *
     * @param profileDTO the AppUserProfileDTO to map
     * @return the corresponding AppUser
     */
    private AppUser mapAppUserProfileDTOToAppUser(AppUserProfileDTO profileDTO) {
        List<Role> roles = profileDTO.getRoles() != null
                ? profileDTO.getRoles().stream()
                    .map(RoleDTO::toRole)
                    .collect(Collectors.toList())
                : null;

        return AppUser.builder()
                .username(profileDTO.getUsername())
                .firstName(profileDTO.getFirstName())
                .lastName(profileDTO.getLastName())
                .email(profileDTO.getEmail())
                .roles(roles)
                .build();
    }

}
