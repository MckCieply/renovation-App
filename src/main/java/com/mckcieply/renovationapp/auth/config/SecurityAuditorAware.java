package com.mckcieply.renovationapp.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

/**
 * Configuration class to provide the current auditor information for JPA auditing.
 *
 * <p>This class implements the {@link AuditorAware} interface to retrieve the username
 * of the currently authenticated user for auditing purposes. It leverages Spring Security's
 * {@link SecurityContextHolder} to access the authentication information.</p>
 */
@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class SecurityAuditorAware implements AuditorAware<String> {

    /**
     * Retrieves the current auditor's username from the security context.
     *
     * @return an Optional containing the username of the current auditor if authenticated,
     *         or an empty Optional if no authenticated user is found.
     */
    @Override
    public Optional<String> getCurrentAuditor() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        return Optional.of(((UserDetails) principal).getUsername());
    }

    /**
     * Bean definition for {@link AuditorAware} implementation.
     *
     * @return an instance of {@link AuditorAware<String>} that retrieves the current auditor's username.
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return new SecurityAuditorAware();
    }
}
