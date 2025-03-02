package com.sudoku.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.sudoku.dto.SudokuRequestDto;
import com.sudoku.dto.SudokuResponseDto;
import com.sudoku.service.Difficulty;
import com.sudoku.service.SudokuService;

@Controller
@Slf4j
@RequiredArgsConstructor
public class SudokuWebController {

    private final SudokuService sudokuService;

    // Serve the UI page
    @GetMapping("/")
    public String homePage(Model model) {
        return "index.html";  // This will map to src/main/resources/static/index.html
    }

    // Generate Sudoku Puzzle
    @GetMapping("/sudoku/generate")
    @ResponseBody
    public ResponseEntity<SudokuResponseDto> generateSudoku(@RequestParam(defaultValue = "9") int size, 
    		@RequestParam(defaultValue = "MEDIUM") Difficulty difficulty) {
        return new ResponseEntity<>(SudokuResponseDto.builder()
        		.grid(sudokuService.generateSudoku(size, difficulty))
        		.message("Sudoku puzzle generated successfully.")
        		.build(), HttpStatus.CREATED);
    }

    // Solve Sudoku Puzzle
    @PostMapping("/sudoku/solve")
    @ResponseBody
    public ResponseEntity<SudokuResponseDto> solveSudoku(@Valid @RequestBody SudokuRequestDto request) {
        return new ResponseEntity<>(SudokuResponseDto.builder()
        		.grid(sudokuService.solveSudoku(request.getPuzzle()))
        		.message("Sudoku puzzle solved successfully.")
        		.build(), HttpStatus.OK);
    }

    // Validate Sudoku Solution
    @PostMapping("/sudoku/validate")
    @ResponseBody
    public ResponseEntity<String> validateSolution(@Valid @RequestBody SudokuRequestDto request) {
        return new ResponseEntity<>(sudokuService.isValidSolution(request.getPuzzle()) ? "Congratulation !! Solution is correct." : "Sorry!! Solution is not correct.", HttpStatus.OK);
    }
}
