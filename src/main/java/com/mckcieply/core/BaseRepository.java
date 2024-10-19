package com.mckcieply.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * Base repository interface that extends {@link JpaRepository}, providing standard
 * CRUD operations for any entity type.
 *
 * <p>This repository is generic and designed to be extended by specific repositories
 * for different entities. The {@link NoRepositoryBean} annotation ensures that this
 * interface is not directly instantiated as a Spring bean.</p>
 *
 * @param <T>  the type of the entity the repository will manage
 * @param <ID> the type of the entity's identifier
 */
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID> {
}
