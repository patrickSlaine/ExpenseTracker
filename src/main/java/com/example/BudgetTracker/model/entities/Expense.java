package com.example.BudgetTracker.model.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

@Entity
@Table(name="expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private Long expenseId;
    @NotNull(message = "'amount' is mandatory")
    @Positive(message = "'amount' must be greater than zero")
    private double amount;
    @Column(name = "date", nullable = false)
    @NotNull(message = "'date' is mandatory")
    private Date date;
    @Column(name="category", nullable=false)
    @NotBlank(message="'category' is mandatory")
    private String category;
    @Column(name="description")
    private String description;

    public Expense(Long expenseId, double amount, Date date, String category,String description) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.description = description;
    }

    public Expense(){

    }

    public Long getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Long expenseId) {
        this.expenseId = expenseId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
