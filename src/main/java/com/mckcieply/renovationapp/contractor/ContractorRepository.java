package com.mckcieply.renovationapp.contractor;

import com.mckcieply.core.BaseRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for accessing contractor data.
 * Extends BaseRepository to provide CRUD operations for Contractor entities.
 */
@Repository
public interface ContractorRepository extends BaseRepository<Contractor, Long> {
}
