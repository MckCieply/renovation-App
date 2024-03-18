package com.mckcieply.renovationapp.workType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkTypeService {

    @Autowired
    private WorkTypeRepository workTypeRepository;

    public List<WorkType> getAllWorkTypes(){
        return workTypeRepository.findAll();
    }

    public void addWorkType(WorkType workType){
        workTypeRepository.save(workType);
    }

    public void deleteWorkType(Long id){
        workTypeRepository.deleteById(id);
    }

    public void updateWorkType(WorkType workType){
        workTypeRepository.save(workType);
    }
}
