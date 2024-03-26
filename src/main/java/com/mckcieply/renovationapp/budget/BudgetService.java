package com.mckcieply.renovationapp.budget;

import com.mckcieply.core.BaseService;
import org.springframework.stereotype.Service;

@Service
public class BudgetService extends BaseService<Budget, Long> {

    private final BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        super(budgetRepository);
        this.budgetRepository = budgetRepository;
    }

    public Budget getBudget(){
        return budgetRepository.findAll().get(0);
    }

}
