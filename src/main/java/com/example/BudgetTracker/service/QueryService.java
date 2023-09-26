package com.example.BudgetTracker.service;

import com.example.BudgetTracker.model.entities.Budget;
import com.example.BudgetTracker.model.entities.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QueryService {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private BudgetService budgetService;

    public QueryService(ExpenseService expenseService, BudgetService budgetService) {
        this.expenseService = expenseService;
        this.budgetService = budgetService;
    }
    public List<Expense> getAllExpenses() {
        return expenseService.getAllExpenses();
    }
    public List<Budget> getAllBudgets() {
        return budgetService.getAllBudgets();
    }

    public List<Map<String, Object>> getCategorySummaries() {
        List<Budget> budgets = budgetService.getAllBudgets();

        return budgets.stream().map(budget -> {
            Map<String, Object> summary = Map.of(
                    "category", budget.getCategory(),
                    "budgetAmount", budget.getMonthlyLimit()
            );

            List<Expense> expenses = expenseService.getExpensesByCategory(budget.getCategory());
            double totalExpenses = expenses.stream().mapToDouble(Expense::getAmount).sum();
            double remainingBudget = budget.getMonthlyLimit() - totalExpenses;

            summary.put("totalExpenses", totalExpenses);
            summary.put("remainingBudget", remainingBudget);

            return summary;
        }).collect(Collectors.toList());
    }
    public Map<String, Double> getTotalExpensesByCategory() {
        List<Expense> expenses = expenseService.getAllExpenses();

        Map<String, Double> totalExpensesByCategory = expenses.stream()
                .collect(Collectors.groupingBy(Expense::getCategory,
                        Collectors.summingDouble(Expense::getAmount)));

        return totalExpensesByCategory;
    }


}






