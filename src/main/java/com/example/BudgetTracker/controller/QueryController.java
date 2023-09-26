package com.example.BudgetTracker.controller;

import com.example.BudgetTracker.service.QueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/query")

public class QueryController {

    private final QueryService queryService;

    public QueryController(QueryService queryService) {
        this.queryService = queryService;
    }

    @GetMapping("/total-expenses-by-category")
    public ResponseEntity<Map<String, Double>> getTotalExpensesByCategory() {
        Map<String, Double> totalExpensesByCategory = queryService.getTotalExpensesByCategory();
        if (!totalExpensesByCategory.isEmpty()) {
            return ResponseEntity.ok(totalExpensesByCategory);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/category-summaries")
    public List<Map<String, Object>> getCategorySummaries() {
        return queryService.getCategorySummaries();
    }
}
