package com.example.BudgetTracker.repository;

import com.example.BudgetTracker.model.entities.Budget;
import com.example.BudgetTracker.model.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategory(String category);

    @Transactional
    void deleteAll();
}


