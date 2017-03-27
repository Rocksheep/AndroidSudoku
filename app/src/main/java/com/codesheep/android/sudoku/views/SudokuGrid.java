package com.codesheep.android.sudoku.views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.codesheep.android.sudoku.elements.SudokuCell;
import com.codesheep.android.sudoku.elements.SudokuPuzzle;
import com.codesheep.android.sudoku.exceptions.NoSudokuGeneratorSpecifiedException;
import com.codesheep.android.sudoku.generators.SudokuGenerator;

public class SudokuGrid extends View {

    private static final String TAG = SudokuGrid.class.getSimpleName();
    private Paint mPaint;
    private GestureDetector mGestureDetector;
    private float mCellWidth;
    private SudokuPuzzle mPuzzle;

    private SudokuCell mSelectedCell;

    public SudokuGrid(Context context) {
        super(context);
        init();
    }

    public SudokuGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SudokuGrid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);

        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener());

        mPuzzle = new SudokuGenerator().generate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width = 0;
        int height = 0;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            width = Math.min(widthSize, heightSize);
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            height = Math.min(width, heightSize);
        }

        mCellWidth = width / 9;

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the border
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#666666"));
        mPaint.setStrokeWidth(12);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        // Draw the different coloured rectangles for a nicer view
        mPaint.setColor(Color.argb(25, 0, 0, 0));
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, mCellWidth * 3, mCellWidth * 3, mPaint);
        canvas.drawRect(mCellWidth * 6, 0, mCellWidth * 9, mCellWidth * 3, mPaint);
        canvas.drawRect(mCellWidth * 3, mCellWidth * 3, mCellWidth * 6, mCellWidth * 6, mPaint);
        canvas.drawRect(0, 6 * mCellWidth, mCellWidth * 3, mCellWidth * 9, mPaint);
        canvas.drawRect(mCellWidth * 6, 6 * mCellWidth, mCellWidth * 9, mCellWidth * 9, mPaint);

        // If a cell is selected draw two semi transparent rectangles that overlap to show which cell is selected
        if (mSelectedCell != null) {
            mPaint.setColor(Color.argb(100, 102, 160, 255));
            canvas.drawRect(mSelectedCell.x() * mCellWidth, 0, (mSelectedCell.x() + 1) * mCellWidth, getMeasuredHeight(), mPaint);
            canvas.drawRect(0, mSelectedCell.y() * mCellWidth, getMeasuredWidth(), (mSelectedCell.y() + 1) * mCellWidth, mPaint);
        }

        // Draw that sweet sexy raster
        mPaint.setColor(Color.parseColor("#666666"));
        mPaint.setStyle(Paint.Style.STROKE);
        for (int i = 1; i < 9; i++) {
            if (i % 3 == 0) {
                mPaint.setStrokeWidth(6);
            }
            else {
                mPaint.setStrokeWidth(3);
            }
            canvas.drawLine(i * mCellWidth, 0, i * mCellWidth, getMeasuredHeight(), mPaint);
        }
        for (int i = 1; i < 9; i++) {
            if (i % 3 == 0) {
                mPaint.setStrokeWidth(6);
            }
            else {
                mPaint.setStrokeWidth(3);
            }
            canvas.drawLine(0, i * mCellWidth, getMeasuredWidth(), i * mCellWidth, mPaint);
        }

        for (final SudokuCell cell : mPuzzle.cells()) {
            // Draw a number to see what it would look like when the app is done! :D
            mPaint.setColor(Color.parseColor("#666666"));
            mPaint.setStyle(Paint.Style.FILL);
            mPaint.setTextSize(75);
            mPaint.setTextAlign(Paint.Align.CENTER);
            mPaint.setFakeBoldText(true);
            float textY = cell.y() * mCellWidth + mCellWidth / 2 - (mPaint.descent() + mPaint.ascent()) / 2;
            float textX = cell.x() * mCellWidth + mCellWidth / 2;
            canvas.drawText(Integer.toString(cell.value()), textX, textY, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = mGestureDetector.onTouchEvent(event);
        float x = event.getX();
        float y = event.getY();
        Log.d(TAG, "You touched me at these coords: " + x + "," + y);
        if (x != 0 && y != 0) {
            // determine touched cell
            int cellX = (int) (x / mCellWidth);
            int cellY = (int) (y / mCellWidth);
            mSelectedCell = new SudokuCell(cellX, cellY, 5);
            Log.d(TAG, "User touched cell: " + cellX + "," + cellY);

            // Determine square
            int squareX = (int) (x / (mCellWidth * 3));
            int squareY = (int) (y / (mCellWidth * 3));
            Log.d(TAG, "User touched square: " + squareX + "," + squareY);
            invalidate();
        }
        else {
            Log.d(TAG, "No cell touched");
        }
        return result;
    }
}
