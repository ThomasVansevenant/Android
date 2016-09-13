package com.thomasvansevenant.userinterfaces1;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelcomeActivity extends AppCompatActivity {


    /**
     * After Refactoring
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_about)
    void clickedAbout(){
        showAlertBox();
    }

    private void showAlertBox() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setTitle(R.string.dialog_title)
                .setMessage(R.string.dialog_message);
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    /**
     * Before refactoring
     */

//    private Button buttonAbout;
//

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_welcome);
//        buttonAbout = (Button) findViewById(R.id.button_about);
//
//        buttonAbout.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                showAlertBox();
//            }
//        });
//    }


}
