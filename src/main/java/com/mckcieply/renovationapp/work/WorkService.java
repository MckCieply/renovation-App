package com.mckcieply.renovationapp.work;

import com.mckcieply.core.BaseService;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Override
    public Work update(Work work){
        Optional<Work> optionalOldWork = workRepository.findById(work.getId());

        if(optionalOldWork.isEmpty()){
            throw new IllegalArgumentException("Work with id " + work.getId() + " does not exist");
        }

        // Budget is now calculated dynamically from Work entities
        // No manual budget updates needed - totals are computed in real-time

        return workRepository.save(work);
    }



    @Override
    protected Class<Work> getEntityClass() {
        return Work.class;
    }
}
