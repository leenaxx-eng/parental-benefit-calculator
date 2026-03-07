package com.helmes.parental_benefit_calculator.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "benefits")
public class Benefit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal grossSalary;

    @Column(nullable = false)
    private LocalDate birthDate;

    public Benefit() {
    }

    public Benefit(BigDecimal grossSalary, LocalDate birthDate) {
        this.grossSalary = grossSalary;
        this.birthDate = birthDate;
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

    public void setId(Long id) {
        this.id = id;
    }

    public void setGrossSalary(BigDecimal grossSalary) {
        this.grossSalary = grossSalary;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }
}