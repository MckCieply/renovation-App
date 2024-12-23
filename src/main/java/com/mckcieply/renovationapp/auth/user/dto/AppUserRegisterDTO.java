package com.mckcieply.renovationapp.auth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for registering a new user.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserRegisterDTO {

    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;

}
