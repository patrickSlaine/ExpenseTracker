package com.example.BudgetTracker.model.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


@Entity
@Table(name="budgets")
public class Budget {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",nullable = false)
    private Long budgetId;

    @Column(name="category", nullable = false)
    @NotBlank
    private String category;

    @Column(name="monthly_limit", nullable = false)
    @NotNull
    @Positive
    private double monthlyLimit;

    public Budget(Long budgetId, String category, double monthlyLimit) {
        this.budgetId = budgetId;
        this.category = category;
        this.monthlyLimit = monthlyLimit;
    }

    public Budget(){

    }

    public Long getBudgetId() {
        return budgetId;
    }

    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(double monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }
}
