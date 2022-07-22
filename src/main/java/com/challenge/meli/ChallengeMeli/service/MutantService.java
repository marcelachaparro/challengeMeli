package com.challenge.meli.ChallengeMeli.service;

import org.springframework.http.ResponseEntity;

import com.challenge.meli.ChallengeMeli.dto.MutantRequest;

public interface MutantService {

	ResponseEntity<Object> detectMutant(MutantRequest request);

	ResponseEntity<Object> getAllHumans();
}