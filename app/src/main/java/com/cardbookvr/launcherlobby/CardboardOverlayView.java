package com.cardbookvr.launcherlobby;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by Jonathan on 11/8/2015.
 */
public class CardboardOverlayView extends LinearLayout {

    public CardboardOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);

        TextView textView = new TextView(context, attrs);
        textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0f);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setGravity(Gravity.CENTER);
        textView.setShadowLayer(3.0f, 0.0f, 0.0f, Color.DKGRAY);
        addView(textView);

        // Set some reasonable defaults.
        //textView.setOffset(0.01f);
        textView.setTextColor(Color.rgb(150, 255, 180));
        setVisibility(View.VISIBLE);

        textView.setText("Yo Hello!");

    }

}
