package com.mckcieply.renovationapp.room;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add")
    public ResponseEntity<Room> addRoom(@RequestBody Room room){
        roomService.addRoom(room);
        return new ResponseEntity<>(room, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRoom(@PathVariable("id") Long id){
        roomService.deleteRoom(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
