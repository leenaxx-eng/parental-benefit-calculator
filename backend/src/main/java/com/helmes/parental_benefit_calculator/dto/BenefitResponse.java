package com.helmes.parental_benefit_calculator.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Benefit calculation response")
public class BenefitResponse {

  @Schema(description = "Unique identifier of the calculated benefit schedule", example = "1")
  private Long id;

  @Schema(description = "User's gross salary in Euro before cap applied", example = "5000")
  private BigDecimal grossSalary;

  @Schema(description = "Birth date of the baby (ISO format: YYYY-MM-DD)", example = "2026-01-01")
  private LocalDate birthDate;

  @Schema(description = "Salary in Euro after applying the €4000 cap", example = "4000")
  private BigDecimal cappedSalary;

  @Schema(description = "Daily benefit rate calculated from capped salary", example = "133.33")
  private BigDecimal dailyRate;

  @Schema(description = "List of monthly parental benefit payments for the 12-month period",
    example = """
    [
      {"month": "2026-01", "days": 31, "payment": 4133.33},
      {"month": "2026-02", "days": 28, "payment": 3733.24}
    ]
    """
  )
  private List<MonthlyPayment> monthlyPayments;
}