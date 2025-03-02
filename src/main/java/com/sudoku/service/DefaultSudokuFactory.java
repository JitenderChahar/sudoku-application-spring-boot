package com.sudoku.service;

import org.springframework.stereotype.Service;

import com.sudoku.exception.InvalidSudoku;

import de.sfuhrm.sudoku.Creator;
import de.sfuhrm.sudoku.GameMatrix;
import de.sfuhrm.sudoku.GameSchema;
import de.sfuhrm.sudoku.GameSchemas;
import de.sfuhrm.sudoku.Riddle;

@Service
public class DefaultSudokuFactory implements PuzzleFactory {

    @Override
    public Riddle createPuzzle(int size, Difficulty difficulty) {
        GameSchema schema = size == 4 ? GameSchemas.SCHEMA_4X4 : GameSchemas.SCHEMA_9X9;
        GameMatrix gameMatrix = Creator.createFull(schema);

        if (gameMatrix == null) {
            throw new InvalidSudoku("Failed to generate a full Sudoku puzzle.");
        }

        Riddle riddle = Creator.createRiddle(gameMatrix, getDifficultyLevel(size, difficulty));
        if (riddle == null) {
            throw new InvalidSudoku("Failed to create a Sudoku riddle.");
        }

        return riddle;
    }
    
    private int getDifficultyLevel(int size, Difficulty difficulty) {
    	 switch (difficulty) {
         case VERY_EASY:
             return size == 9 ? Creator.RIDDLE_9X9_EMPTY_FIELDS_VERY_EASY : Creator.RIDDLE_4X4_EMPTY_FIELDS_VERY_EASY; 
         case EASY:
             return size == 9 ? Creator.RIDDLE_9X9_EMPTY_FIELDS_EASY : Creator.RIDDLE_4X4_EMPTY_FIELDS_EASY;
         case MEDIUM:
             return size == 9 ? Creator.RIDDLE_9X9_EMPTY_FIELDS_MEDIUM : Creator.RIDDLE_4X4_EMPTY_FIELDS_MEDIUM;
         case HARD:
             return size == 9 ? Creator.RIDDLE_9X9_EMPTY_FIELDS_HARD : Creator.RIDDLE_4X4_EMPTY_FIELDS_HARD ;
         case VERY_HARD:
             return size == 9 ? Creator.RIDDLE_9X9_EMPTY_FIELDS_VERY_HARD : Creator.RIDDLE_4X4_EMPTY_FIELDS_VERY_HARD; 
         default:
             throw new IllegalArgumentException("Invalid difficulty level");
     }
    	
    }
}