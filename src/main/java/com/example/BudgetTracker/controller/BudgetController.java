package com.example.BudgetTracker.controller;

import com.example.BudgetTracker.model.entities.Budget;
import com.example.BudgetTracker.service.BudgetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/api/budget")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping
    public List<Budget> getAll(){
        return budgetService.getAllBudgets();
    }

    @GetMapping("/{id}")
    public Budget getById(@PathVariable Long id){
        return budgetService.getBudgetById(id);
    }

    @PostMapping
    public Budget post (@Valid @RequestBody Budget budget){
        try{
            return budgetService.createBudget(budget);
        }
        catch(Exception exception){
            throw new ResponseStatusException(NOT_FOUND, exception.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Budget put(@PathVariable Long id, @Valid @RequestBody Budget budget){
        return budgetService.updateBudget(id,budget);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        budgetService.deleteBudget(id);
    }
}
