package com.challenge.meli.ChallengeMeli.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.challenge.meli.ChallengeMeli.entity.Human;

@Repository
public class HumanDao {

	public static final String HASH_KEY = "Human";
	@Autowired
	private RedisTemplate template;

	public Human save(Human dna) {
		template.opsForHash().put(HASH_KEY, dna.getDna(), dna);
		return dna;
	}

	public List<Human> findAll() {
		return template.opsForHash().values(HASH_KEY);
	}
}