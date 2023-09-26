package com.example.BudgetTracker.service;

import com.example.BudgetTracker.model.entities.Expense;
import com.example.BudgetTracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Expense saveExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
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

            return null;
        }
    }
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    };
}
