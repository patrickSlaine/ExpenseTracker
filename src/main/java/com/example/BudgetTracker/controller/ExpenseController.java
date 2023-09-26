package com.example.BudgetTracker.controller;

import com.example.BudgetTracker.model.entities.Budget;
import com.example.BudgetTracker.model.entities.Expense;
import com.example.BudgetTracker.model.exceptions.ExpenseNotFoundException;
import com.example.BudgetTracker.service.BudgetService;
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
    private BudgetService budgetService;

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

    @GetMapping("/expenses/totalByCategory/{category}")
    public double getTotalExpensesByCategory(@PathVariable String category) {
        List<Expense> expenses = expenseService.getExpensesByCategory(category);

        double totalExpenses = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        return totalExpenses;
    }


    @GetMapping("/{category}/availableBudget")
    public double getAvailableBudgetAfterExpenses(@PathVariable String category) {

        List<Expense> expenses = expenseService.getExpensesByCategory(category);

        if (expenses.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "No expenses found for the specified category");
        }

        double totalExpenses = expenses.stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        // Retrieve the budget for the specified category
        List<Budget> budgets = budgetService.getBudgetsByCategory(category);

        if (budgets.isEmpty()) {
            throw new ResponseStatusException(NOT_FOUND, "No budget found for the specified category");
        }

        double monthlyLimit = budgets.get(0).getMonthlyLimit();

        double availableBudget = monthlyLimit - totalExpenses;

        return availableBudget;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        try {
            expenseService.deleteExpense(id);
        } catch (ExpenseNotFoundException exception) {
            throw new ResponseStatusException(NOT_FOUND, exception.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(NOT_FOUND, "Error occurred while deleting the expense with ID: " + id, e);
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

    @DeleteMapping("/deleteAll")
    public String deleteAllExpenses() {
        try {
            expenseService.deleteAllExpenses();
            return "All expenses have been deleted successfully.";
        } catch (Exception e) {
            return "Error occurred: " + e.getMessage();
        }
    }
}
