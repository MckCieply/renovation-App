package com.mckcieply.renovationapp.auth.user.dto;

import com.mckcieply.renovationapp.auth.user.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserProfileDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private List<Role> roles;
}
