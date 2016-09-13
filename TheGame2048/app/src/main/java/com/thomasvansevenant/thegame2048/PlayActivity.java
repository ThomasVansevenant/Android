package com.thomasvansevenant.thegame2048;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.thomasvansevenant.thegame2048.models.Board;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayActivity extends AppCompatActivity {

    private static final String ARG_BOARD_ARRAY = "board_array";
    boolean isNewGame;

    Board board;

    @Bind(R.id.score)
    TextView scoreTextView;

    @Bind(R.id.high_score)
    TextView high_scoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        isNewGame = false;
        if (getIntent().getExtras() != null) {
            isNewGame = getIntent().getExtras().getBoolean(Constants.IS_NEW_GAME, true);
        }

        if (savedInstanceState != null) {
            int[] array1d = savedInstanceState.getIntArray(ARG_BOARD_ARRAY);
            int score = savedInstanceState.getInt(Constants.HIGH_SCORE);
            int highscore = savedInstanceState.getInt(Constants.SCORE);
//            Log.i(Constants.LOG_TAG_PLAY_ACTIVITY, Arrays.toString(array1d));
            int[][] array2d = get2DArrayFrom1DArray(array1d, Constants.SIZE);
//            Log.i(Constants.LOG_TAG_PLAY_ACTIVITY, Arrays.deepToString(array2d));
            createBoard(array2d);
            board.setmScore(score);
            board.setmHighScore(highscore);
            adjustScore();
        } else {
            createBoard();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_play, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_restart) {
            Log.i(Constants.LOG_TAG_PLAY_ACTIVITY, "restart clicked");
            restartGame();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void makeToast(String text) {
        Toast.makeText(PlayActivity.this, text, Toast.LENGTH_SHORT).show();
    }

    private void restartGame() {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(Constants.IS_NEW_GAME, true);
        // set the new task and clear flags
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        createBoard();
        adjustScore();
    }

    private void createBoard(int[][]... params) {
        //Add board to PlayActivity
        board = (Board) findViewById(R.id.board);
        if (params.length > 0) {
            board.reCreateBoard(params[0]);
        }

        board.setOnTouchListener(new SwipeListener(board.getContext()) {
            @Override
            public void onSwipeBottom() {
                board.moveDown();
                adjustScore();
//                makeToast("bottom");
            }

            @Override
            public void onSwipeLeft() {
                board.moveLeft();
                adjustScore();
//                makeToast("left");
            }

            @Override
            public void onSwipeRight() {
                board.moveRight();
                adjustScore();
//                makeToast("right");
            }

            @Override
            public void onSwipeTop() {
                board.moveUp();
                adjustScore();
//                makeToast("top");
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isNewGame) {
            board.loadGame(this);
        }
        adjustScore();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adjustScore();
    }

    @Override
    protected void onPause() {
        super.onPause();
        adjustScore();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        board.saveGame(this);
        adjustScore();
    }

    private void adjustScore() {
        scoreTextView.setText(String.valueOf(board.getmScore()));
        board.checkHighScore();
        high_scoreTextView.setText(String.valueOf(board.getmHighScore()));
//        Log.i(Constants.LOG_TAG_PLAY_ACTIVITY, String.valueOf(board.getmScore()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        int[][] boardArray = board.getmBoardArray();
        int[] array1d = get1DArrayFrom2DArray(boardArray, Constants.SIZE);
        outState.putIntArray(ARG_BOARD_ARRAY, array1d);
        outState.putInt(Constants.SCORE, board.getmScore());
        outState.putInt(Constants.HIGH_SCORE, board.getmHighScore());
    }

    private int[] get1DArrayFrom2DArray(int[][] array2d, int size) {
        int[] array1d = new int[size * size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < array2d[i].length; j++) {
                array1d[j * size + i] = array2d[i][j];
            }
        }
        return array1d;
    }

    private int[][] get2DArrayFrom1DArray(int[] array1d, int size) {

        int array2d[][] = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                array2d[i][j] = array1d[(j * size) + i];
            }
        }
        return array2d;

    }
}

