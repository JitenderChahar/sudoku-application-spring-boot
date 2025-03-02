package com.sudoku.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sudoku.dto.ErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidSudoku.class)
    public ResponseEntity<ErrorResponseDto> handleApplicationException(InvalidSudoku ex) {
    	ErrorResponseDto errorResponse = ErrorResponseDto.builder()
    			.statusCode(HttpStatus.BAD_REQUEST.value())
    			.message( ex.getMessage())
    			.build();
        return ResponseEntity.badRequest().body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGeneralException(Exception ex) {
        ErrorResponseDto errorResponse = ErrorResponseDto.builder()
    			.statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
    			.message( ex.getMessage())
    			.build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }
}
