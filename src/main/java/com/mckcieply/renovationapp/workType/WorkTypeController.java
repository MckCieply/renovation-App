package com.mckcieply.renovationapp.workType;

import com.mckcieply.core.BaseController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing work types in the renovation application.
 * Provides RESTful endpoints for work type-related operations.
 * Extends the BaseController to inherit common CRUD functionalities.
 */
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
