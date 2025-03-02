package com.sudoku.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class DefaultValidationStrategy implements ValidationStrategy {

    @Override
    public boolean validate(int[][] solution) {
        int size = solution.length;
        int subGridSize = (size == 4) ? 2 : 3;

        // Validate rows
        for (int[] row : solution) {
            if (!isValidSet(row)) return false;
        }

        // Validate columns
        for (int col = 0; col < size; col++) {
            int[] column = new int[size];
            for (int row = 0; row < size; row++) {
                column[row] = solution[row][col];
            }
            if (!isValidSet(column)) return false;
        }

        // Validate subgrids
        for (int row = 0; row < size; row += subGridSize) {
            for (int col = 0; col < size; col += subGridSize) {
                if (!isValidSubGrid(solution, row, col, subGridSize)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValidSet(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (num < 1 || set.contains(num)) return false;
            set.add(num);
        }
        return true;
    }

    private boolean isValidSubGrid(int[][] grid, int startRow, int startCol, int subGridSize) {
        Set<Integer> set = new HashSet<>();
        for (int i = 0; i < subGridSize; i++) {
            for (int j = 0; j < subGridSize; j++) {
                int num = grid[startRow + i][startCol + j];
                if (num < 1 || set.contains(num)) return false;
                set.add(num);
            }
        }
        return true;
    }
}