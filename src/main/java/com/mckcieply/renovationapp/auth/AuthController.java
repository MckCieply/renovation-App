package com.mckcieply.renovationapp.auth;

import com.mckcieply.renovationapp.auth.user.dto.AppUserLoginDTO;
import com.mckcieply.renovationapp.auth.user.dto.AppUserRegisterDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling authentication-related requests.
 *
 * <p>This class provides endpoints for user registration, login, and
 * retrieving user roles.</p>
 */
@RestController
@RequestMapping(path = "api/auth")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    /**
     * Registers a new user.
     *
     * @param appUserRegisterDTO the user registration data
     * @return ResponseEntity containing the authentication response
     */
    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AppUserRegisterDTO appUserRegisterDTO) {
        return ResponseEntity.ok(authService.register(appUserRegisterDTO));
    }

    /**
     * Authenticates a user and returns a token.
     *
     * @param appUserLoginDTO the user login data
     * @return ResponseEntity containing the authentication response
     */
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AppUserLoginDTO appUserLoginDTO) {
        return ResponseEntity.ok(authService.login(appUserLoginDTO));
    }

    /**
     * Retrieves the roles associated with the current user.
     *
     * @return ResponseEntity containing a list of user roles
     */
    @GetMapping("/roles")
    public ResponseEntity<List<String>> getUserRoles() {
        return ResponseEntity.ok(authService.getUserRoles());
    }

}
