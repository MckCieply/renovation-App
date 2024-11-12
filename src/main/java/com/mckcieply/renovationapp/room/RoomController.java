package com.mckcieply.renovationapp.room;

import com.mckcieply.core.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing rooms in the renovation application.
 * Provides RESTful endpoints for room-related operations.
 * Extends the BaseController to inherit common CRUD functionalities.
 */
@RestController
@RequestMapping("api/rooms")
@CrossOrigin(origins = "http://localhost:4200")
public class RoomController extends BaseController<Room, Long> {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        super(roomService);
        this.roomService = roomService;
    }

    /**
     * Filters a list of {@link Room} entities based on various optional parameters provided as query parameters.
     *
     * @param filter       the {@link RoomFilter} model attribute automatically populated with the query parameters if name is matched
     * @return             a {@link ResponseEntity} containing the filtered list of {@link Room} entities if available,
     *                     or a 204 No Content response if no matching records are found
     */
    @GetMapping("/filter")
    public ResponseEntity<List<Room>> getFiltered(@ModelAttribute RoomFilter filter) {

        List<Room> filteredRooms = roomService.getFiltered(filter);
        if (filteredRooms.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(filteredRooms, HttpStatus.OK);
    }

}
