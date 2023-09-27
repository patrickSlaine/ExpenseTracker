package com.example.BudgetTracker.repository;

import com.example.BudgetTracker.model.entities.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BudgetRepository extends JpaRepository<Budget,Long> {
    List<Budget> findBudgetsByCategory(String category);

    @Transactional
    void deleteAll();
}
