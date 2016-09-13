package com.thomasvansevenant.diary;

import android.view.View;
import android.widget.EditText;

/**
 * Created by ThomasVansevenant on 25/01/2016.
 */
public class Utility {
    public final static String convertToString(EditText editText) {
        String text = editText.getText().toString();
        return upperCaseFirst(text);
    }

    private static final String upperCaseFirst(String value) {

        // Convert String to char array.
        char[] array = value.toCharArray();
        // Modify first element in array.
        array[0] = Character.toUpperCase(array[0]);
        // Return string.
        return new String(array);
    }
}
