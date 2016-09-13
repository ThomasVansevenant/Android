package com.thomasvansevenant.choices;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ContactActivity extends AppCompatActivity {
    private static final String ARG_CONTACT_NAME = "answer";

    @Bind(R.id.contactName)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        Intent intent = getIntent();
        ButterKnife.bind(this);
        String contactName = intent.getStringExtra(ARG_CONTACT_NAME);
        textView.setText(contactName);


    }
}
