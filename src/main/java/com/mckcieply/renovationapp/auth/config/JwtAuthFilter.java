package com.mckcieply.renovationapp.auth.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A filter that processes incoming requests and checks for a JWT token in the "Authorization" header.
 * If a valid JWT is found, the filter extracts the username, validates the token, and authenticates the user.
 *
 * <p>This filter is part of the security chain and ensures that only authenticated users,
 * based on a valid JWT, can proceed with the request.</p>
 *
 * <p>JWTs must be in the "Bearer" format. The token is validated using the {@link JwtService},
 * and if valid, the user details are loaded from the {@link UserDetailsService} to create an
 * authenticated security context.</p>
 */
@Component
@AllArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * Filters incoming requests to validate JWT from the "Authorization" header.
     * If a valid token is found, the user is authenticated and added to the security context.
     *
     * <p>Steps performed:
     * <ol>
     *     <li>Checks if the "Authorization" header contains a Bearer token.</li>
     *     <li>Extracts the token and retrieves the username from it using {@link JwtService#extractUsername(String)}.</li>
     *     <li>Loads the user details using {@link UserDetailsService#loadUserByUsername(String)}.</li>
     *     <li>If the username is valid and the token is valid, authenticates the user and sets the security context.</li>
     * </ol>
     *
     * @param request     the HTTP request to filter.
     * @param response    the HTTP response.
     * @param filterChain the filter chain to continue processing.
     * @throws ServletException if an error occurs during filtering.
     * @throws IOException      if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        final String authHeader = request.getHeader("Authorization");
        final String token;
        final String username;

//        Checks if the "Authorization" header contains a Bearer token.
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

//        Extracts the token and retrieves the username from it
        token = authHeader.substring(7);
        username = jwtService.extractUsername(token);



//        Loads the user details if the username is not null and the security context is not yet authenticated
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

//            If the token is valid, authenticates the user and sets the security context
            if (jwtService.isTokenValid(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );

                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
