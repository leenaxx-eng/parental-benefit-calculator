package com.helmes.parental_benefit_calculator.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class BenefitResponse {

    private Long id;
    private BigDecimal grossSalary;
    private LocalDate birthDate;
    private BigDecimal cappedSalary;
    private BigDecimal dailyRate;
    private List<MonthlyPayment> monthlyPayments;

    public BenefitResponse() {
    }

    public BenefitResponse(Long id,
                           BigDecimal grossSalary,
                           LocalDate birthDate,
                           BigDecimal cappedSalary,
                           BigDecimal dailyRate,
                           List<MonthlyPayment> monthlyPayments) {
        this.id = id;
        this.grossSalary = grossSalary;
        this.birthDate = birthDate;
        this.cappedSalary = cappedSalary;
        this.dailyRate = dailyRate;
        this.monthlyPayments = monthlyPayments;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getGrossSalary() {
        return grossSalary;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public BigDecimal getCappedSalary() {
        return cappedSalary;
    }

    public BigDecimal getDailyRate() {
        return dailyRate;
    }

    public List<MonthlyPayment> getMonthlyPayments() {
        return monthlyPayments;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGrossSalary(BigDecimal grossSalary) {
        this.grossSalary = grossSalary;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public void setCappedSalary(BigDecimal cappedSalary) {
        this.cappedSalary = cappedSalary;
    }

    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }

    public void setMonthlyPayments(List<MonthlyPayment> monthlyPayments) {
        this.monthlyPayments = monthlyPayments;
    }
}