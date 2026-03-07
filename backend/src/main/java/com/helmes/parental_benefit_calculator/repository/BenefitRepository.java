package com.helmes.parental_benefit_calculator.repository;

import com.helmes.parental_benefit_calculator.entity.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {
}