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

        if (mSudokuPuzzle.isValid()) {
            Log.d(TAG, "The generated puzzle is valid");
        }
        else {
            Log.d(TAG, "The generated puzzle is invalid");
        }
        return mSudokuPuzzle;
    }

    private boolean generateCells(int i, Random random) {
        if (i >= 81) {
            return true;
        }
        SudokuCell cell = mSudokuPuzzle.getCell(i);
        ArrayList<Integer> availableNumbers = generateValidRowNumbers();


        while (availableNumbers.size() > 0) {
            int index = random.nextInt(availableNumbers.size());
            int value = availableNumbers.get(index);
            availableNumbers.remove(index);
            cell.value(value);
            if (mSudokuPuzzle.cellIsValid(cell)) {
                Log.d(TAG, "Cell is valid, moving to the next one");
                if (generateCells(i + 1, random)) {
                    return true;
                }
            }
        }
        cell.value(0);
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
