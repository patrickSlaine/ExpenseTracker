package com.example.BudgetTracker.config;


import com.example.BudgetTracker.model.entities.Budget;
import com.example.BudgetTracker.repository.BudgetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

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

    @Autowired
    private BudgetRepository budgetRepository;

    @Override
    public void run(String... args) throws Exception{
        budgetRepository.saveAll(DB_SEEDER_BUDGET);
    }

}
