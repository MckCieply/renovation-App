package com.mckcieply.renovationapp.auth.user;

import com.mckcieply.renovationapp.auth.user.dto.AppUserChangePasswordDTO;
import com.mckcieply.renovationapp.auth.user.dto.AppUserProfileDTO;
import com.mckcieply.renovationapp.auth.user.dto.AppUserUpdateRolesDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing user-related operations.
 */
@RestController
@RequestMapping(path = "api/user")
@CrossOrigin(origins = "http://localhost:4200")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;

    /**
     * Retrieves the profile of a user by username.
     *
     * @param username the username of the user to retrieve
     * @return a ResponseEntity containing the user profile data
     */
    @GetMapping("/get")
    public ResponseEntity<AppUserProfileDTO> getUser(@RequestParam String username) {
        return ResponseEntity.ok(appUserService.getUser(username));
    }

    /**
     * Updates the profile of a user.
     *
     * @param profileDTO the updated user profile data
     * @return a ResponseEntity with status 200 (OK) if the update is successful
     */
    @PutMapping("/update")
    public ResponseEntity<Void> updateUser(@RequestBody AppUserProfileDTO profileDTO) {
        this.appUserService.updateUser(profileDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Changes the password for a user.
     *
     * @param changePasswordDTO contains the username and new password details
     * @return a ResponseEntity with status 200 (OK) if the password change is successful
     */
    @PutMapping("/change-password")
    public ResponseEntity<Void> changePassword(@RequestBody AppUserChangePasswordDTO changePasswordDTO) {
        this.appUserService.changePassword(changePasswordDTO);
        return ResponseEntity.ok().build();
    }

    /**
     * Retrieves a list of all user profiles.
     *
     * @return a ResponseEntity containing a list of user profiles
     */
    @GetMapping("/get-all")
    public ResponseEntity<List<AppUserProfileDTO>> getAllUsers() {
        return ResponseEntity.ok(appUserService.getAllUsers());
    }

    /**
     * Updates the roles of a user.
     *
     * @param payload contains the username and new role information
     * @return a ResponseEntity with status 200 (OK) if the role update is successful
     */
    @PutMapping("/update-roles")
    public ResponseEntity<Void> updateRoles(@RequestBody AppUserUpdateRolesDTO payload) {
        this.appUserService.updateRoles(payload.getUser(), payload.getIsAdmin());
        return ResponseEntity.ok().build();
    }
}
