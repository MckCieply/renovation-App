package com.mckcieply.renovationapp.workType;

import com.mckcieply.core.BaseService;
import org.springframework.stereotype.Service;

/**
 * Service class for managing work types in the renovation application.
 * Provides methods for work type operations such as retrieval and processing.
 * Extends BaseService to inherit common CRUD functionalities.
 */
@Service
public class WorkTypeService extends BaseService<WorkType, Long> {

    private final WorkTypeRepository workTypeRepository;

    public WorkTypeService(WorkTypeRepository workTypeRepository) {
        super(workTypeRepository);
        this.workTypeRepository = workTypeRepository;
    }

    @Override
    protected Class<WorkType> getEntityClass() {
        return WorkType.class;
    }
}
