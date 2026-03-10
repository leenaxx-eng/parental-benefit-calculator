package com.helmes.parental_benefit_calculator.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BenefitRequest {

    @NotNull(message = "Gross salary is required")
    @Positive(message = "Gross salary must be greater than 0")
    @Digits(integer = 10, fraction = 2, message = "Salary can have max 2 decimal places")
    private BigDecimal grossSalary;

    @NotNull(message = "Birth date is required")
    private LocalDate birthDate;
}