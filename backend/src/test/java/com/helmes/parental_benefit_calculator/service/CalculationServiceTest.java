package com.helmes.parental_benefit_calculator.service;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculationServiceTest {
    private CalculationService service;

    @BeforeEach
    void setUp() {
        this.service = new CalculationService();
    }        

    @Test
    void shouldCapSalaryWhenAboveLimit() {
        BigDecimal grossSalary = BigDecimal.valueOf(5000);
        BigDecimal cappedSalary = service.calculateCappedSalary(grossSalary);
        assertEquals(BigDecimal.valueOf(4000).setScale(2), cappedSalary);
    }
}