package com.mckcieply.renovationapp.auth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for changing a user's password.
 */
@Data
@Builder
@AllArgsConstructor
public class AppUserChangePasswordDTO {
    private String username;
    private String oldPassword;
    private String password;

}
