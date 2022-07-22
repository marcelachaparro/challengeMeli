package com.challenge.meli.ChallengeMeli.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.challenge.meli.ChallengeMeli.dto.MutantRequest;
import com.challenge.meli.ChallengeMeli.service.MutantService;

@Service
public class MutantServiceImpl implements MutantService {

	public ResponseEntity<Object> detectMutant(MutantRequest request) {

		if (isMutant(request.getDna())) {
			return new ResponseEntity<Object>("es un mutante", HttpStatus.OK);
		}
		return new ResponseEntity<Object>("no es un mutante", HttpStatus.FORBIDDEN);

	}

	/**
	 * Validates the array (human DNA sequence) in order to find at least one
	 * sequence of four equal letters vertically, horizontally or diagonally to
	 * detect if human is a mutant or not.
	 * 
	 * @param dna
	 * @return
	 */
	private boolean isMutant(String[] dna) {

		for (int i = 0; i < dna.length; i++) {
			if (verifyHorizontal(dna[i]))
				return true;

			if (verifyVertical(dna, i))
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
	private boolean verifyVertical(String[] dna, int i) {
		int repeated = 1;
		char[][] matrix = buildMatrix(dna);
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
