package com.example.BudgetTracker.controller;

import com.example.BudgetTracker.model.entities.Expense;
import com.example.BudgetTracker.model.exceptions.ExpenseNotFoundException;
import com.example.BudgetTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import static org.springframework.http.HttpStatus.NOT_FOUND;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Optional<Expense> expenseOptional = expenseService.getExpenseById(id);
        if (expenseOptional.isPresent()) {
            return expenseOptional.get();
        } else {
            throw new ExpenseNotFoundException("Expense not found with id: " + id);
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
    @GetMapping("/api/expenses-by-category")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@RequestParam String category) {
        List<Expense> expenses = expenseService.getExpensesByCategory(category);
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }


    @GetMapping("/sorted-by-amount")
    public ResponseEntity<List<Expense>> getExpensesSortedByAmount() {
        List<Expense> expenses = expenseService.getExpensesSortedByAmount();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }
}
