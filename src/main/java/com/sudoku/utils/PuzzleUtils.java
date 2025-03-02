package com.sudoku.utils;

import com.sudoku.exception.InvalidSudoku;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PuzzleUtils {

    public static String[] convertArrayToStringArray(int[][] array) {
        if (array == null) throw new InvalidSudoku("Input array is null.");
        String[] result = new String[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = convertRowToString(array[i]);
        }
        return result;
    }

    private static String convertRowToString(int[] row) {
        StringBuilder rowString = new StringBuilder();
        for (int value : row) {
            rowString.append(value);
        }
        return rowString.toString();
    }

    public static int[][] convertByteToIntArray(byte[][] byteArray) {
        if (byteArray == null || byteArray.length == 0) {
            throw new InvalidSudoku("Invalid input: byteArray is null or empty.");
        }
        int[][] intArray = new int[byteArray.length][byteArray[0].length];
        for (int i = 0; i < byteArray.length; i++) {
            for (int j = 0; j < byteArray[i].length; j++) {
                intArray[i][j] = byteArray[i][j];
            }
        }
        return intArray;
    }
}