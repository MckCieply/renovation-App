package com.mckcieply.renovationapp.auth;

import com.mckcieply.renovationapp.auth.user.AppUserLoginDTO;
import com.mckcieply.renovationapp.auth.user.AppUserRegisterDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AppUserRegisterDTO appUserRegisterDTO) {
        return ResponseEntity.ok(authService.register(appUserRegisterDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AppUserLoginDTO appUserLoginDTO) {
        return ResponseEntity.ok(authService.login(appUserLoginDTO));
    }
}
