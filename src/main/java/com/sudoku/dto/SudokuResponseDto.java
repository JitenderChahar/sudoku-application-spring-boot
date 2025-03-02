package com.sudoku.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SudokuResponseDto {
	private int[][] grid;
    private String message;
}
