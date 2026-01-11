package com.mckcieply.renovationapp.apiTests.serviceTests;

import com.mckcieply.renovationapp.auth.AuthResponse;
import com.mckcieply.renovationapp.auth.AuthService;
import com.mckcieply.renovationapp.auth.config.JwtService;
import com.mckcieply.renovationapp.auth.user.AppUser;
import com.mckcieply.renovationapp.auth.user.AppUserRepository;
import com.mckcieply.renovationapp.auth.user.dto.AppUserLoginDTO;
import com.mckcieply.renovationapp.auth.user.dto.AppUserRegisterDTO;
import com.mckcieply.renovationapp.auth.user.role.Role;
import com.mckcieply.renovationapp.auth.user.role.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTests {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testRegister() {
        // Arrange
        AppUserRegisterDTO registerDTO = new AppUserRegisterDTO(
                "John", "Doe", "johndoe", "john@example.com", "password123");

        Role userRole = Role.builder().name("USER").build();
        AppUser savedUser = AppUser.builder()
                .firstName("John")
                .lastName("Doe")
                .username("johndoe")
                .email("john@example.com")
                .password("encodedPassword")
                .roles(List.of(userRole))
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findByName("USER")).thenReturn(userRole);
        when(appUserRepository.save(any(AppUser.class))).thenReturn(savedUser);
        when(jwtService.generateToken(any(AppUser.class))).thenReturn("jwt-token-123");

        // Act
        AuthResponse response = authService.register(registerDTO);

        // Assert
        assertNotNull(response);
        assertEquals("jwt-token-123", response.getToken());

        verify(passwordEncoder, times(1)).encode("password123");
        verify(roleRepository, times(1)).findByName("USER");
        verify(appUserRepository, times(1)).save(any(AppUser.class));
        verify(jwtService, times(1)).generateToken(any(AppUser.class));
    }

    @Test
    public void testLogin() {
        // Arrange
        AppUserLoginDTO loginDTO = new AppUserLoginDTO("testuser", "password123");

        AppUser user = AppUser.builder()
                .username("testuser")
                .password("encodedPassword")
                .build();

        when(appUserRepository.findByUsername("testuser")).thenReturn(user);
        when(jwtService.generateToken(any(AppUser.class))).thenReturn("jwt-token-456");

        // Act
        AuthResponse response = authService.login(loginDTO);

        // Assert
        assertNotNull(response);
        assertEquals("jwt-token-456", response.getToken());

        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(appUserRepository, times(1)).findByUsername("testuser");
        verify(jwtService, times(1)).generateToken(user);
    }

    @Test
    public void testLoginWithUppercaseUsername() {
        // Arrange
        AppUserLoginDTO loginDTO = new AppUserLoginDTO("TestUser", "password123");

        AppUser user = AppUser.builder()
                .username("testuser")
                .password("encodedPassword")
                .build();

        when(appUserRepository.findByUsername("testuser")).thenReturn(user);
        when(jwtService.generateToken(any(AppUser.class))).thenReturn("jwt-token-789");

        // Act
        AuthResponse response = authService.login(loginDTO);

        // Assert
        assertNotNull(response);
        assertEquals("jwt-token-789", response.getToken());

        // Verify that username is converted to lowercase
        verify(authenticationManager, times(1))
                .authenticate(new UsernamePasswordAuthenticationToken("testuser", "password123"));
        verify(appUserRepository, times(1)).findByUsername("testuser");
    }

    @Test
    public void testAdminInit_WhenAdminDoesNotExist() {
        // Arrange
        Role adminRole = Role.builder().name("ADMIN").build();
        Role userRole = Role.builder().name("USER").build();

        when(appUserRepository.findByUsername("admin")).thenReturn(null);
        when(roleRepository.findByName("ADMIN")).thenReturn(adminRole);
        when(roleRepository.findByName("USER")).thenReturn(userRole);
        when(passwordEncoder.encode("zaq1@WSX")).thenReturn("encodedPassword");
        when(appUserRepository.save(any(AppUser.class))).thenReturn(any(AppUser.class));

        // Act
        authService.adminInit();

        // Assert
        verify(appUserRepository, times(1)).findByUsername("admin");
        verify(roleRepository, times(1)).findByName("ADMIN");
        verify(roleRepository, times(1)).findByName("USER");
        verify(passwordEncoder, times(1)).encode("zaq1@WSX");
        verify(appUserRepository, times(1)).save(any(AppUser.class));
    }

    @Test
    public void testAdminInit_WhenAdminExists() {
        // Arrange
        AppUser existingAdmin = AppUser.builder()
                .username("admin")
                .build();
        when(appUserRepository.findByUsername("admin")).thenReturn(existingAdmin);

        // Act
        authService.adminInit();

        // Assert
        verify(appUserRepository, times(1)).findByUsername("admin");
        verify(appUserRepository, never()).save(any(AppUser.class));
    }
}
