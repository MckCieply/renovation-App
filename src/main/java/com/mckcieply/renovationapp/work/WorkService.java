package com.mckcieply.renovationapp.work;

import com.mckcieply.core.BaseService;
import org.springframework.stereotype.Service;

@Service
public class WorkService extends BaseService<Work, Long> {

    private final WorkRepository workRepository;

    public WorkService(WorkRepository workRepository) {
        super(workRepository);
        this.workRepository = workRepository;
    }
}
