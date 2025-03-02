package com.sudoku.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDto {
	@Builder.Default
	private LocalDateTime timestamp = LocalDateTime.now();
	private int statusCode;
	private String message;

}
