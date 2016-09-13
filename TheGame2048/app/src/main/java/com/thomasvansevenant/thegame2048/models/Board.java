package com.thomasvansevenant.thegame2048.models;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.GridLayout;

import com.thomasvansevenant.thegame2048.Constants;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Random;


public class Board extends GridLayout {

    private Card[][] board;
    private static final String LOG_TAG = Board.class.getSimpleName();
    private int height, width;
    private static final int SIZE = 4;
    public int mScore;
    public int mHighScore;
    public int[][] getmBoardArray() {
        return mBoardArray;
    }

    public void setBoardArray(int[][] board) {
        this.mBoardArray = board;
    }

    private int[][] mBoardArray;


    public Board(Context context) {
        super(context);
        initBoard();
            }

    public Board(Context context, AttributeSet attrs) {
        super(context, attrs);
        initBoard();
    }

    public Board(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBoard();
    }

    public void initBoard() {
        board = new Card[SIZE][SIZE];
        mBoardArray = new int[SIZE][SIZE];
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                board[x][y] = new Card(getContext());
            }
        }
        Log.i(LOG_TAG, Arrays.deepToString(mBoardArray));
        mScore = 0;
        mHighScore = 0;
        addRandomCard();
        addRandomCard();
        addAllCards();
    }


    /**
     * Called when the size of the screen changes
     *
     * @param w    new width
     * @param h    new height
     * @param oldw old width
     * @param oldh old heigth
     */

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        height = h;
        width = w;
//        addRandomCard();
        addAllCards();
        Log.i(LOG_TAG, "onSizeChanged called");
    }

    /**
     * adds 16 cards to the board
     */

    private void addAllCards() {
        int cardSize = calculateCardsSize();
        removeAllViews();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                board[x][y].setCardTextView(mBoardArray[x][y]);
                board[x][y].setBackgroundColor(mBoardArray[x][y]);
                this.addView(board[x][y], cardSize, cardSize);
            }
        }     }

    public void reCreateBoard(int[][] array) {
        initBoard();
        setBoardArray(array);
        addAllCards();
    }

    //Calculate the bar height of the landscape view
    private int getActionBarHeight() {
        int actionBarHeight;
        TypedArray styledAttributes = getContext().getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        actionBarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionBarHeight + 12;
    }

    private int calculateCardsSize() {
        int actionBarHeight = getActionBarHeight();
        int cardSize;
        if (height > width) {
            cardSize = (width - 10) / 4;
        } else {
            int heightMinusToolbar = height - actionBarHeight;
            cardSize = (getResources().getDisplayMetrics().heightPixels - (10 * SIZE * SIZE)) / (SIZE);
        }
        return cardSize;
    }

    public void moveUp() {
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 4; x++) {
                for (int dX = x + 1; dX < 4; dX++) {
                    int cNumber = mBoardArray[x][y];
                    int dNumber = mBoardArray[dX][y];

                    if (dNumber > 0) {
                        if (cNumber <= 0) {
                            mBoardArray[x][y] = dNumber;
                            mBoardArray[dX][y] = 0;
                        } else if (dNumber == cNumber) {
                            mBoardArray[x][y] = dNumber * 2;
                            mBoardArray[dX][y] = 0;

                            addScore(mBoardArray[x][y]);
                        }
                    }
                }
            }
        }
        addRandomCard();
        addAllCards();
    }

    public void moveDown() {
        for (int y = 0; y < 4; y++) {
            for (int x = 3; x >= 0; x--) {
                for (int dX = x - 1; dX >= 0; dX--) {
                    int cNumber = mBoardArray[x][y];
                    int dNumber = mBoardArray[dX][y];

                    if (dNumber > 0) {
                        if (cNumber <= 0) {
                            mBoardArray[x][y] = dNumber;
                            mBoardArray[dX][y] = 0;
                        } else if (dNumber == cNumber) {
                            mBoardArray[x][y] = dNumber * 2;
                            mBoardArray[dX][y] = 0;

                            addScore(mBoardArray[x][y]);
                        }
                    }
                }
            }
        }
        addRandomCard();
        addAllCards();
    }

    public void moveLeft() {
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int dY = y + 1; dY < 4; dY++) {
                    int cNumber = mBoardArray[x][y];
                    int dNumber = mBoardArray[x][dY];

                    if (dNumber > 0) {
                        if (cNumber <= 0) {
                            mBoardArray[x][y] = dNumber;
                            mBoardArray[x][dY] = 0;
                        } else if (dNumber == cNumber) {
                            mBoardArray[x][y] = dNumber * 2;
                            mBoardArray[x][dY] = 0;

                            addScore(mBoardArray[x][y]);
                        }
                    }
                }
            }
        }
        addRandomCard();
        addAllCards();
    }

    public void addScore(int value) {
        mScore += value;
    }

    public int getmScore() {
        return mScore;
    }

    public void setmScore(int mScore) {
        this.mScore = mScore;
    }

    public void setmHighScore(int mHighScore) {
        this.mHighScore = mHighScore;
    }

    public void moveRight() {
        for (int x = 0; x < 4; x++) {
            for (int y = 3; y >= 0; y--) {
                for (int dY = y - 1; dY >= 0; dY--) {
                    int cNumber = mBoardArray[x][y];
                    int dNumber = mBoardArray[x][dY];

                    if (dNumber > 0) {
                        if (cNumber <= 0) {
                            mBoardArray[x][y] = dNumber;
                            mBoardArray[x][dY] = 0;
                        } else if (dNumber == cNumber) {
                            mBoardArray[x][y] = dNumber * 2;
                            mBoardArray[x][dY] = 0;

                            addScore(mBoardArray[x][y]);
                        }
                    }
                }
            }
        }
        addRandomCard();
        addAllCards();
    }

