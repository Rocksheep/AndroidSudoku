package com.codesheep.android.sudoku.generators;


import android.util.Log;

import com.codesheep.android.sudoku.elements.SudokuCell;
import com.codesheep.android.sudoku.elements.SudokuPuzzle;

import java.util.ArrayList;
import java.util.Random;

public class SudokuGenerator {

    private static String TAG = SudokuGenerator.class.getSimpleName();
    private SudokuPuzzle mSudokuPuzzle;

    public SudokuGenerator() {
        mSudokuPuzzle = new SudokuPuzzle();
    }

    public SudokuPuzzle generate() {
        Random random = new Random();
        random.setSeed(1);

        for (int i = 0; i < 81; i++) {
            mSudokuPuzzle.addCell(new SudokuCell(i % 9, i / 9));
        }
        generateCells(0, random);
        return mSudokuPuzzle;
    }

    private boolean generateCells(int i, Random random) {
        if (i >= mSudokuPuzzle.size()) {
            return true;
        }
        SudokuCell cell = mSudokuPuzzle.getCell(i);
        ArrayList<Integer> availableNumbers = generateValidRowNumbers();

        while (!availableNumbers.isEmpty()) {
            int index = random.nextInt(availableNumbers.size());
            int value = availableNumbers.get(index);
            availableNumbers.remove(index);
            cell.value(value);
            if (mSudokuPuzzle.cellIsValid(cell) && generateCells(i + 1, random)) {
                Log.d(TAG, "Cell is valid, moving to the next one");
                return true;
            }
        }
        cell.clear();
        Log.d(TAG, "No valid cell found. Reverting state");
        return false;
    }

    private ArrayList<Integer> generateValidRowNumbers() {
        ArrayList<Integer> availableNumbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            availableNumbers.add(i);
        }
        return availableNumbers;
    }

}
