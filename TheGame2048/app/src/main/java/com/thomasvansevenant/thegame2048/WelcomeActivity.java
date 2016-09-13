package com.thomasvansevenant.thegame2048;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @OnClick(R.id.button_continue)
    void clickedContinue() {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(Constants.IS_NEW_GAME, false);
        startActivity(intent);
    }

    @OnClick(R.id.button_new_game)
    void clickedNewGame() {
        Intent intent = new Intent(this, PlayActivity.class);
        intent.putExtra(Constants.IS_NEW_GAME, true);
        startActivity(intent);
    }

    @OnClick(R.id.button_about)
    void clickedAbout() {
        showAlertBox();
    }

    @OnClick(R.id.button_exit)
    void clickedExit() {
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Log.i(Constants.LOG_TAG_WELCOME_ACTIVITY, "Reset has been called");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_reset:

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }

    private void showAlertBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


}
