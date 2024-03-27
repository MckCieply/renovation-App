package com.mckcieply.renovationapp.work;

import com.mckcieply.core.BaseController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/works")
public class WorkController extends BaseController<Work, Long> {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        super(workService);
        this.workService = workService;
    }
}
