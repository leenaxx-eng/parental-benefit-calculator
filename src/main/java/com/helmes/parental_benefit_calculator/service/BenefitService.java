package com.helmes.parental_benefit_calculator.service;

import com.helmes.parental_benefit_calculator.dto.BenefitRequest;
import com.helmes.parental_benefit_calculator.dto.BenefitResponse;
import com.helmes.parental_benefit_calculator.dto.MonthlyPayment;
import com.helmes.parental_benefit_calculator.entity.Benefit;
import com.helmes.parental_benefit_calculator.exception.ResourceNotFoundException;
import com.helmes.parental_benefit_calculator.repository.BenefitRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BenefitService {

    private final BenefitRepository benefitRepository;
    private final CalculationService calculationService;

    public BenefitService(BenefitRepository benefitRepository, CalculationService calculationService) {
        this.benefitRepository = benefitRepository;
        this.calculationService = calculationService;
    }

    public BenefitResponse createBenefit(BenefitRequest request) {
        Benefit benefit = new Benefit(request.getGrossSalary(), request.getBirthDate());
        Benefit savedBenefit = benefitRepository.save(benefit);

        return mapToResponse(savedBenefit);
    }

    public BenefitResponse getBenefitById(Long id) {
        Benefit benefit = benefitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Benefit not found with id: " + id));

        return mapToResponse(benefit);
    }

    private BenefitResponse mapToResponse(Benefit benefit) {
        BigDecimal cappedSalary = calculationService.calculateCappedSalary(benefit.getGrossSalary());
        BigDecimal dailyRate = calculationService.calculateDailyRate(benefit.getGrossSalary());
        List<MonthlyPayment> monthlyPayments = calculationService.calculateMonthlyPayments(
                benefit.getGrossSalary(),
                benefit.getBirthDate()
        );

        return new BenefitResponse(
                benefit.getId(),
                benefit.getGrossSalary(),
                benefit.getBirthDate(),
                cappedSalary,
                dailyRate,
                monthlyPayments
        );
    }
}