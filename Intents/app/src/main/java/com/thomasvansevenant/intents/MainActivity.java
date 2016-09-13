package com.thomasvansevenant.intents;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 1234;

    @Bind(R.id.speech)
    TextView speech;
    ArrayList<String> matches_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button_record_command)
    void recordSpeech() {

        Log.i(Constants.MAIN_ACTIVITY, "Pushed on record button");
        if (isConnected()) {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            Log.i(Constants.MAIN_ACTIVITY, String.valueOf(isIntentSafe(intent)));
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            if (isIntentSafe(intent)) {
                startActivityForResult(intent, REQUEST_CODE);
            } else {
                Toast.makeText(getApplicationContext(), R.string.choose_another_app, Toast.LENGTH_LONG).show();
            }


            Log.i(Constants.MAIN_ACTIVITY, "Connected the to internet");
        } else {
            Toast.makeText(getApplicationContext(), R.string.connect_internet, Toast.LENGTH_LONG).show();
            Log.i(Constants.MAIN_ACTIVITY, "Not connected to the internet");
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE: {
                if (resultCode == RESULT_OK && null != data) {
                    matches_text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    showDialog();
                }
                break;
            }

        }
    }

    //Check if device is connected to the internet
    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        return net != null && net.isAvailable() && net.isConnected();
    }


    private void showDialog() {
        final String[] a = new String[matches_text.size()];
        matches_text.toArray(a);

        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Select answer")
                .setItems(a, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String text = a[which];
                        speech.setText(text);
                        checkText(text);
                    }
                })
                .setIcon(android.R.drawable.ic_menu_add)
                .show();
    }

    private boolean isIntentSafe(Intent intent) {
        boolean safe = false;
        // Check if an Activity exists to perform this action.
        PackageManager pm = getPackageManager();
        ComponentName cn = intent.resolveActivity(pm);
        if (cn == null) {
            // If there is no Activity available to perform the action
            // Check to see if the Google Play Store is available.
            Uri marketUri =
                    Uri.parse("market://search?q=pname:com.myapp.packagename");
            Intent marketIntent = new Intent(Intent.ACTION_VIEW).setData(marketUri);
            // If the Google Play Store is available, use it to download an application
            // capable of performing the required action. Otherwise log an
            // error.
            if (marketIntent.resolveActivity(pm) != null) {
                startActivity(marketIntent);
            } else {
                Log.d(Constants.MAIN_ACTIVITY, "Market client not available.");
            }
        } else {
            safe = true;
        }
        return safe;

    }

    private void checkText(String text) {
        switch (text.toLowerCase()) {
            case "hello": {
                Toast.makeText(getApplicationContext(), R.string.andy_says_hello, Toast.LENGTH_LONG).show();
            }
        }
    }

}


