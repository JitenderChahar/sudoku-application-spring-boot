package com.sudoku.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SudokuRequestDto {
	@NotNull(message = "Puzzle cannot be null")
    private int[][] puzzle;
}
