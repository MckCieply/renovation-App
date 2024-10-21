package com.mckcieply.renovationapp.workType;

import com.mckcieply.core.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing work type data.
 * Extends BaseRepository to provide CRUD operations for WorkType entities.
 */
@Repository
public interface WorkTypeRepository extends BaseRepository<WorkType, Long> {
}
