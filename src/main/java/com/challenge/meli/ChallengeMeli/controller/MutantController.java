package com.challenge.meli.ChallengeMeli.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Object> checkMutant(@Valid @RequestBody MutantRequest request) {
		return mutantService.detectMutant(request);
	}

}
