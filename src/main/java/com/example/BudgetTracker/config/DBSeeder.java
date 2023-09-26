package com.example.BudgetTracker.config;

import com.example.BudgetTracker.model.entities.Budget;

import com.example.BudgetTracker.model.entities.Expense;
import com.example.BudgetTracker.repository.BudgetRepository;
import com.example.BudgetTracker.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Date;
import java.util.List;

@Configuration
@Profile({"dbseeder"})
public class DBSeeder implements CommandLineRunner {

    public static final List<Budget> DB_SEEDER_BUDGET = List.of(
            new Budget(null, "Food",1500),
            new Budget(null, "Drink",200),
            new Budget(null, "Fuel",300),
            new Budget(null, "Housing",1000)
    );

    public static final List<Expense> DB_SEEDER_EXPENSE = List.of(
            new Expense(null, 1500, new Date(), "Food", "I like food") ,
            new Expense(null, 250, new Date(), "Drink", "Starbucks for life") ,
            new Expense(null, 200, new Date(), "Fuel", "Car") ,
            new Expense(null, 1000, new Date(), "Food", "Rent payment")
    );

    @Autowired
    private BudgetRepository budgetRepository;


    @Autowired
    private ExpenseRepository expenseRepository;

    @Override
    public void run(String... args) throws Exception{
        budgetRepository.saveAll(DB_SEEDER_BUDGET);
        expenseRepository.saveAll(DB_SEEDER_EXPENSE);
    }





}
