package com.mckcieply.renovationapp.auth.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Service class for handling JSON Web Token (JWT) operations.
 *
 * <p>This class provides methods for generating, validating, and extracting information
 * from JWTs. It uses a secret key for signing the tokens and ensures secure
 * user authentication in the application.</p>
 */
@Service
public class JwtService {

    private static final String SECRET_KEY = "wPKn/4bUxJ6G6DQe9XE0K2nFNnAv40KYogoL4yRrsWI=";

    /**
     * Extracts the username from the JWT.
     *
     * @param token the JWT from which the username is to be extracted
     * @return the username contained in the token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the JWT.
     *
     * @param token the JWT from which the claim is to be extracted
     * @param claimsResolver a function to extract the desired claim from the claims
     * @param <T> the type of the claim
     * @return the extracted claim
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a JWT for a given user.
     *
     * @param userDetails the details of the user for whom the token is to be generated
     * @return a JWT as a string
     */
    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    /**
     * Generates a JWT with additional claims for a given user.
     *
     * @param extraClaims additional claims to be included in the JWT
     * @param userDetails the details of the user for whom the token is to be generated
     * @return a JWT as a string
     */
    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Validates a JWT against the provided user details.
     *
     * @param token the JWT to be validated
     * @param userDetails the user details to validate against
     * @return true if the token is valid; false otherwise
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks if a JWT is expired.
     *
     * @param token the JWT to be checked
     * @return true if the token is expired; false otherwise
     */
    public boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the JWT.
     *
     * @param token the JWT from which the expiration date is to be extracted
     * @return the expiration date of the token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts all claims from the JWT.
     *
     * @param token the JWT from which to extract claims
     * @return the claims contained in the token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    /**
     * Retrieves the signing key used for generating and validating JWTs.
     *
     * @return the signing key
     */
    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
