package com.sudoku.service;

public interface ValidationStrategy {
    boolean validate(int[][] solution);
}