package com.example.BudgetTracker.service;

import com.example.BudgetTracker.model.entities.Budget;
import com.example.BudgetTracker.model.exceptions.BudgetNotFoundException;
import com.example.BudgetTracker.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public Budget getBudgetById(Long id) {
        return budgetRepository.findById(id).orElseThrow(()-> new BudgetNotFoundException("No Budget with Id: "+ id));
    }

    public Budget createBudget(Budget budget) {
        return budgetRepository.save(budget);
    }

    public Budget updateBudget(Long id, Budget updatedBudget) {
        Optional<Budget> existingBudget = budgetRepository.findById(id);
        if (existingBudget.isPresent()) {
            Budget budget = existingBudget.get();
            budget.setCategory(updatedBudget.getCategory());
            budget.setMonthlyLimit(updatedBudget.getMonthlyLimit());
            return budgetRepository.save(budget);
        } else {
            throw new BudgetNotFoundException("No Budget with Id: "+ id);
        }
    }

    public void deleteBudget(Long id) {
            if(!budgetRepository.findById(id).isPresent()){
                throw new BudgetNotFoundException("No Budget with Id: "+ id);
            }
            budgetRepository.deleteById(id);
    }

    public List<Budget> getBudgetsByCategory(String category) {
        return budgetRepository.findBudgetsByCategory(category);
    }

    @Transactional
    public void deleteAllBudgets() {
        budgetRepository.deleteAll();
    }
}

