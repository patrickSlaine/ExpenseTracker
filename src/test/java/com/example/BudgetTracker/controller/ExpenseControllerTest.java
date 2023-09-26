package com.example.BudgetTracker.controller;

import com.example.BudgetTracker.model.entities.Expense;
import com.example.BudgetTracker.model.exceptions.ExpenseNotFoundException;
import com.example.BudgetTracker.service.ExpenseService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;


import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"h2","dbseeder"})
class ExpenseControllerTest {

    private static final String EXPENSE_ENDPOINT_URL = "/api/expenses";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExpenseService expenseService;

    @Test
    public void testGETAll() throws Exception {
        String JSON = mockMvc.perform(
                        get("/api/expenses")
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<Expense> expensesFromResponse = objectMapper.readValue(JSON, List.class);

        assertNotNull(expensesFromResponse);
        assertFalse(expensesFromResponse.isEmpty());
    }

    @Test
    @DirtiesContext
    public void testPOST_Success() throws Exception {

        Expense expense = new Expense(null, 200, new Date(), "Fuel", "Car");

        String JSONToSent = objectMapper.writeValueAsString(expense);

        mockMvc.perform(post("/api/expenses")
                        .contentType("application/json")
                        .content(JSONToSent)
                )
                .andExpect(status().isOk());

        List<Expense> expenses = expenseService.getAllExpenses();
        assertFalse(expenses.isEmpty());
        Expense savedExpense = expenses.get(expenses.size() - 1);
        assertNotNull(savedExpense.getExpenseId());
        assertEquals(expense.getAmount(), savedExpense.getAmount());
        assertEquals(expense.getCategory(), savedExpense.getCategory());
        assertEquals(expense.getDescription(), savedExpense.getDescription());
    }


    @Test
    @DirtiesContext
    public void testPUT_Success() throws Exception {

        Expense existingExpense = new Expense(null, 1500, new Date(), "Food", "I like food");
        expenseService.saveExpense(existingExpense);

        Expense updatedExpense = new Expense(existingExpense.getExpenseId(), 2000, new Date(), "Updated Food", "Updated description");
        String JSONToSend = objectMapper.writeValueAsString(updatedExpense);
        String JSONFromResponse = mockMvc.perform(put("/api/expenses/{id}", existingExpense.getExpenseId())
                        .contentType("application/json")
                        .content(JSONToSend)
                )
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Expense updatedExpenseFromResponse = objectMapper.readValue(JSONFromResponse, Expense.class);

        assertEquals(existingExpense.getExpenseId(), updatedExpenseFromResponse.getExpenseId());
        assertEquals(updatedExpense.getAmount(), updatedExpenseFromResponse.getAmount());
        assertEquals(updatedExpense.getCategory(), updatedExpenseFromResponse.getCategory());
        assertEquals(updatedExpense.getDescription(), updatedExpenseFromResponse.getDescription());
    }

}