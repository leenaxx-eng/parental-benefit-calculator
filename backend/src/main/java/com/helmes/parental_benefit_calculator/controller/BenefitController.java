package com.helmes.parental_benefit_calculator.controller;

import com.helmes.parental_benefit_calculator.dto.BenefitRequest;
import com.helmes.parental_benefit_calculator.dto.BenefitResponse;
import com.helmes.parental_benefit_calculator.service.BenefitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/benefits")
@CrossOrigin(origins = "*")
public class BenefitController {

    private final BenefitService benefitService;

    public BenefitController(BenefitService benefitService) {
        this.benefitService = benefitService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BenefitResponse createBenefit(@Valid @RequestBody BenefitRequest request) {
        return benefitService.createBenefit(request);
    }

    @GetMapping("/{id}")
    public BenefitResponse getBenefitById(@PathVariable Long id) {
        return benefitService.getBenefitById(id);
    }
}