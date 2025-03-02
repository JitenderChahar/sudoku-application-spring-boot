package com.sudoku.service;

import de.sfuhrm.sudoku.*;
import org.springframework.stereotype.Service;
import com.sudoku.exception.InvalidSudoku;
import com.sudoku.utils.PuzzleUtils;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class SudokuService {

    private final PuzzleFactory puzzleFactory;
    private final SudokuValidator sudokuValidator;

    public int[][] generateSudoku(int size, Difficulty difficulty) {
        validateSize(size);
        Riddle riddle = puzzleFactory.createPuzzle(size, difficulty);
        log.info("Generated {}x{} Sudoku puzzle", size, size);
        return PuzzleUtils.convertByteToIntArray(riddle.getArray());
    }

    public int[][] solveSudoku(int[][] puzzle) {
        validatePuzzle(puzzle);

        GameSchema schema = determineSchema(puzzle.length);
        Riddle riddle = new GameMatrixFactory().newRiddle(schema);
        riddle.setAll(QuadraticArrays.parse(PuzzleUtils.convertArrayToStringArray(puzzle)));

        Solver solver = new Solver(riddle);
        List<GameMatrix> solutions = solver.solve();

        if (solutions.isEmpty()) {
            throw new InvalidSudoku("No solution found for the provided Sudoku puzzle.");
        }

        return PuzzleUtils.convertByteToIntArray(solutions.get(0).getArray());
    }

    public boolean isValidSolution(int[][] solution) {
        validatePuzzle(solution);
        return sudokuValidator.validate(solution);
    }

    private void validateSize(int size) {
        if (size != 4 && size != 9) {
            throw new InvalidSudoku("Only 4x4 and 9x9 Sudoku are supported.");
        }
    }

    private void validatePuzzle(int[][] puzzle) {
        if (puzzle == null || puzzle.length == 0 || puzzle[0].length == 0) {
            throw new InvalidSudoku("Provided puzzle is null or empty.");
        }
        int rows = puzzle.length;
        int cols = puzzle[0].length;
        if (rows != cols || (rows != 4 && rows != 9)) {
            throw new InvalidSudoku("Puzzle must have equal rows and columns and be 4x4 or 9x9.");
        }
    }

    private GameSchema determineSchema(int size) {
        return size == 4 ? GameSchemas.SCHEMA_4X4 : GameSchemas.SCHEMA_9X9;
    }
}


