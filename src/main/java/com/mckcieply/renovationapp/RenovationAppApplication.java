package com.mckcieply.renovationapp;

import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.security.Key;

@SpringBootApplication
public class RenovationAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(RenovationAppApplication.class, args);
	}


//	@Bean
//	public static String secretKey() {
//		// Generate a secret key
//		Key key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
//
//		// Convert the key to a string
//		String base64Key = java.util.Base64.getEncoder().encodeToString(key.getEncoded());
//
//		// Print the generated key
//		System.out.println("Generated Secret Key: " + base64Key);
//
//		return base64Key;
//	}
}
