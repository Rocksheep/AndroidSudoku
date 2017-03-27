package com.codesheep.android.sudoku.elements;


import android.util.Log;

import java.util.ArrayList;

public class SudokuPuzzle {

    private static String TAG = SudokuPuzzle.class.getSimpleName();
    private ArrayList<SudokuCell> mCells;

    public SudokuPuzzle() {
        mCells = new ArrayList<>();
    }

    public void addCell(SudokuCell cell) {
        mCells.add(cell);
    }

    public ArrayList<SudokuCell> cells() {
        return mCells;
    }

    public boolean isValid() {
        return true;
    }

    public boolean cellIsValid(SudokuCell givenCell) {
        Log.d(TAG, "Testing cell with value: " + givenCell.value());
        boolean squareDuplicateFound = false;
        for (final SudokuCell cell : mCells) {
            // Check for duplicates in Row
            if (cell.y() == givenCell.y() && cell.x() != givenCell.x() && cell.value() == givenCell.value()) {
                return false;
            }
            // Check for duplicates in Column
            if (cell.x() == givenCell.x() && cell.y() != givenCell.y() && cell.value() == givenCell.value()) {
                return false;
            }
            // Check for duplicates in Square
            int givenSquareX = givenCell.x() / 3;
            int givenSquareY = givenCell.y() / 3;
            int cellSquareX = cell.x() / 3;
            int cellSquareY = cell.y() / 3;
            if (cell.y() != givenCell.y() && cell.x() != givenCell.x() && cellSquareX == givenSquareX && cellSquareY == givenSquareY && cell.value() == givenCell.value()) {
                Log.d(TAG, "Duplicate of " + givenCell.value() + " found in square " + givenSquareX + "," + givenSquareY);
                squareDuplicateFound = true;
            }
        }
        if (squareDuplicateFound) {
            return false;
        }
        return true;
    }

    public SudokuCell getCell(int i) {
        return mCells.get(i);
    }

    public void removeCell(SudokuCell cell) {
        mCells.remove(cell);
    }

    public void removeCell(int cellIndex) {
        mCells.remove(cellIndex);
    }
}
