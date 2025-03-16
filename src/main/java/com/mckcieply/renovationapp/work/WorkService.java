package com.mckcieply.renovationapp.work;

import com.mckcieply.core.BaseService;
import com.mckcieply.renovationapp.budget.BudgetService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Service class for managing works in the renovation application.
 * Provides methods for work operations such as retrieval and processing.
 * Extends BaseService to inherit common CRUD functionalities.
 */
@Service
public class WorkService extends BaseService<Work, Long> {

    private final WorkRepository workRepository;

    @Autowired
    private BudgetService budgetService;

    public WorkService(WorkRepository workRepository) {
        super(workRepository);
        this.workRepository = workRepository;
    }

    @Transactional
    @Override
    public Work update(Work work){
        Optional<Work> optionalOldWork = workRepository.findById(work.getId());

        if(optionalOldWork.isEmpty()){
            throw new IllegalArgumentException("Work with id " + work.getId() + " does not exist");
        }

        Work oldWork = optionalOldWork.get();

        // If estimated costs are changed update planned budget
        if (oldWork.getEstimatedCosts() != work.getEstimatedCosts()) {
            double difference = work.getEstimatedCosts() - oldWork.getEstimatedCosts();
            budgetService.updateAllocatedBudget(difference);
        }

        // If its flagged as paid move from allocated to spent
        if(!oldWork.isPaid() && work.isPaid()){
            budgetService.moveFromAllocatedToSpent(oldWork.getEstimatedCosts(), work.getFinalCosts());

        }


        // Unmarked as paid
        if(oldWork.isPaid() && !work.isPaid()){
            budgetService.moveFromSpentToAllocated(oldWork.getFinalCosts(), work.getEstimatedCosts());
        }

        return workRepository.save(work);
    }

    @Transactional
    @Override
    public Work add(Work work){

        if(work.isPaid()){
            budgetService.updateSpentBudget(work.getFinalCosts());
        } else if(work.getEstimatedCosts() != 0){
            budgetService.updateAllocatedBudget(work.getEstimatedCosts());
        }

        return workRepository.save(work);
    }

    @Override
    protected Class<Work> getEntityClass() {
        return Work.class;
    }
}
