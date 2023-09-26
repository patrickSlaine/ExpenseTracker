package com.example.BudgetTracker.controller;


import com.example.BudgetTracker.model.entities.Budget;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles({})
public class BudgetControllerTest {

    private static final String BUDGET_ENDPOINT_URL = "/api/budget";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

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
        assertEquals(DB_SEEDER_BUDGETS.size(),budgetsFromDB.size());
    }

}
