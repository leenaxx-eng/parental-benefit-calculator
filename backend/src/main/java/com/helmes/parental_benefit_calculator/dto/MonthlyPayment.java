package com.helmes.parental_benefit_calculator.dto;

import java.math.BigDecimal;

public class MonthlyPayment {

    private String month;
    private int days;
    private BigDecimal payment;

    public MonthlyPayment() {
    }

    public MonthlyPayment(String month, int days, BigDecimal payment) {
        this.month = month;
        this.days = days;
        this.payment = payment;
    }

    public String getMonth() {
        return month;
    }

    public int getDays() {
        return days;
    }

    public BigDecimal getPayment() {
        return payment;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public void setPayment(BigDecimal payment) {
        this.payment = payment;
    }
}