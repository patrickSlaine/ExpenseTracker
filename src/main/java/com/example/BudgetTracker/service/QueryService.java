<<<<<<< HEAD
//package com.example.BudgetTracker.service;
//
//import com.example.BudgetTracker.repository.BudgetRepository;
//import com.example.BudgetTracker.repository.ExpenseRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Dictionary;
//
//@Service
//public class QueryService {
//
//    @Autowired
//    private BudgetRepository budgetRepository;
//
//    @Autowired
//    private ExpenseRepository expenseRepository;
//
//    public QueryService(BudgetRepository budgetRepository, ExpenseRepository expenseRepository){
//        this.budgetRepository = budgetRepository;
//        this.expenseRepository= expenseRepository;
//    }
//
//    public Dictionary getExpenseInfo(String category){
//
//    }
//}
=======
package com.example.BudgetTracker.service;

import com.example.BudgetTracker.model.entities.Budget;
import com.example.BudgetTracker.model.entities.Expense;
import org.springframework.beans.factory.annotation.Autowired;
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
}





>>>>>>> bec1781 (Query)
