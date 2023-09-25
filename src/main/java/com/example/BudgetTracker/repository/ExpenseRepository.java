package com.example.BudgetTracker.repository;

import com.example.BudgetTracker.model.entities.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}
