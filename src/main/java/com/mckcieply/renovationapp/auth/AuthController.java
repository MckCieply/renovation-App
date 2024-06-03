package com.mckcieply.renovationapp.auth;

import com.mckcieply.renovationapp.auth.user.dto.AppUserLoginDTO;
import com.mckcieply.renovationapp.auth.user.dto.AppUserRegisterDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getUserRoles() {
        return ResponseEntity.ok(authService.getUserRoles());
    }

}
