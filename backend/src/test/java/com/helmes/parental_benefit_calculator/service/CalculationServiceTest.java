package com.helmes.parental_benefit_calculator.service;

import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import com.helmes.parental_benefit_calculator.dto.MonthlyPayment;

class CalculationServiceTest {
  private CalculationService service;
  private static final BigDecimal SALARY_CAP = BigDecimal.valueOf(4000);

  @BeforeEach
  void setUp() {
    this.service = new CalculationService();
  }        
  
  @Nested
  class CalculateCappedSalaryTests {
    @Test
    void shouldCapSalaryWhenAboveLimit() {
      BigDecimal grossSalary = BigDecimal.valueOf(5000);
      BigDecimal cappedSalary = service.calculateCappedSalary(grossSalary);
      assertEquals(0, SALARY_CAP.compareTo(cappedSalary));
    }

    @Test
    void shouldReturnSalaryWhenAtLimit() {
      BigDecimal grossSalary = BigDecimal.valueOf(4000);
      BigDecimal cappedSalary = service.calculateCappedSalary(grossSalary);
      assertEquals(0, grossSalary.compareTo(cappedSalary));
    }

    @Test
    void shouldReturnSalaryWhenBelowLimit() {
      BigDecimal grossSalary = BigDecimal.valueOf(3000);
      BigDecimal cappedSalary = service.calculateCappedSalary(grossSalary);
      assertEquals(0, grossSalary.compareTo(cappedSalary));
    }
  }

  @Nested
  class CalculateDailyRateTests {
    @Test
    void shouldDivideSalaryByThirty() {
      BigDecimal cappedSalary = BigDecimal.valueOf(3000);

      BigDecimal actualDailyRate = service.calculateDailyRate(cappedSalary);

      BigDecimal expectedDailyRate = BigDecimal.valueOf(100);
      assertEquals(0, expectedDailyRate.compareTo(actualDailyRate));
    }

    @Test
    void shouldReturnZeroWhenSalaryIsZero() {
      BigDecimal cappedSalary = BigDecimal.ZERO;

      BigDecimal actualDailyRate = service.calculateDailyRate(cappedSalary);

      BigDecimal expectedDailyRate = BigDecimal.ZERO;
      assertEquals(0, expectedDailyRate.compareTo(actualDailyRate));
    }
  }

  @Nested 
  class CalculateMonthlyPaymentsTests {
    @Test
    void shouldReturnTwelveMonthlyPayments() {
      BigDecimal dailyRate = BigDecimal.valueOf(100);
      LocalDate birthDate = LocalDate.of(2026, 1, 1);

      List<MonthlyPayment> payments = service.calculateMonthlyPayments(dailyRate, birthDate);

      assertEquals(12, payments.size());
    }

    @Test
    void shouldReturnPartialFirstMonthPayment() {
      BigDecimal dailyRate = BigDecimal.valueOf(100);
      LocalDate birthDate = LocalDate.of(2026, 4, 21);

      List<MonthlyPayment> payments = service.calculateMonthlyPayments(dailyRate, birthDate);

      BigDecimal actualFirstMonthPayment = payments.get(0).getPayment();
      BigDecimal expectedFirstMonthPayment = BigDecimal.valueOf(1000);
      assertEquals(0, expectedFirstMonthPayment.compareTo(actualFirstMonthPayment));
    }

    @Test
    void shouldReturnSingleDayPaymentWhenBirthOnLastDay() {
      BigDecimal dailyRate = BigDecimal.valueOf(100);
      LocalDate birthDate = LocalDate.of(2026, 4, 30);

      List<MonthlyPayment> payments = service.calculateMonthlyPayments(dailyRate, birthDate);

      BigDecimal actualFirstMonthPayment = payments.get(0).getPayment();
      BigDecimal expectedFirstMonthPayment = BigDecimal.valueOf(100);
      assertEquals(0, expectedFirstMonthPayment.compareTo(actualFirstMonthPayment));
    }

    @Test
    void shouldReturnFullMonthPaymentWhenBirthOnFirstDay() {
      BigDecimal dailyRate = BigDecimal.valueOf(100);
      LocalDate birthDate = LocalDate.of(2026, 4, 1);

      List<MonthlyPayment> payments = service.calculateMonthlyPayments(dailyRate, birthDate);

      BigDecimal actualFirstMonthPayment = payments.get(0).getPayment();
      BigDecimal expectedFirstMonthPayment = BigDecimal.valueOf(3000);
      assertEquals(0, expectedFirstMonthPayment.compareTo(actualFirstMonthPayment));
    }

    @Test 
    void shouldCalculateCorrectPaymentForThirtyDayMonth() {
      BigDecimal dailyRate = BigDecimal.valueOf(100);
      LocalDate birthDate = LocalDate.of(2026, 5, 15);

      List<MonthlyPayment> payments = service.calculateMonthlyPayments(dailyRate, birthDate);

      BigDecimal actualThirtyDayMonthPayment = payments.get(1).getPayment();
      BigDecimal expectedThirtyDayMonthPayment = BigDecimal.valueOf(3000);
      assertEquals(0, expectedThirtyDayMonthPayment.compareTo(actualThirtyDayMonthPayment));
    }

    @Test
    void shouldReturnCorrectFebruaryPaymentInLeapYear() {
      BigDecimal dailyRate = BigDecimal.valueOf(100);
      LocalDate birthDate = LocalDate.of(2024, 2, 1);

      List<MonthlyPayment> payments = service.calculateMonthlyPayments(dailyRate, birthDate);
      
      BigDecimal actualLeapYearFebruaryPayment = payments.get(0).getPayment();
      BigDecimal expectedLeapYearFebruaryPayment = BigDecimal.valueOf(2900);
      assertEquals(0, expectedLeapYearFebruaryPayment.compareTo(actualLeapYearFebruaryPayment));            
    }
  }
}