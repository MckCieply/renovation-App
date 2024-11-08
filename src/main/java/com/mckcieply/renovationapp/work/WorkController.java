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
     * @param filter       the {@link WorkFilter} model attribute that will be populated with the provided filtering criteria
     * @param daysCreated  optional number of days since creation to works
     * @param daysUpdated  optional number of days since the last update to works
     * @param createdBy    optional filter by the username of the creator
     * @param updatedBy    optional filter by the username of the last updater
     * @param name         optional filter by the name of the work
     * @param state        optional filter by the {@link EnumWorkState} of the work
     * @param paid         optional filter by the payment status of the work
     * @param roomId       optional filter by the ID of the room associated with the work
     * @param workTypeId   optional filter by the ID of the work type
     * @param description  optional filter by a description, performing a case-insensitive match
     * @return             a {@link ResponseEntity} containing the filtered list of {@link Work} entities if available,
     *                     or a 204 No Content response if no matching records are found
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Work>> getFiltered(@ModelAttribute WorkFilter filter,
                                                  @RequestParam(required = false) Integer daysCreated,
                                                  @RequestParam(required = false) Integer daysUpdated,
                                                  @RequestParam(required = false) String createdBy,
                                                  @RequestParam(required = false) String updatedBy,
                                                  @RequestParam(required = false) String name,
                                                  @RequestParam(required = false) EnumWorkState state,
                                                  @RequestParam(required = false) Boolean paid,
                                                  @RequestParam(required = false) Long roomId,
                                                  @RequestParam(required = false) Long workTypeId,
                                                  @RequestParam(required = false) String description) {

        filter.setDaysCreated(daysCreated);
        filter.setDaysUpdated(daysUpdated);
        filter.setName(name);
        filter.setCreatedBy(createdBy);
        filter.setUpdatedBy(updatedBy);
        filter.setState(state);
        filter.setPaid(paid);
        filter.setRoomId(roomId);
        filter.setWorkTypeId(workTypeId);
        filter.setDescription(description);

        List<Work> filteredWorks = workService.getFiltered(filter);
        if (filteredWorks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(filteredWorks);
    }
}
