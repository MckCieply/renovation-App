package com.mckcieply.renovationapp.work;

import com.mckcieply.core.BaseService;
import org.springframework.stereotype.Service;

/**
 * Service class for managing works in the renovation application.
 * Provides methods for work operations such as retrieval and processing.
 * Extends BaseService to inherit common CRUD functionalities.
 */
@Service
public class WorkService extends BaseService<Work, Long> {

    private final WorkRepository workRepository;

    public WorkService(WorkRepository workRepository) {
        super(workRepository);
        this.workRepository = workRepository;
    }
}
