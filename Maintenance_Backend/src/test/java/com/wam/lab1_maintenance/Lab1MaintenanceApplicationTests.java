package com.wam.lab1_maintenance;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
class Lab1MaintenanceApplicationTests {

    @Test
    void contextLoads() {
        assertEquals(true, true);
    }

}
