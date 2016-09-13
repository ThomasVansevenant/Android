package com.thomasvansevenant.choices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AnswerActivity extends AppCompatActivity {

    private static final String ARG_ANSWER = "answer";

    @Bind(R.id.speech_textview)
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        Intent intent = getIntent();
        ButterKnife.bind(this);
        String word = intent.getStringExtra(ARG_ANSWER);
        textView.setText(word);
        Log.i(Constants.ANSWER_ACTIVITY, "AnswerActivity created");
    }
}
