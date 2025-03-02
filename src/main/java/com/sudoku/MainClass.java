package com.sudoku;

import com.sudoku.service.DefaultSudokuFactory;
import com.sudoku.service.DefaultValidationStrategy;
import com.sudoku.service.Difficulty;
import com.sudoku.service.SudokuService;
import com.sudoku.service.SudokuValidator;

public class MainClass {
public static void main(String[] args) {
	SudokuService s = new SudokuService(new DefaultSudokuFactory(), new SudokuValidator(new DefaultValidationStrategy()));
	int[][] sudoku = s.generateSudoku(9, Difficulty.MEDIUM);
	printSudoku(sudoku);
	
	System.out.println("-----------------");
	int[][] solveSudoku = s.solveSudoku(sudoku);
	printSudoku(solveSudoku);
	System.out.println("-----------------");
	System.out.println("Valid : " + s.isValidSolution(solveSudoku));
	
	System.out.println("-----------------");
	printSudoku(sudoku);
	System.out.println("Valid : " + s.isValidSolution(sudoku));
	
}

public static void printSudoku(int[][] puzzle) {
    for (int[] row : puzzle) {
        for (int cell : row) {
            System.out.print(cell + " ");
        }
        System.out.println();
    }
}
}
