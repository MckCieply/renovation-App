package com.mckcieply.renovationapp.work;

import com.mckcieply.core.BaseController;
import com.mckcieply.renovationapp.enumerable.EnumWorkState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for managing works in the renovation application.
 * Provides RESTful endpoints for work-related operations.
 * Extends the BaseController to inherit common CRUD functionalities.
 */
@RestController
@RequestMapping("api/works")
@CrossOrigin(origins = "http://localhost:4200")
public class WorkController extends BaseController<Work, Long> {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        super(workService);
        this.workService = workService;
    }

    /**
     * Retrieves all possible work states.
     *
     * @return a ResponseEntity containing an array of EnumWorkState values
     */
    @GetMapping("/getEnumWorkState")
    public ResponseEntity<EnumWorkState[]> getEnumWorkState() {
        return new ResponseEntity<>(EnumWorkState.values(), HttpStatus.OK);
    }
}
