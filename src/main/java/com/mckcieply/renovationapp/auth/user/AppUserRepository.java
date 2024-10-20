package com.mckcieply.renovationapp.auth.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link AppUser} entities.
 *
 * <p>This interface extends {@link JpaRepository} to provide CRUD operations
 * and includes a method to find a user by their username.</p>
 */
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {

    /**
     * Retrieves an {@link AppUser} by their username.
     *
     * @param username the username of the user to retrieve
     * @return the AppUser associated with the given username, or null if not found
     */
    AppUser findByUsername(String username);
}
