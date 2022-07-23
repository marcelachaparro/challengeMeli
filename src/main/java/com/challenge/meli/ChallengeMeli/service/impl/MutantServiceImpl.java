package com.challenge.meli.ChallengeMeli.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.challenge.meli.ChallengeMeli.dto.MutantRequest;
import com.challenge.meli.ChallengeMeli.dto.ResponseStats;
import com.challenge.meli.ChallengeMeli.entity.Human;
import com.challenge.meli.ChallengeMeli.repository.HumanDao;
import com.challenge.meli.ChallengeMeli.service.MutantService;

@Service
public class MutantServiceImpl implements MutantService {

	@Autowired
	private HumanDao humanDao;

	/**
	 * Validate the dna in the request to return if is mutant
	 */
	public ResponseEntity<Object> detectMutant(MutantRequest request) {

		boolean mutant = isMutant(request.getDna());
		saveDna(request.getDna(), mutant);
		if (mutant) {
			return new ResponseEntity<Object>("Es un mutante", HttpStatus.OK);
		}
		return new ResponseEntity<Object>("No es un mutante", HttpStatus.FORBIDDEN);

	}

	/**
	 * Calculate the stats of the received dna
	 */
	public ResponseEntity<Object> calculateStats() {
		List<Human> humans = humanDao.findAll();
		ResponseStats responseStats = new ResponseStats(0, 0, 0);
		if (!humans.isEmpty()) {
			List<Human> mutants = humans.stream().filter(h -> h.isMutant()).collect(Collectors.toList());

			float ratio = (float) mutants.size() / (float) humans.size();
			responseStats.setCountMutantDna(mutants.size());
			responseStats.setCountHumanDna(humans.size());
			responseStats.setRatio(ratio);
		}
		return new ResponseEntity<Object>(responseStats, HttpStatus.OK);
	}

	/**
	 * Consult all humans saved
	 */
	public ResponseEntity<Object> getAllHumans() {

		List<Human> humans = humanDao.findAll();
		if (!humans.isEmpty()) {
			return new ResponseEntity<Object>(humans, HttpStatus.OK);
		}
		return new ResponseEntity<Object>("No se han registrado humanos", HttpStatus.NOT_FOUND);
	}

	/**
	 * Remove all humans saved
	 */
	public ResponseEntity<Object> removeAllHumans() {

		humanDao.deleteAll();
		return new ResponseEntity<Object>("Se borraron los DNA almacenados", HttpStatus.OK);
	}

	/**
	 * Save the DNA of a given human and the flag to know if is a mutant or not.
	 * 
	 * @param dnaArray
	 * @param isMutant
	 */
	private void saveDna(String[] dnaArray, boolean isMutant) {
		Human dna = new Human(dnaArray, isMutant);
		humanDao.save(dna);

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
		if (verifyDiagonal(dna))
			return true;
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
	 * Receives the array and the 2d matrix and for each column compares the letter
	 * with the letter below. Resets "repeated" to 1 if it is not consecutive
	 * 
	 * @param matrix: dna as 2d array
	 * @param dna:    array to compare
	 * @param i:      position in the rows
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

	/**
	 * Receives the array in order to validate the diagonals from top left to bottom
	 * right and from bottom left to top right.
	 * 
	 * @param dna
	 * @return
	 */
	private boolean verifyDiagonal(String[] dna) {
		int repeated = 0;
		int yLenght = dna.length;
		int xLenght = dna[0].length();
		int maxLength = Math.max(xLenght, yLenght);

		for (int k = 0; k < 2 * (maxLength - 1); ++k) {
			String dnaDiagonal1 = "";
			String dnaDiagonal2 = "";
			for (int y = yLenght - 1; y >= 0; --y) {

				int x = k - y;
				if (x >= 0 && x < xLenght) {
					dnaDiagonal1 += dna[y].charAt(x);
				}
				int xRight = k - (yLenght - y);
				if (xRight >= 0 && xRight < xLenght) {
					dnaDiagonal2 += dna[y].charAt(xRight);
				}
			}
			// verify diagonal top left to bottom right (dnaDiagonal1)
			if (dnaDiagonal1.length() >= 4 && verifyHorizontal(dnaDiagonal1))
				repeated++;
			// verify diagonal bottom left to top right (dnaDiagonal2)
			if (dnaDiagonal2.length() >= 4 && verifyHorizontal(dnaDiagonal2))
				repeated++;
		}
		return repeated >= 1;
	}
}
