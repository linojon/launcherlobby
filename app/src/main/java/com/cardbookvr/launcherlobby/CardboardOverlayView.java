package com.cardbookvr.launcherlobby;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class CardboardOverlayView extends LinearLayout{
    private final OverlayEyeView leftView;
    private final OverlayEyeView rightView;

    List<Shortcut> shortcuts = new ArrayList<Shortcut>();

    public CardboardOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        setVisibility(View.VISIBLE);

        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1.0f);
        params.setMargins(0, 0, 0, 0);

        leftView = new OverlayEyeView(context, attrs);
        leftView.setLayoutParams(params);
        addView(leftView);

        rightView = new OverlayEyeView(context, attrs);
        rightView.setLayoutParams(params);
        addView(rightView);

        setDepthOffset(0.1f);
        setColor(Color.rgb(150, 255, 180));
    }

    float lastHeadOffset = 0;
    float headOrigin = 0;
    public void setHeadOffset(float headOffset){

        float dHeadOffset = headOffset - lastHeadOffset;
        if(dHeadOffset > Math.PI - 1){  //1 is some number which is greater than a reaonable delta and smaller than pi
            dHeadOffset -= Math.PI + Math.PI;
        }
        if(dHeadOffset < -Math.PI - 1){
            dHeadOffset += Math.PI + Math.PI;
        }
        headOrigin += dHeadOffset;

        if(headOrigin > 0.5)      //Don't let them scroll into nothing
            headOrigin = 0.5f;

        leftView.setHeadOffset(headOrigin);
        rightView.setHeadOffset(headOrigin);
        lastHeadOffset = headOffset;
    }

    public void setDepthOffset(float offset) {
        leftView.setOffset(offset);
        rightView.setOffset(-offset);
    }

    public void setColor(int color) {
        leftView.setColor(color);
        rightView.setColor(color);
    }

    public void addShortcut(Shortcut shortcut){
        shortcuts.add(shortcut);
        leftView.addShortcut(shortcut);
        rightView.addShortcut(shortcut);
    }

    ///////////////////////////////////////////////
    private class OverlayEyeView extends ViewGroup {
        private final List<TextView> textViews = new ArrayList<TextView>();
        private final List<ImageView> imageViews = new ArrayList<ImageView>();
        private float offset;
        private int color;

//        CardboardOverlayView owner;
        private Context context;
        private AttributeSet attrs;

        public OverlayEyeView(Context context, AttributeSet attrs) {
            super(context, attrs);
            this.context = context;
            this.attrs = attrs;
        }

        public void addShortcut(Shortcut shortcut){
            ImageView imageView = new ImageView(context, attrs);
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setAdjustViewBounds(true);  // Preserve aspect ratio.
            imageView.setImageDrawable(shortcut.icon);
            addView(imageView);
            imageViews.add(imageView);

            TextView textView = new TextView(context, attrs);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14.0f);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
//            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.WHITE);
            textView.setText(shortcut.name);
            addView(textView);
            textViews.add(textView);
        }


        public void setColor(int color) {
           this.color = color;
        }

        public void setOffset(float offset) {
            this.offset = offset;
        }

        public void setHeadOffset(float headOffset){
            //Log.d(TAG, textInitX + ", " + headOffset);
            int count = 0;
            for(TextView textView : textViews) {
                textView.setX(headOffset * 1000 + count * 500 + offset);
                count++;
            }
            count = 0;
            for(ImageView imageView : imageViews) {
                imageView.setX(headOffset * 1000 + count * 500 + 20 + offset);
                count++;
            }
        }

        float leftMargin = 0;

        @Override
        protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
            // Width and height of this ViewGroup
            final int width = right - left;
            final int height = bottom - top;

            // Vertical position of the text, specified in fractions of this ViewGroup's height.
            final float verticalTextPos = 0.52f;
            // Layout TextView
            float topMargin = height * verticalTextPos;
             leftMargin = offset * width;
            for(TextView textView : textViews) {
                textView.layout(
                        (int) leftMargin, (int) topMargin,
                        (int) (leftMargin + width), (int) (topMargin + height * (1.0f - verticalTextPos))
                );
            }

            // The size of the image, given as a fraction of the dimension as a ViewGroup.
            // We multiply both width and heading with this number to compute the image's bounding
            // box. Inside the box, the image is the horizontally and vertically centered.
            final float imageSize = 0.2f;

            // The fraction of this ViewGroup's height by which we shift the image off the
            // ViewGroup's center. Positive values shift downwards, negative values shift upwards.
            final float verticalImageOffset = -0.07f;

            // Layout ImageView
            float adjustedOffset = offset;// + owner.headOffset;
            // If the half screen width is bigger than 1000 pixels, that means it's a big screen
            // phone and we need to use a different offset value.
            if (width > 1000) {
                adjustedOffset = 3.8f * offset;
            }
            //Log.d("onLayout", "adjoffset " + adjustedOffset);
            float imageMargin = (1.0f - imageSize) / 2.0f;
             leftMargin = (int) (width * (imageMargin + adjustedOffset));
             topMargin = (int) (height * (imageMargin + verticalImageOffset));
            Log.d("onLayout", "width, leftMargin " + width + ", " + leftMargin);
            for(ImageView imageView : imageViews) {
                imageView.layout(
                        (int) leftMargin, (int) topMargin,
                        (int) (leftMargin + width * imageSize), (int) (topMargin + height * imageSize));

                imageView.offsetLeftAndRight((int) (-leftMargin));
            }
        }
    }


}
