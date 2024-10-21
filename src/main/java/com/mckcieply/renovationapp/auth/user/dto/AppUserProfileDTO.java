package com.mckcieply.renovationapp.auth.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mckcieply.renovationapp.auth.user.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO for user profile information.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties
public class AppUserProfileDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> roles;
}
