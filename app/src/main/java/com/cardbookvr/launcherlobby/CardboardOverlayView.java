package com.cardbookvr.launcherlobby;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CardboardOverlayView extends LinearLayout{
    private final CardboardOverlayEyeView leftView;
    private final CardboardOverlayEyeView rightView;
    private AlphaAnimation textFadeAnimation;

    public CardboardOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setVisibility(View.VISIBLE);

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);

        leftView = new CardboardOverlayEyeView(context, attrs);
        leftView.setLayoutParams(params);
        addView(leftView);

        rightView = new CardboardOverlayEyeView(context, attrs);
        rightView.setLayoutParams(params);
        addView(rightView);

        setDepthOffset(0.01f);
        setColor(Color.rgb(150, 255, 180));

        textFadeAnimation = new AlphaAnimation(1.0f, 0.0f);
        textFadeAnimation.setDuration(5000);
    }

    public void show3DToast(String message) {
        setText(message);
        setTextAlpha(1f);
        textFadeAnimation.setAnimationListener(new EndAnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                setTextAlpha(0f);
            }
        });
        startAnimation(textFadeAnimation);
    }

    private abstract class EndAnimationListener implements Animation.AnimationListener {
        @Override public void onAnimationRepeat(Animation animation) {}
        @Override public void onAnimationStart(Animation animation) {}
    }

    private void setDepthOffset(float offset) {
        leftView.setOffset(offset);
        rightView.setOffset(-offset);
    }

    private void setColor(int color) {
        leftView.setColor(color);
        rightView.setColor(color);
    }

    private void setTextAlpha(float alpha) {
        leftView.setTextViewAlpha(alpha);
        rightView.setTextViewAlpha(alpha);
    }

    private void setText(String text) {
        leftView.setText(text);
        rightView.setText(text);
    }

    private class CardboardOverlayEyeView extends ViewGroup {
        private final TextView textView;
        private float offset;

        public CardboardOverlayEyeView(Context context, AttributeSet attrs) {
            super(context, attrs);

            textView = new TextView(context, attrs);
            textView.setGravity(Gravity.CENTER);
            addView(textView);
        }

        private void setTextAlpha(float alpha) {
            leftView.setTextViewAlpha(alpha);
            rightView.setTextViewAlpha(alpha);
        }

        public void setColor(int color) {
            textView.setTextColor(color);
        }

        public void setTextViewAlpha(float alpha) {
            textView.setAlpha(alpha);
        }

        public void setText(String text) {
            textView.setText(text);
        }

        public void setOffset(float offset) {
            this.offset = offset;
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
            float leftMargin = offset * width;
            textView.layout(
                    (int) leftMargin, (int) topMargin,
                    (int) (leftMargin + width), (int) (topMargin + height * (1.0f - verticalTextPos))
            );
        }
    }


}
