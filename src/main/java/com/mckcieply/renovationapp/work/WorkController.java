package com.mckcieply.renovationapp.work;

import com.mckcieply.core.BaseController;
import com.mckcieply.renovationapp.enumerable.EnumWorkState;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;

@RestController
@RequestMapping("api/works")
@CrossOrigin(origins = "http://localhost:4200")
public class WorkController extends BaseController<Work, Long> {

    private final WorkService workService;

    public WorkController(WorkService workService) {
        super(workService);
        this.workService = workService;
    }

    @Override
    @PostMapping("/add")
    public ResponseEntity<Work> add(@RequestBody Work work) {
        work.setCreatedAt(new Date(System.currentTimeMillis()));
        return super.add(work);
    }

    @GetMapping("/getEnumWorkState")
    public ResponseEntity<EnumWorkState[]> getEnumWorkState() {
        return new ResponseEntity<>(EnumWorkState.values(), HttpStatus.OK);
    }
}
