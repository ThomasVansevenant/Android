package com.thomasvansevenant.thegame2048.models;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.provider.CalendarContract;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.thomasvansevenant.thegame2048.Constants;
import com.thomasvansevenant.thegame2048.R;

/**
 * Created by ThomasVansevenant on 28/12/2015.
 */
public class Card extends FrameLayout {

    private static final String LOG_TAG = Card.class.getSimpleName();
    private TextView cardTextView;

    public Card(Context context) {
        super(context);
        cardTextView = new TextView(getContext());
        cardSettings();
    }

    public void setCardTextView(int value) {
        cardTextView.setText(String.valueOf(value));
    }

    /**
     * create textView + setting
     * add textView to card
     */
    private void cardSettings() {
        //getContext(): Returns the context the view is running in, through which it can access the current theme, resources, etc.
        cardTextView.setGravity(Gravity.CENTER);
        cardTextView.setTextColor(Color.parseColor("#FFFFFF"));
        cardTextView.setBackgroundColor(Color.parseColor("#ecc400"));
        cardTextView.setTypeface(null, Typeface.BOLD);
        cardTextView.setTextSize(32);

//        -1: fill_parent
//        -1: fill_parent
        LayoutParams layoutParams = new LayoutParams(-1, -1);
//        addPadding(cardTextView);
        layoutParams.setMargins(5, 5, 5, 5);

        //Adds a child view with the specified layout parameters.
        addView(cardTextView, layoutParams);
    }

    public void setBackgroundColor(int value) {
        switch (value) {
            case 2:
//                cardTextView.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.colorTile2));
//                cardTextView.setTextColor(Color.parseColor("#776e65"));
                changeSetting(false, R.color.colorTile2);
                break;
            case 4:
//                cardTextView.setBackgroundColor(Color.parseColor(Constants.COLOR_TILE_4));
//                cardTextView.setTextColor(Color.parseColor("#776e65"));
                changeSetting(false, R.color.colorTile4);
                break;
            case 8:
//                cardTextView.setBackgroundColor(Color.parseColor(Constants.COLOR_TILE_8));
                changeSetting(true, R.color.colorTile8);
                break;
            case 16:
//                cardTextView.setBackgroundColor(Color.parseColor(Constants.COLOR_TILE_16));
                changeSetting(true, R.color.colorTile16);
                break;
            case 32:
//                cardTextView.setBackgroundColor(Color.parseColor(Constants.COLOR_TILE_32));
                changeSetting(true, R.color.colorTile32);
                break;
            case 64:
//                cardTextView.setBackgroundColor(Color.parseColor(Constants.COLOR_TILE_64));
                changeSetting(true, R.color.colorTile64);
                break;
            case 128:
//                cardTextView.setBackgroundColor(Color.parseColor(Constants.COLOR_TILE_128));
                changeSetting(true, R.color.colorTile128);
                break;
            case 256:
//                cardTextView.setBackgroundColor(Color.parseColor(Constants.COLOR_TILE_256));
                changeSetting(true, R.color.colorTile256);
                break;
            case 512:
//                cardTextView.setBackgroundColor(Color.parseColor(Constants.COLOR_TILE_512));
                changeSetting(true, R.color.colorTile512);
                break;
            case 1024:
//                cardTextView.setBackgroundColor(Color.parseColor(Constants.COLOR_TILE_1024));
                changeSetting(true, R.color.colorTile1024);
                break;
            case 2048:
//                cardTextView.setBackgroundColor(Color.parseColor(Constants.COLOR_TILE_2048));
                changeSetting(true, R.color.colorTile2048);
                break;
            case 4096:
//                cardTextView.setBackgroundColor(Color.parseColor(Constants.COLOR_TILE_4096));
                changeSetting(true, R.color.colorTile4096);
                break;
            default:
//                cardTextView.setBackgroundColor(Color.parseColor(Constants.COLOR_TILE_0));
//                cardTextView.setText("");
                changeSetting(true, R.color.colorTile0);
                cardTextView.setText("");
                break;
        }

    }

    public void changeSetting(boolean isWhiteText, int colorID) {
        if (isWhiteText) {
            cardTextView.setTextColor(Color.parseColor("#FFFFFF"));
        } else {
            cardTextView.setTextColor(Color.parseColor("#776e65"));
        }
        cardTextView.setBackgroundColor(ContextCompat.getColor(getContext(), colorID));
    }


}
