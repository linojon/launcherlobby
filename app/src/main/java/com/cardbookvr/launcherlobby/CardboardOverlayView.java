package com.cardbookvr.launcherlobby;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CardboardOverlayView extends LinearLayout{

    public CardboardOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);

        TextView textView = new TextView(context, attrs);
        addView(textView);

        // Set some reasonable defaults.
        textView.setTextColor(Color.rgb(150, 255, 180));
        setVisibility(View.VISIBLE);

        textView.setText("Hello Virtual World!");
    }





}
