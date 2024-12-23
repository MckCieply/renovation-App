package com.mckcieply.renovationapp;

import com.mckcieply.renovationapp.auth.AuthService;
import com.mckcieply.renovationapp.auth.user.role.RoleService;
import com.mckcieply.renovationapp.budget.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
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

    /**
     * Runs after the application context is loaded. This method is used to
     * initialize roles, budget, and admin user upon application startup.
     *
     * @param args command-line arguments passed to the application
     * @throws Exception if an error occurs during initialization
     */
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
