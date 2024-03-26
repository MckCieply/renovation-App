package com.mckcieply.renovationapp.workType;

import com.mckcieply.core.BaseController;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/work-types")
@CrossOrigin(origins = "http://localhost:4200")
public class WorkTypeController extends BaseController<WorkType, Long> {

    private final WorkTypeService workTypeService;

    public WorkTypeController(WorkTypeService workTypeService) {
        super(workTypeService);
        this.workTypeService = workTypeService;
    }

}
