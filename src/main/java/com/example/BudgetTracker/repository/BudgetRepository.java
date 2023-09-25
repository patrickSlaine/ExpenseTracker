package com.example.BudgetTracker.repository;

import com.example.BudgetTracker.model.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
}
