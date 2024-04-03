package com.mckcieply.renovationapp.auth;


import com.mckcieply.renovationapp.auth.config.JwtService;
import com.mckcieply.renovationapp.auth.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AppUserRepository appUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthResponse register(AppUserRegisterDTO appUserRegisterDTO) {
        var user = AppUser.builder()
                .firstName(appUserRegisterDTO.getFirstName())
                .lastName(appUserRegisterDTO.getLastName())
                .username(appUserRegisterDTO.getUsername())
                .email(appUserRegisterDTO.getEmail())
                .password(passwordEncoder.encode(appUserRegisterDTO.getPassword()))
                .role(Role.USER)
                .build();
        appUserRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AuthResponse login(AppUserLoginDTO appUserLoginDTO) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(appUserLoginDTO.getUsername(), appUserLoginDTO.getPassword()));

        var user = appUserRepository.findByUsername(appUserLoginDTO.getUsername());
        var jwtToken = jwtService.generateToken(user);
        return AuthResponse.builder()
                .token(jwtToken)
                .build();
    }
}
