package com.example.BudgetTracker;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"h2","dbseeder"})
class BudgetTrackerApplicationTests {

	@Test
	void contextLoads() {
	}

}
