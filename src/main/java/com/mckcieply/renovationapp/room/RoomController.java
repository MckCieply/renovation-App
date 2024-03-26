package com.mckcieply.renovationapp.room;

import com.mckcieply.core.BaseController;
import org.springframework.web.bind.annotation.*;

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
