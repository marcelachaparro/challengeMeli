package com.challenge.meli.ChallengeMeli.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.challenge.meli.ChallengeMeli.dto.MutantRequest;
import com.challenge.meli.ChallengeMeli.service.MutantService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api/v1/")
public class MutantController {

	@Autowired
	private MutantService mutantService;

	@PostMapping("mutant")
	@ApiOperation(value = "Detect if a human is a mutant by sending the DNA sequence", notes = "Detect if a human is a mutant by sending the DNA sequence")
	public ResponseEntity<Object> checkMutant(@Validated @RequestBody MutantRequest request) {
		return mutantService.detectMutant(request);
	}

	@GetMapping("retrieveHumans")
	@ApiOperation(value = "Retrieve all humans saved in the DB (verified by the API)", notes = "Retrieve all humans saved in the DB (verified by the API)")
	public ResponseEntity<Object> getAllHumans() {
		return mutantService.getAllHumans();

	}

}
