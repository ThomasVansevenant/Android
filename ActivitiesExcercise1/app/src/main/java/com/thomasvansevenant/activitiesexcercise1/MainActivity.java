package com.thomasvansevenant.activitiesexcercise1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int countOnCreate = 0,
            countOnStart = 0,
            countOnResume = 0,
            countOnRestart = 0;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    /**
     * The activity is being created.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        countOnCreate++;
        setContentView(R.layout.activity_main);
        setTextView();
        writeAllLogs();
    }

    /**
     * // The activity is about to become visible.
     */
    @Override
    protected void onStart() {
        super.onStart();
        countOnStart++;
        setTextView();
        writeAllLogs();
    }

    /**
     * // The activity has become visible (it is now "resumed").
     */
    @Override
    protected void onResume() {
        super.onResume();
        countOnResume++;
        setTextView();
        writeAllLogs();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        countOnRestart++;
        setTextView();
        writeAllLogs();
    }

    private void setTextView() {
        TextView textView = (TextView) findViewById(R.id.textView_output_first_activity);
        textView.setText(String.format("" +
                        "onCreate() calls: %s\n" +
                        "onStart calls: %s\n" +
                        "onResume() calls: %s\n" +
                        "onRestart calls: %s",
                String.valueOf(countOnCreate),
                String.valueOf(countOnStart),
                String.valueOf(countOnResume),
                String.valueOf(countOnRestart)));
    }

    /**
     * creates and starts the second activity
     *
     * @param v
     */
    public void loadSecondActivity(View v) {
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    /**
     * creates one log
     *
     * @param call  example: "OnCreate calls: "
     * @param value the counter value
     */
    private void writeLog(String call, int value) {
        Log.i(LOG_TAG, call + String.valueOf(value));
    }

    /**
     * write all logs to the console.
     */
    private void writeAllLogs() {
        writeLog("onCreate calls: ", countOnCreate);
        writeLog("onStart calls: ", countOnStart);
        writeLog("onResume calls: ", countOnResume);
        writeLog("OnRestart calls", countOnRestart);
    }
}
