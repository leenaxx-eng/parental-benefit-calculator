package com.helmes.parental_benefit_calculator.service;

import com.helmes.parental_benefit_calculator.dto.BenefitRequest;
import com.helmes.parental_benefit_calculator.dto.BenefitResponse;
import com.helmes.parental_benefit_calculator.dto.MonthlyPayment;
import com.helmes.parental_benefit_calculator.entity.Benefit;
import com.helmes.parental_benefit_calculator.exception.ResourceNotFoundException;
import com.helmes.parental_benefit_calculator.repository.BenefitRepository;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BenefitService {

    private static final Logger log = LoggerFactory.getLogger(BenefitService.class);
    private final BenefitRepository benefitRepository;
    private final CalculationService calculationService;

    public BenefitService(BenefitRepository benefitRepository, CalculationService calculationService) {
        this.benefitRepository = benefitRepository;
        this.calculationService = calculationService;
    }

    public BenefitResponse createBenefit(BenefitRequest request) {
        log.info("Creating benefit for salary {} and birth date {}", request.getGrossSalary(), request.getBirthDate());
        
        Benefit benefit = new Benefit();
        benefit.setGrossSalary(request.getGrossSalary());
        benefit.setBirthDate(request.getBirthDate());
        Benefit savedBenefit = benefitRepository.save(benefit);

        log.info("Benefit saved with id {}", savedBenefit.getId());

        return mapToResponse(savedBenefit);
    }

    public BenefitResponse getBenefitById(Long id) {
        log.info("Fetching benefit with id {}", id);

        Benefit benefit = benefitRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Benefit not found with id: " + id));

        log.info("Benefit found with id {}", id);
        return mapToResponse(benefit);
    }

    private BenefitResponse mapToResponse(Benefit benefit) {
        BigDecimal cappedSalary = calculationService.calculateCappedSalary(benefit.getGrossSalary());
        BigDecimal dailyRate = calculationService.calculateDailyRate(cappedSalary);

        List<MonthlyPayment> monthlyPayments = calculationService.calculateMonthlyPayments(
                dailyRate,
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