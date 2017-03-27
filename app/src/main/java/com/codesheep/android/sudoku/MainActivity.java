package com.codesheep.android.sudoku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.codesheep.android.sudoku.generators.SudokuGenerator;
import com.codesheep.android.sudoku.views.SudokuGrid;

public class MainActivity extends AppCompatActivity {

    private SudokuGrid mSudokuGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSudokuGrid = (SudokuGrid) findViewById(R.id.sudoku_grid);
    }
}
