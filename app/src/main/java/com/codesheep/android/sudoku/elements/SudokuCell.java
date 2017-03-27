package com.codesheep.android.sudoku.elements;


public class SudokuCell {

    private int mX;
    private int mY;
    private int mValue;

    public SudokuCell(int x, int y) {
        this(x, y, 0);
    }

    public SudokuCell(int x, int y, int value) {
        mX = x;
        mY = y;
        mValue = value;
    }

    public int value() {
        return mValue;
    }

    public void value(int value) {
        mValue = value;
    }

    public int x() {
        return mX;
    }

    public int y() {
        return mY;
    }
}
