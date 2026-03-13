package com.helmes.parental_benefit_calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BenefitResponse {

    private Long id;
    private BigDecimal grossSalary;
    private LocalDate birthDate;
    private BigDecimal cappedSalary;
    private BigDecimal dailyRate;
    private List<MonthlyPayment> monthlyPayments;
}