package com.mckcieply.renovationapp.auth.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


// DAO for AppUser for login

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AppUserLoginDTO {

    String username;
    String password;
}
