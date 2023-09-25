package com.example.BudgetTracker.model.entities;


import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotBlank;

import java.util.Date;

@Entity
@Table(name="expenses")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", nullable = false)
    private int expenseId;
    @Column(name="amount",nullable = false)
    @NotBlank(message="'name' is mandatory")
    private double amount;
    @Column(name="date", nullable=false)
    @NotBlank(message="'date' is mandatory")
    private Date date;
    @Column(name="category", nullable=false)
    @NotBlank(message="'category' is mandatory")
    private String category;
    @Column(name="description")
    private String description;

    public Expense(int expenseId, double amount, Date date, String category,String description) {
        this.expenseId = expenseId;
        this.amount = amount;
        this.date = date;
        this.category = category;
        this.description = description;
    }

    public Expense(){

    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
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
