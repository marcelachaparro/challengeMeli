package com.challenge.meli.ChallengeMeli.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.challenge.meli.ChallengeMeli.dto.MutantRequest;
import com.challenge.meli.ChallengeMeli.entity.Human;
import com.challenge.meli.ChallengeMeli.repository.HumanDao;
import com.challenge.meli.ChallengeMeli.service.MutantService;

@Service
public class MutantServiceImpl implements MutantService {

	@Autowired
	private HumanDao dnaDao;

	public ResponseEntity<Object> detectMutant(MutantRequest request) {

		boolean mutant = isMutant(request.getDna());
		saveDna(request.getDna(), mutant);
		if (mutant) {
			return new ResponseEntity<Object>("Es un mutante", HttpStatus.OK);
		}
		return new ResponseEntity<Object>("No es un mutante", HttpStatus.FORBIDDEN);

	}

	public ResponseEntity<Object> getAllHumans() {

		List<Human> humans = dnaDao.findAll();
		if (!humans.isEmpty()) {
			return new ResponseEntity<Object>(humans, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("No se han registrado humanos", HttpStatus.NOT_FOUND);
	}

	/**
	 * Save the DNA of a given human and the flag to know if is a mutant or not.
	 * 
	 * @param dnaArray
	 * @param isMutant
	 */
	private void saveDna(String[] dnaArray, boolean isMutant) {
		Human dna = new Human(dnaArray, isMutant);
		dnaDao.save(dna);

	}

	/**
	 * Validates the array (human DNA sequence) in order to find at least one
	 * sequence of four equal letters vertically, horizontally or diagonally to
	 * detect if human is a mutant or not.
	 * 
	 * @param dna
	 * @return true is given dna is of a mutant
	 */
	private boolean isMutant(String[] dna) {

		char[][] matrix = buildMatrix(dna);
		for (int i = 0; i < dna.length; i++) {
			if (verifyHorizontal(dna[i]))
				return true;

			if (verifyVertical(matrix, dna, i))
				return true;
		}

		return false;

	}

	/**
	 * Build a 2D matrix from the array of Strings
	 * 
	 * @param dna: array to build the 2d matrix
	 * @return matrix 2d
	 */
	private static char[][] buildMatrix(String[] dna) {
		char[][] matrix = new char[dna.length][dna.length];
		for (int i = 0; i < dna.length; i++) {
			for (int j = 0; j < dna[0].length(); j++) {
				matrix[i][j] = dna[i].toCharArray()[j];
			}
		}
		return matrix;
	}

	/**
	 * Compare the letter with the letter on the right in the "dna" string. Resets
	 * "repeated" to 1 if it is not consecutive
	 * 
	 * @param dna: String to compare its letters
	 * @return true if there are 4 consecutive equal letters in "dna", false
	 *         otherwise.
	 */
	private boolean verifyHorizontal(String dna) {
		int repeated = 1;
		char[] chars = dna.toCharArray();
		for (int j = 0; j < chars.length - 1; j++) {
			if (chars[j] == chars[j + 1])
				repeated++;
			else
				repeated = 1;
			if (repeated == 4)
				return true;
		}
		return false;
	}

	/**
	 * Receives the array, build the 2d matrix and for each column compares the
	 * letter with the letter below. Resets "repeated" to 1 if it is not consecutive
	 * 
	 * @param dna: array to compare
	 * @param i:   position in the rows
	 * @return true if there are 4 consecutive equal letters in "dna", false
	 *         otherwise.
	 */
	private boolean verifyVertical(char[][] matrix, String[] dna, int i) {
		int repeated = 1;
		for (int j = 0; j < dna.length - 1; j++) {
			if (matrix[j][i] == matrix[j + 1][i])
				repeated++;
			else
				repeated = 1;
			if (repeated == 4)
				return true;
		}
		return false;
	}
}
