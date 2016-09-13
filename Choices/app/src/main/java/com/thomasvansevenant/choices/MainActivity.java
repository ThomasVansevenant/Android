package com.thomasvansevenant.choices;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindString;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;
    private static final String ARG_ANSWER = "answer";
    private static final String ARG_CONTACT_NAME = "answer";
    private static final int PICK_CONTACT = 4321;

    Intent intent;

    ArrayAdapter mActionsAdapter;

    @BindString(R.string.choose_another_app)
    String choose_another_app;

    @BindString(R.string.no_internet_connnection)
    String no_internet_connection;

    @BindString(R.string.select_answer)
    String select_answer;

    ArrayList<String> matches_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mActionsAdapter = new ArrayAdapter<>(getApplicationContext(),
                R.layout.list_item_activity,
                R.id.list_item_paradox_textview,
                Activities.activityNames);
        ListView listView = (ListView) findViewById(R.id.listview_activities);
        listView.setAdapter(mActionsAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i(Constants.MAIN_ACTIVITY, "Position: " + position);
                onActionSelected(position);
            }
        });
    }

    private void activityStarter(String uriString, String intentAction) {
        Intent intent;

        if (uriString == null) {
            intent = new Intent(intentAction);
            if (isIntentSafe(intent)) {
                startActivity(intent);
            }
        } else {
            Uri uri = Uri.parse(uriString);
            intent = new Intent(intentAction, uri);
            if (isIntentSafe(intent)) {
                startActivity(intent);
            }
        }
    }

    private void openWebPage() {
        activityStarter("http://www.android.com",
                Intent.ACTION_VIEW);
    }


    private void openContacts() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);
        Log.i(Constants.MAIN_ACTIVITY, "openContacts");
    }

    private void openDialer(String tel) {
        String telNumber = "tel:" + tel;
        activityStarter(telNumber,
                Intent.ACTION_DIAL);
    }

    private void openGoogleSearch() {
        activityStarter(null, Intent.ACTION_WEB_SEARCH);
    }

    private void openVoice() {
        startVoiceCommand();
    }


    private void startVoiceCommand() {
        Log.i(Constants.MAIN_ACTIVITY, "Pushed on record button");
        if (isConnected()) {
            intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            if (isIntentSafe(intent)) {
                startActivityForResult(intent, REQUEST_CODE);
            } else {
                Toast.makeText(getApplicationContext(), choose_another_app, Toast.LENGTH_LONG).show();
            }

            Log.i(Constants.MAIN_ACTIVITY, "Connected the to internet");
        } else {
            Toast.makeText(getApplicationContext(), no_internet_connection, Toast.LENGTH_LONG).show();
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
            case (PICK_CONTACT):
                if (resultCode == RESULT_OK && null != data) {
                    Uri contactData = data.getData();
                    Cursor phone = getContentResolver().query(contactData, null, null, null, null);
                    if (phone.moveToFirst()) {
                        String contactName = phone.getString(phone.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        Intent intent = new Intent(getApplicationContext(), ContactActivity.class);
                        intent.putExtra(ARG_CONTACT_NAME, contactName);
                        Log.i(Constants.MAIN_ACTIVITY, "The contactName is:" + contactName);
                        startActivity(intent);
                    }
                }
                break;

        }
    }


    private void showDialog() {
        final String[] a = new String[matches_text.size()];
        matches_text.toArray(a);

        new AlertDialog.Builder(MainActivity.this)
                .setTitle(select_answer)
                .setItems(a, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String word = a[which];
                        Intent intent = new Intent(getApplicationContext(), AnswerActivity.class);

                        intent.putExtra(ARG_ANSWER, word);
                        Log.i(Constants.MAIN_ACTIVITY, "The word is:" + word);
                        startActivity(intent);
                    }
                })
                .setIcon(android.R.drawable.ic_menu_add)
                .show();
    }

    //Check if device is connected to the internet
    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        return net != null && net.isAvailable() && net.isConnected();
    }

    //see if intent can be opened by any app or activity
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

    private void onActionSelected(int position) {
        switch (position) {
            case 0:
                openWebPage();
                break;
            case 1:
                openContacts();
            case 2:
                openDialer(Constants.TEL_NUMBER);
                break;
            case 3:
                openGoogleSearch();
                break;
            case 4:
                openVoice();
                break;
        }
    }
}
