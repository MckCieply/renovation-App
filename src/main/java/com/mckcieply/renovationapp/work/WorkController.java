package com.mckcieply.renovationapp.work;

import com.mckcieply.core.BaseController;
import com.mckcieply.renovationapp.enumerable.EnumWorkState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    /**
     * Filters a list of {@link Work} entities based on various optional parameters provided as query parameters.
     *
     * @param filter       the {@link WorkFilter} model attribute automatically populated with the query parameters if name is matched
     * @return             a {@link ResponseEntity} containing the filtered list of {@link Work} entities if available,
     *                     or a 204 No Content response if no matching records are found
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Work>> getFiltered(@ModelAttribute WorkFilter filter) {

        List<Work> filteredWorks = workService.getFiltered(filter);
        if (filteredWorks.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(filteredWorks);
    }
}
