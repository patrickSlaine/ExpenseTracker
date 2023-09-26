package com.example.BudgetTracker.controller;

import com.example.BudgetTracker.model.entities.Expense;
import com.example.BudgetTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public List<Expense> getAll() {
        return expenseService.getAllExpenses();
    }

    @GetMapping("/{id}")
    public Expense getById(@PathVariable Long id) {
        try {
            return expenseService.getExpenseById(id);
        } catch (ExpenseNotFoundException exception) {
            throw new ResponseStatusException(NOT_FOUND, exception.getMessage());
        }
    }

    @PostMapping
    public Expense post(@Valid @RequestBody Expense expense) {
        return expenseService.saveExpense(expense);
    }

    @PutMapping("/{id}")
    public Expense put(@PathVariable Long id, @Valid @RequestBody Expense expense) {
        try {
            return expenseService.updateExpense(id, expense);
        } catch (ExpenseNotFoundException exception) {
            throw new ResponseStatusException(NOT_FOUND, exception.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            expenseService.deleteExpense(id);
        } catch (ExpenseNotFoundException exception) {
            throw new ResponseStatusException(NOT_FOUND, exception.getMessage());
        }
    }
}
