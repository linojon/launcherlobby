package com.cardbookvr.launcherlobby;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CardboardOverlayView extends LinearLayout{

    public CardboardOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setVisibility(View.VISIBLE);

        CardboardOverlayEyeView textView = new CardboardOverlayEyeView(context, attrs);
        addView(textView);

        textView.setColor(Color.rgb(150, 255, 180));
        textView.setText("Hello Virtual World!");
    }


    private class CardboardOverlayEyeView extends ViewGroup {
        private final TextView textView;

        public CardboardOverlayEyeView(Context context, AttributeSet attrs) {
            super(context, attrs);

            textView = new TextView(context, attrs);
            textView.setGravity(Gravity.CENTER);
            addView(textView);
        }

        public void setColor(int color) {
            textView.setTextColor(color);
        }

        public void setText(String text) {
            textView.setText(text);
        }

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            // Width and height of this ViewGroup
            final int width = right - left;
            final int height = bottom - top;

            // Vertical position of the text, in fractions of this ViewGroup's height
            final float verticalTextPos = 0.52f;

            // Layout TextView
            float topMargin = height * verticalTextPos;
            textView.layout(
                    0, (int) topMargin,
                    width, (int) (topMargin + height * (1.0f - verticalTextPos))
            );
        }
    }


}
