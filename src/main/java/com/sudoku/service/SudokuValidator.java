package com.sudoku.service;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SudokuValidator {

    private final ValidationStrategy validationStrategy;

    public boolean validate(int[][] solution) {
        return validationStrategy.validate(solution);
    }
}