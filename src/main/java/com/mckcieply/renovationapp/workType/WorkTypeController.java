package com.mckcieply.renovationapp.workType;

import com.mckcieply.renovationapp.room.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-types")
@CrossOrigin(origins = "http://localhost:4200")
public class WorkTypeController {

    @Autowired
    private WorkTypeService workTypeService;

    @GetMapping("/all")
    public ResponseEntity<List<WorkType>> getAllWorkTypes(){
        List<WorkType> workTypes = workTypeService.getAllWorkTypes();
        return new ResponseEntity<>(workTypes, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<WorkType> addWorkType(@RequestBody WorkType workType){
        workTypeService.addWorkType(workType);
        return new ResponseEntity<>(workType, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteWorkType(@PathVariable("id") Long id){
        workTypeService.deleteWorkType(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<WorkType> updateWorkType(@RequestBody WorkType workType){
        workTypeService.updateWorkType(workType);
        return new ResponseEntity<>(workType, HttpStatus.OK);
    }
}