//    public void loadHighScore(Context context) {
//        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
//        mHighScore = sharedPref.getInt(Constants.HIGH_SCORE, 0);
//    }
//
//    public void saveHighScore(Context context) {
//        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        editor.putInt(Constants.SCORE, mScore);
//        editor.putInt(Constants.HIGH_SCORE, mHighScore);
//        editor.commit();
//    }

    public void loadGame(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);

        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                mBoardArray[x][y] = sharedPref.getInt(x + "_" + y, 0);
            }
        }
        mHighScore = sharedPref.getInt(Constants.HIGH_SCORE, 0);
        mScore = sharedPref.getInt(Constants.SCORE, 0);
    }

    public void saveGame(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constants.PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        for (int x = 0; x < 4; ++x) {
            for (int y = 0; y < 4; ++y) {
                editor.putInt(x + "_" + y, mBoardArray[x][y]);
            }
        }
        editor.putInt(Constants.HIGH_SCORE, mHighScore);
        editor.putInt(Constants.SCORE, mScore);
        editor.commit();
    }

    public int getmHighScore() {
        return mHighScore;
    }

    public void checkHighScore() {
        if (mScore >= mHighScore) {
            mHighScore = mScore;
        }
    }


    public void addRandomCard() {
        Random random = new SecureRandom();

        int xPos = random.nextInt(SIZE);
        int yPos = random.nextInt(SIZE);

        while (mBoardArray[xPos][yPos] != 0) {
            xPos = random.nextInt(SIZE);
            yPos = random.nextInt(SIZE);
        }
        mBoardArray[xPos][yPos] = 2;
    }


