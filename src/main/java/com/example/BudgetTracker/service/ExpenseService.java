package com.example.BudgetTracker.service;

import com.example.BudgetTracker.model.entities.Expense;
import com.example.BudgetTracker.model.exceptions.ExpenseNotFoundException;
import com.example.BudgetTracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;


import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public ExpenseService(ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }


    public void deleteExpense(Long id) {
        Optional<Expense> expenseOptional = expenseRepository.findById(id);

        if (expenseOptional.isPresent()) {
            expenseRepository.deleteById(id);
        } else {
            throw new ExpenseNotFoundException("Expense not found with ID: " + id);
        }
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {
        Optional<Expense> existingExpenseOptional = expenseRepository.findById(id);

        if (existingExpenseOptional.isPresent()) {
            Expense existingExpense = existingExpenseOptional.get();
            existingExpense.setAmount(updatedExpense.getAmount());
            existingExpense.setDate(updatedExpense.getDate());
            existingExpense.setCategory(updatedExpense.getCategory());
            existingExpense.setDescription(updatedExpense.getDescription());
            return expenseRepository.save(existingExpense);
        } else {
            throw new ExpenseNotFoundException("Expense not found with ID: " + id);
        }


    }
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    };

    public List<Expense> getExpensesSortedByAmount() {
        Sort sort = Sort.by(Sort.Order.asc("amount"));
        return expenseRepository.findAll(sort);
    }


    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);

    }

    public Expense saveExpense(Expense expense) {
        Long id = expense.getExpenseId(); // Assuming ID is a unique identifier

        // Check if an expense with the same ID already exists
        if (expenseRepository.existsById(id)) {
            throw new RuntimeException("Expense with the same ID already exists.");
        }

        try {
            // Attempt to save the expense
            Expense savedExpense = expenseRepository.save(expense);
            return savedExpense;
        } catch (Exception e) {
            // Handle other exceptions
            throw new RuntimeException("Error occurred while saving the expense.", e);
        }
    }

    @Transactional
    public void deleteAllExpenses() {
        expenseRepository.deleteAll();
    }



}
