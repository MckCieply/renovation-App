package com.mckcieply.renovationapp.budget;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public Budget getBudget(){
        return budgetRepository.findAll().get(0);
    }

    public void updateBudget(Budget budget){
        budgetRepository.save(budget);
    }
}