//    public int[][] getStartArray() {
//        return startArray;
//    }
//
//    private int findTarget(int array[], int x, int stop) {
//        int t;
//        if (x == 0) {
//            return x;
//        }
//        for (t = x - 1; t >= 0; t--) {
//            if (array[t] != 0) {
//                if (array[t] != array[x]) {
//                    // merge is not possible, take next position
//                    return t + 1;
//                }
//                return t;
//            } else {
//                // we should not slide further, return this one
//                if (t == stop) {
//                    return t;
//                }
//            }
//        }
//        // we did not find a
//        return x;
//    }
//
//    private boolean slideArray(int array[]) {
//        boolean success = false;
//        int x, t, stop = 0;
//
//        for (x = 0; x < SIZE; x++) {
//            if (array[x] != 0) {
//                t = findTarget(array, x, stop);
//                // if target is not original position, then move or merge
//                if (t != x) {
//                    // if target is zero, this is a move
//                    if (array[t] == 0) {
//                        array[t] = array[x];
//                    } else if (array[t] == array[x]) {
//                        // merge (increase power of two)
//                        array[t]++;
//                        // increase score
//                        score += array[t];
//                        // set stop to avoid double merge
//                        stop = t + 1;
//                    }
//                    array[x] = 0;
//                    success = true;
//                }
//            }
//        }
//        return success;
//    }
//
//    private void rotateBoard() {
//        int i, j, n = SIZE;
//        int tmp;
//        for (i = 0; i < n / 2; i++) {
//            for (j = i; j < n - i - 1; j++) {
//                tmp = mBoardArray[i][j];
//                mBoardArray[i][j] = mBoardArray[j][n - i - 1];
//                mBoardArray[j][n - i - 1] = mBoardArray[n - i - 1][n - j - 1];
//                mBoardArray[n - i - 1][n - j - 1] = mBoardArray[n - j - 1][i];
//                mBoardArray[n - j - 1][i] = tmp;
//            }
//        }
//    }

//    public boolean moveUp() {
//        boolean success = false;
//        int x;
//        for (x = 0; x < SIZE; x++) {
//            success = slideArray(mBoardArray[x]);
//        }
//        addAllCards();
//        Log.i(LOG_TAG, Arrays.deepToString(mBoardArray));
//        return success;
//
//    }

//    public boolean moveLeft() {
//        boolean success;
//        rotateBoard();
//        success = moveUp();
//        rotateBoard();
//        rotateBoard();
//        rotateBoard();
//        addAllCards();
//        Log.i(LOG_TAG, Arrays.deepToString(mBoardArray));
//        return success;
//    }
//
//    public boolean moveDown() {
//        boolean success;
//        rotateBoard();
//        rotateBoard();
//        success = moveUp();
//        rotateBoard();
//        rotateBoard();
//        addAllCards();
//        Log.i(LOG_TAG, Arrays.deepToString(mBoardArray));
//        return success;
//    }
//
//    public boolean moveRight() {
//        boolean success;
//        rotateBoard();
//        rotateBoard();
//        rotateBoard();
//        success = moveUp();
//        rotateBoard();
//        addAllCards();
//        Log.i(LOG_TAG, Arrays.deepToString(mBoardArray));
//        return success;
//    }
//
//    private boolean findPairDown() {
//        boolean success = false;
//        int x, y;
//        for (x = 0; x < SIZE; x++) {
//            for (y = 0; y < SIZE - 1; y++) {
//                if (mBoardArray[x][y] == mBoardArray[x][y + 1]) return true;
//            }
//        }
//        return success;
//    }

//    private int[][] testArray = {
//            {1, 2, 3, 4},
//            {5, 6, 7, 8},
//            {9, 10, 11, 12},
//            {13, 14, 15, 16}
//    };
//
//    private void convertTo1DArray(int[][] array) {
//        int[] oneDArray = new int[4];
//        for (int row = 0; row < array.length; row++) {
//            for (int column = 0; column < array[row].length; column++) {
//                oneDArray[row + 4*column] = array[row][column];
//            }
//        }
//        Log.i(LOG_TAG, oneDArray.toString());
//    }


    //    private void startBoard(int... ints){
//
//        board[0][0].setCardTextView(ints[0]);
//        board[0][1].setCardTextView(ints[1]);
//        board[0][2].setCardTextView(ints[2]);
//        board[0][3].setCardTextView(ints[3]);
//        board[1][0].setCardTextView(ints[4]);
//        board[1][1].setCardTextView(ints[5]);
//        board[1][2].setCardTextView(ints[6]);
//        board[1][3].setCardTextView(ints[7]);
//        board[2][0].setCardTextView(ints[8]);
//        board[2][1].setCardTextView(ints[9]);
//        board[2][2].setCardTextView(ints[10]);
//        board[2][3].setCardTextView(ints[11]);
//        board[3][0].setCardTextView(ints[12]);
//        board[3][1].setCardTextView(ints[13]);
//        board[3][2].setCardTextView(ints[14]);
//        board[3][3].setCardTextView(ints[15]);
//    }

