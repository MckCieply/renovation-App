package com.mckcieply.renovationapp.room;

import com.mckcieply.core.BaseController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
