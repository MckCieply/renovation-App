package com.mckcieply.renovationapp.auth.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for user login information.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserLoginDTO {

    String username;
    String password;
}
