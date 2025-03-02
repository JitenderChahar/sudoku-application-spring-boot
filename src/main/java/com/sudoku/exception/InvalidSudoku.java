package com.sudoku.exception;

public class InvalidSudoku extends RuntimeException{
	private static final long serialVersionUID = 3099806274656954215L;

	public InvalidSudoku(String message) {
		super(message);
	}

}
