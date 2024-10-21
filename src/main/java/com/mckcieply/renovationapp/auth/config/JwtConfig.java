package com.mckcieply.renovationapp.auth.config;

import com.mckcieply.renovationapp.auth.user.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * Configuration class for JWT-related beans and security components.
 *
 * <p>This class defines beans for managing user authentication,
 * password encoding, and the user details service that integrates with
 * the {@link AppUserRepository} to load user data for authentication.</p>
 */
@Configuration
@RequiredArgsConstructor
public class JwtConfig {

    private final AppUserRepository appUserRepository;

    /**
     * Bean definition for {@link UserDetailsService}.
     *
     * <p>This service is responsible for loading user-specific data
     * during authentication by fetching user details from the
     * {@link AppUserRepository} based on the provided username.</p>
     *
     * @return an instance of {@link UserDetailsService}.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return username -> appUserRepository.findByUsername(username);
    }

    /**
     * Bean definition for {@link AuthenticationProvider}.
     *
     * <p>The authentication provider is responsible for retrieving user
     * details via the {@link DaoAuthenticationProvider}, verifying the
     * password with the provided {@link PasswordEncoder}, and
     * authenticating users during the login process.</p>
     *
     * @return an instance of {@link AuthenticationProvider}.
     */
    @Bean
    public AuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Bean definition for {@link AuthenticationManager}.
     *
     * <p>The authentication manager is a central component of Spring Security
     * responsible for managing authentication requests and delegating them
     * to the appropriate authentication provider.</p>
     *
     * @param authenticationConfiguration the Spring Security configuration for authentication.
     * @return an instance of {@link AuthenticationManager}.
     * @throws Exception if an error occurs while retrieving the {@link AuthenticationManager}.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Bean definition for {@link PasswordEncoder}.
     *
     * <p>This encoder is used to hash passwords using the BCrypt algorithm,
     * which ensures that passwords are stored securely.</p>
     *
     * @return an instance of {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