//    private void startBoard2(){
//        int mBoardArray[][] =
//                {
//                        {0, 1, 2, 3},
//                        {3, 2, 1, 0},
//                        {3, 5, 6, 1},
//                        {3, 8, 3, 4}
//                };
//
//        for (int x = 0; x < 4; x++) {
//            for (int y = 0; y < 4; y++) {
//                board[x][y].setCardTextView(mBoardArray[x][y]);
//                this.addView(board[x][y], 1, cardHeight);
//                Log.i(LOG_TAG,String.valueOf(board[x][y]));
//            }
//        }
//    }
//    private void startBoard() {
//        int aNumbers[][] =
//                {
//                        {0, 1, 2, 3},
//                        {3, 2, 1, 0},
//                        {3, 5, 6, 1},
//                        {3, 8, 3, 4}
//                };
//
//        for (int a[] : aNumbers) {
//            board[]
//            for(int b : a){
//                Log.i(LOG_TAG, String.valueOf(b));
//            }
//
//            Arrays.fill();
//
//        }
//
//        for (int x = 0; x < 4; x++) {
//            for (int y = 0; y < 4; y++) {
//                board[x][y].setCardTextView(generateRandomNumbers(4));
//            }
//        }
//    }

    //    private void merge() {
//        int l = mBoardArray.length;
//        for (int row = l; l > 0; row--) {
//            for (int col = l - 1; l > 0; col--) {
//                while (mBoardArray[row][col] == 0 && mBoardArray[row][col] ==)
//            }
//        }
//    }
//
//    private void mergeUp() {
//        for (int x = 0; x < 4; x++) {
//            if (mBoardArray[0][x] == 0) {
//
////                mBoardArray[x][0] = mBoardArray[x + 1][0];
//                Log.i(LOG_TAG, "yep tis 0");
//            }
//        }
//
//        Log.i(LOG_TAG, Arrays.deepToString(mBoardArray));
//    }

//    public void moveBoardLeft() {
//        mergeUp();
////        rotateBoardCounterClockWise();
////        rotateBoardCounterClockWise();
////        rotateBoardCounterClockWise();
//        Log.i(LOG_TAG, mBoardArray.toString());
//        addAllCards();
//    }


//    private void setTestBoard() {
//        for (int x = 0; x < 4; x++) {
//            for (int y = 0; y < 4; y++) {
//                board[x][y].setCardTextView(x * 10 + y);
//            }
//        }
//    }

//    private int getScreenHeight(Context context) {
//        int height;
//
//        if (android.os.Build.VERSION.SDK_INT >= 13) {
//            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//            Display display = wm.getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//            height = size.y;
//        } else {
//            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//            Display display = wm.getDefaultDisplay();
//            height = display.getHeight();  // deprecated
//        }
//        return height;
//    }


//
//
//    private void rotateBoardCounterClockWise() {
//        int r = mBoardArray.length;
//        int c = mBoardArray[0].length;
//
//        //calculate the transpose
//        for (int i = 0; i < r; i++) {
//            for (int j = i + 1; j < c; j++) {
//                int save = mBoardArray[i][j];
//                mBoardArray[i][j] = mBoardArray[j][i];
//                mBoardArray[j][i] = save;
//            }
//        }
//        for (int i = 0; i < c; i++) {
//            for (int j = 0; j < (r / 2); j++) {
//                int save = mBoardArray[j][i];
//                mBoardArray[j][i] = mBoardArray[c - j - 1][i];
//                mBoardArray[c - j - 1][i] = save;
//            }
//        }
//    }
//
//    private int generateRandomNumbers(int value) {
//        Random r = new Random();
//        int i1 = r.nextInt(value) + 1;
//        return i1 * 2;
//    }

}
