package com.helmes.parental_benefit_calculator.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Benefit calculation request")
public class BenefitRequest {

  @NotNull(message = "Gross salary is required")
  @DecimalMin(value = "0.01", message = "grossSalary must be greater than 0")
  @Digits(integer = 10, fraction = 2, message = "Salary can have max 2 decimal places")
  @Schema(
    description = "User's gross monthly salary in Euro (max 2 decimal places)", 
    example = "5000", 
    requiredMode = Schema.RequiredMode.REQUIRED)
  private BigDecimal grossSalary;

  @NotNull(message = "Birth date is required")
  @Schema(
    description = "Birth date of the baby (ISO format: YYYY-MM-DD)", 
    example = "2026-01-01",
    requiredMode = Schema.RequiredMode.REQUIRED)
  private LocalDate birthDate;
}