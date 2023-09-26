package com.example.BudgetTracker.controller;

import com.example.BudgetTracker.model.entities.Budget;
import com.example.BudgetTracker.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping
    public List<Budget> getAll() {
        return budgetService.getAllBudgets();
    }

    @GetMapping("/{id}")
    public Budget getById(@PathVariable Long id) {
        try{
            return budgetService.getBudgetById(id);
        }
        catch(Exception exception){
            throw new ResponseStatusException(NOT_FOUND,exception.getMessage());
        }
    }

    @PostMapping
    public Budget post(@Valid @RequestBody Budget budget) {
        try {
            return budgetService.createBudget(budget);
        } catch (Exception exception) {
            throw new ResponseStatusException(NOT_FOUND, exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Budget put(@PathVariable Long id, @Valid @RequestBody Budget budget) {
        try{
            return budgetService.updateBudget(id, budget);
        }catch(Exception exception){
            throw new ResponseStatusException(NOT_FOUND, exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            budgetService.deleteBudget(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(NOT_FOUND, "Budget not found with ID: " + id);
        } catch (Exception e) {
            throw new ResponseStatusException(NOT_FOUND, "Error occurred while deleting the budget with ID: " + id, e);
        }
    }

    @GetMapping("/byCategory/{category}")
    public List<Budget> getBudgetsByCategory(@PathVariable String category) {
        try {
            List<Budget> budgets = budgetService.getBudgetsByCategory(category);
            if (budgets.isEmpty()) {
                throw new ResponseStatusException(NOT_FOUND, "No budgets found for the specified category");
            }
            return budgets;
        } catch (Exception e) {
            throw new ResponseStatusException(NOT_FOUND, "Error occurred while fetching budgets by category", e);
        }
    }

}
