package com.mckcieply.renovationapp.workType;

import com.mckcieply.core.BaseService;
import org.springframework.stereotype.Service;

@Service
public class WorkTypeService extends BaseService<WorkType, Long> {

    private final WorkTypeRepository workTypeRepository;

    public WorkTypeService(WorkTypeRepository workTypeRepository) {
        super(workTypeRepository);
        this.workTypeRepository = workTypeRepository;
    }

}
