package com.helmes.parental_benefit_calculator.service;

import com.helmes.parental_benefit_calculator.dto.MonthlyPayment;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

@Service
public class CalculationService {

    private static final BigDecimal SALARY_CAP = BigDecimal.valueOf(4000);
    private static final BigDecimal DAYS_DIVISOR = BigDecimal.valueOf(30);

    public BigDecimal calculateCappedSalary(BigDecimal grossSalary) {
        return grossSalary.min(SALARY_CAP);
    }

    public BigDecimal calculateDailyRate(BigDecimal grossSalary) {
        BigDecimal cappedSalary = calculateCappedSalary(grossSalary);
        return cappedSalary.divide(DAYS_DIVISOR, 2, RoundingMode.HALF_UP);
    }

    public List<MonthlyPayment> calculateMonthlyPayments(BigDecimal grossSalary, LocalDate birthDate) {
        BigDecimal dailyRate = calculateDailyRate(grossSalary);
        List<MonthlyPayment> payments = new ArrayList<>();

        LocalDate currentDate = birthDate;

        for (int i = 0; i < 12; i++) {
            YearMonth currentMonth = YearMonth.from(currentDate);

            int payableDays;
            if (i == 0) {
                payableDays = currentMonth.lengthOfMonth() - birthDate.getDayOfMonth() + 1;
            } else {
                payableDays = currentMonth.lengthOfMonth();
            }

            BigDecimal payment = dailyRate
                    .multiply(BigDecimal.valueOf(payableDays))
                    .setScale(2, RoundingMode.HALF_UP);

            payments.add(new MonthlyPayment(
                    currentMonth.toString(),
                    payableDays,
                    payment
            ));

            currentDate = currentDate.plusMonths(1).withDayOfMonth(1);
        }

        return payments;
    }
}