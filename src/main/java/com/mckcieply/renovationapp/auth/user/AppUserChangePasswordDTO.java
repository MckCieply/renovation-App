package com.mckcieply.renovationapp.auth.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class AppUserChangePasswordDTO {
    private String username;
    private String oldPassword;
    private String newPassword;

}
