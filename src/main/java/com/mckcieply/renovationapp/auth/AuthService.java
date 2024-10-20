package com.mckcieply.renovationapp.auth;


import com.mckcieply.renovationapp.auth.config.JwtService;
import com.mckcieply.renovationapp.auth.user.AppUser;
import com.mckcieply.renovationapp.auth.user.AppUserRepository;
import com.mckcieply.renovationapp.auth.user.dto.AppUserLoginDTO;
import com.mckcieply.renovationapp.auth.user.dto.AppUserRegisterDTO;
import com.mckcieply.renovationapp.auth.user.role.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for handling authentication-related operations.
 *
 * <p>This service manages user registration, login, role initialization, and retrieval of user roles.</p>
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;

    /**
     * Registers a new user and returns a JWT token.
     *
     * @param appUserRegisterDTO the user registration details.
     * @return an AuthResponse containing the generated JWT token.
     */
    public AuthResponse register(AppUserRegisterDTO appUserRegisterDTO) {
        var user = AppUser.builder()
                .firstName(appUserRegisterDTO.getFirstName())
                .lastName(appUserRegisterDTO.getLastName())
                .username(appUserRegisterDTO.getUsername())
                .email(appUserRegisterDTO.getEmail())
                .password(passwordEncoder.encode(appUserRegisterDTO.getPassword()))
                .roles(List.of(roleRepository.findByName("USER")))
                .build();
        appUserRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Authenticates a user and returns a JWT token.
     *
     * @param appUserLoginDTO the user login details.
     * @return an AuthResponse containing the generated JWT token.
     */
    public AuthResponse login(AppUserLoginDTO appUserLoginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUserLoginDTO.getUsername(), appUserLoginDTO.getPassword()));

        var user = appUserRepository.findByUsername(appUserLoginDTO.getUsername());
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    /**
     * Initializes the admin user if it doesn't already exist.
     * DISABLE ON PRODUCTION!
     */
    public void adminInit() {
        if (appUserRepository.findByUsername("admin") == null) {
            var admin = AppUser.builder()
                    .firstName("Admin")
                    .lastName("Admin")
                    .username("admin")
                    .email("adminoo@gmail.com")
                    .password(passwordEncoder.encode("zaq1@WSX"))
                    .roles(List.of(
                            roleRepository.findByName("ADMIN"),
                            roleRepository.findByName("USER")
                    ))
                    .build();
            appUserRepository.save(admin);
        }
    }

    /**
     * Retrieves the roles of the currently authenticated user.
     *
     * @return a list of role names associated with the authenticated user.
     */
    public List<String> getUserRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getAuthorities().stream()
                .map(authority -> authority.getAuthority())
                .collect(Collectors.toList());
    }
}
