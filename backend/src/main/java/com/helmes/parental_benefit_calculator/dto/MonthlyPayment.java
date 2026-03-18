package com.helmes.parental_benefit_calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Monthly parental benefit payment")
public class MonthlyPayment {

    @Schema(description = "Month of payment in YYYY-MM format", example = "2026-01")
    private String month;

    @Schema(description = "Number of days paid in the month", example = "31")
    private int days;

    @Schema(description = "Amount of payment in Euro for the month (rounded to 2 decimal places)", example = "4133.33")
    private BigDecimal payment;
}