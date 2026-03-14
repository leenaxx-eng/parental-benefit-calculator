package com.helmes.parental_benefit_calculator.controller;

import com.helmes.parental_benefit_calculator.dto.BenefitRequest;
import com.helmes.parental_benefit_calculator.dto.BenefitResponse;
import com.helmes.parental_benefit_calculator.service.BenefitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/benefits")
@CrossOrigin(origins = "*")
@Tag(name = "Benefits", description = "Parental benefit calculation API")
public class BenefitController {

	private final BenefitService benefitService;

	public BenefitController(BenefitService benefitService) {
		this.benefitService = benefitService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@Operation(summary = "Calculate and store a parental benefit schedule")
	public BenefitResponse createBenefit(@Valid @RequestBody BenefitRequest request) {
		return benefitService.createBenefit(request);
	}

	@GetMapping("/{id}")
	@Operation(summary = "Retrieve saved benefit schedule")
	public BenefitResponse getBenefitById(
			@Parameter(description = "ID of saved benefit calculation", example = "1") 
			@PathVariable Long id) {
		return benefitService.getBenefitById(id);
	}
}