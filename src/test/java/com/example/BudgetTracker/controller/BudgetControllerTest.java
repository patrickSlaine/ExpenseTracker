package com.example.BudgetTracker.controller;


import com.example.BudgetTracker.model.entities.Budget;
import com.example.BudgetTracker.repository.BudgetRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({"h2","dbseeder"})
public class BudgetControllerTest {

    private static final String BUDGET_ENDPOINT_URL = "/api/budget";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private BudgetRepository budgetRepository;

    @Test
    public void testGetAll() throws Exception{

        String JSON = mockMvc.perform(
                get(BUDGET_ENDPOINT_URL)
        ).andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<Budget> budgetsFromDB = objectMapper.readValue(JSON, new TypeReference<>() {
        });

        assertFalse(budgetsFromDB.isEmpty());
        assertEquals(budgetRepository.count(),budgetsFromDB.size());
    }

    @Test
    public void testGETById_Success() throws Exception {
        // Given an existing id
        int testId = 2;

        // When
        String JSON = this.mockMvc.perform(get(BUDGET_ENDPOINT_URL + "/" + testId))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Budget budget = objectMapper.readValue(JSON, Budget.class);

        // Then
        assertNotNull(budget);
        assertEquals(testId, budget.getBudgetId());
    }

    @Test
    public void testGETById_Failure() throws Exception {
        // Given a non-existing id
        int testId = 10000;

        // When
        this.mockMvc.perform(get(   BUDGET_ENDPOINT_URL + "/" + testId))
                .andDo(print())
                // Then
                .andExpect(status().isNotFound());
    }


    @Test
    // After the test, restore database to initial state
    @DirtiesContext
    public void testPOST_success() throws Exception {
        // Given
        String testCategory = "Food";

        Budget testBudget = new Budget(null, testCategory, 200.00);
        String JSONToSent = objectMapper.writeValueAsString(testBudget);

        List<Budget> before = getAllShippers();

        // When
        String JSONReceived = this.mockMvc.perform(post(BUDGET_ENDPOINT_URL)
                        .header("Content-Type", "application/json")
                        .content(JSONToSent))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Budget budget = objectMapper.readValue(JSONReceived, Budget.class);

        // Then
        List<Budget> after = getAllShippers();
        assertEquals(before.size(), after.size() - 1);
        assertNotNull(budget);
        assertEquals(testCategory, budget.getCategory());
        assertEquals(200.00, budget.getMonthlyLimit());
    }

    @Test
    // After the test, restore database to initial state
    @DirtiesContext
    void testPOST_failure() throws Exception {
        // Given
        String testCategory = "";

        Budget testShipper = new Budget(null, testCategory, -100.00);
        String JSONToSent = objectMapper.writeValueAsString(testShipper);


        // When
        this.mockMvc.perform(post(BUDGET_ENDPOINT_URL)
                        .header("Content-Type", "application/json")
                        .content(JSONToSent))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    @DirtiesContext
    public void testPUT_success() throws Exception {
        //  Given DB was populated by DBSeeder

        long existingId = 2;
        String newCategory = "Food";

        Budget budgetToUpdate = new Budget(null, newCategory, 200.00);
        String JSONToSend = objectMapper.writeValueAsString(budgetToUpdate);

        List<Budget> before = getAllShippers();

        // When
        String JSONFromResponse = this.mockMvc.perform(put(BUDGET_ENDPOINT_URL + "/" + existingId)
                        .header("Content-Type", "application/json")
                        .content(JSONToSend))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Then
        List<Budget> after = getAllShippers();
        Budget shipper1FromDBAfterUpdate = objectMapper.readValue(JSONFromResponse, Budget.class);
        assertEquals(newCategory, shipper1FromDBAfterUpdate.getCategory());
        assertEquals(200.00, shipper1FromDBAfterUpdate.getMonthlyLimit());
        assertEquals(before.size(), after.size());
    }

    @Test
    public void testPUT_failure_nonExitingId() throws Exception {
        //  Given DB was populated by DBSeeder

        long nonExistingId = 1000;
        String newCategory = "Food";

        Budget budgetToUpdate = new Budget(null, newCategory, 200.00);
        String JSONToSend = objectMapper.writeValueAsString(budgetToUpdate);

        // When
        this.mockMvc.perform(put(BUDGET_ENDPOINT_URL + "/" + nonExistingId)
                        .header("Content-Type", "application/json")
                        .content(JSONToSend))
                .andDo(print())
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    @DirtiesContext
    public void testDELETE_Success() throws Exception {
        //  Given DB was populated by DBSeeder
        long existingID = 1;

        // When
        this.mockMvc.perform(delete(BUDGET_ENDPOINT_URL + "/" + existingID))
                .andDo(print())
                // Then
                .andExpect(status().isOk());

    }
    @Test
    public void testDELETE_Failure() throws Exception {
        //  Given DB was populated by DBSeeder
        long nonExistingID = 10000;

        // When
        this.mockMvc.perform(delete(BUDGET_ENDPOINT_URL + "/" + nonExistingID))
                .andDo(print())
                // Then
                .andExpect(status().isNotFound());
    }


    private List<Budget> getAllShippers() throws Exception {
        String JSON = mockMvc.perform(get(BUDGET_ENDPOINT_URL))
                .andReturn()
                .getResponse()
                .getContentAsString();
        return objectMapper.readValue(JSON, new TypeReference<>() {
        });
    }

}
