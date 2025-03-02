package com.sudoku.service;

import de.sfuhrm.sudoku.Riddle;

public interface PuzzleFactory {
    Riddle createPuzzle(int size, Difficulty difficulty);
}