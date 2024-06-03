package com.mckcieply.renovationapp.auth.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties
public class AppUserUpdateRolesDTO {
    private AppUserProfileDTO user;
    private Boolean isAdmin;
}
