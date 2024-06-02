package com.mckcieply.renovationapp;

import com.mckcieply.renovationapp.auth.AuthService;
import com.mckcieply.renovationapp.auth.user.role.RoleRepository;
import com.mckcieply.renovationapp.auth.user.role.RoleService;
import com.mckcieply.renovationapp.budget.BudgetService;
import com.mckcieply.renovationapp.enumerable.EnumRole;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.security.Key;

@SpringBootApplication
@EnableJpaAuditing
public class RenovationAppApplication implements CommandLineRunner {

	@Autowired
	RoleService roleService;
	
    @Autowired
    private BudgetService budgetService;

	@Autowired
	AuthService authService;

	public static void main(String[] args) {
		SpringApplication.run(RenovationAppApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		roleService.rolesInit();
		budgetService.budgetInit();
		authService.adminInit();
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
