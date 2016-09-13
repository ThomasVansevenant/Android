package com.thomasvansevenant.diary.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.thomasvansevenant.diary.R;
import com.thomasvansevenant.diary.models.Diary;
import com.thomasvansevenant.diary.persistence.MyDB;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @Bind(R.id.diary_detail_title_textview)
    TextView titleTextView;
    @Bind(R.id.diary_detail_context_textview)
    TextView detailTextView;
    @Bind(R.id.diary_detail_date_textview)
    TextView dateTextView;

    MyDB myDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        myDB = new MyDB(this);

        Bundle bundle = getIntent().getExtras();

        long id = bundle.getLong("SI");
        Diary diary = myDB.getDiary(this, id);

        titleTextView.setText(diary.getTitle());
        detailTextView.setText(diary.getContext());
        dateTextView.setText(diary.getDate());
    }


}
