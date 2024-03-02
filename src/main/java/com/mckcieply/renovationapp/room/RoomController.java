package com.mckcieply.renovationapp.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "http://localhost:4200")
public class RoomController {

    @Autowired
    private RoomService roomService;


    @GetMapping("/all")
    public ResponseEntity<List<Room>> getAllRooms(){
        List<Room> rooms = roomService.getAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }
}
