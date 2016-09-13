package com.thomasvansevenant.diary.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import com.thomasvansevenant.diary.R;
import com.thomasvansevenant.diary.Utility;
import com.thomasvansevenant.diary.persistence.MyDB;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InsertDiaryActivity extends AppCompatActivity {

    @Bind(R.id.diary_title_edittext)
    EditText titleEditText;

    @Bind(R.id.diary_context_edittext)
    EditText contextEditText;

    @BindString(R.string.error_title)
    String errorTitle;
    @BindString(R.string.error_context)
    String errorContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_diary);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.add_diary_button)
    public void addDiary(View view) {
        if (titleEditText.getText().toString().isEmpty()) {
            titleEditText.setError(errorTitle);
            return;
        }
        if (contextEditText.getText().toString().isEmpty()) {
            contextEditText.setError(errorContext);
            return;
        }
        titleEditText.setError(null);
        contextEditText.setError(null);

        addToDatabase();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
    }

    private void addToDatabase() {
        MyDB myDB = new MyDB(this);
        myDB.insertDiary(
                Utility.convertToString(titleEditText),
                Utility.convertToString(contextEditText), this);
    }


}
