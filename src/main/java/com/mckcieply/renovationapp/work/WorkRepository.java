package com.mckcieply.renovationapp.work;

import com.mckcieply.core.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing work data.
 * Extends BaseRepository to provide CRUD operations for Work entities.
 */
@Repository
public interface WorkRepository extends BaseRepository<Work, Long> {
}
