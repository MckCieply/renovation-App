package com.mckcieply.renovationapp.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the response returned after successful authentication.
 *
 * <p>This class contains the JWT token required for securing API requests.</p>
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;

}
