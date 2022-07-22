package com.challenge.meli.ChallengeMeli.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RedisHash("Human")
public class Human implements Serializable {

	private static final long serialVersionUID = 5320934104158170909L;

	@Id
	private String[] dna;
	private boolean mutant;
}