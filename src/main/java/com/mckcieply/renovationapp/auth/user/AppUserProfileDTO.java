package com.mckcieply.renovationapp.auth.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AppUserProfileDTO {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
}
