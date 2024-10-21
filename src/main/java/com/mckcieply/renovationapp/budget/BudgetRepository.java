package com.mckcieply.renovationapp.budget;

import com.mckcieply.core.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing budget data.
 * Extends BaseRepository to inherit common CRUD operations.
 */
@Repository
public interface BudgetRepository extends BaseRepository<Budget, Long> {
}
