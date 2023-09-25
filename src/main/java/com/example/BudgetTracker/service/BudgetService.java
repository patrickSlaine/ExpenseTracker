package com.example.BudgetTracker.service;

import com.example.BudgetTracker.model.entities.Budget;
import com.example.BudgetTracker.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BudgetService {

    private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetService(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    public List<Budget> getAllBudgets() {
        return budgetRepository.findAll();
    }

    public Optional<Budget> getBudgetById(Long id) {
        return budgetRepository.findById(id);
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

            return null;
        }
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }
}

