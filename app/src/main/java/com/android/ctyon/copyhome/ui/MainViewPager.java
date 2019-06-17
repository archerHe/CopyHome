package com.android.ctyon.copyhome.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.jar.Attributes;

public class MainViewPager extends ViewPager {
    private ArrayList<View> mViewArrayList;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    @Override
    public void setCurrentItem(int item) {
        // false,  去除切换的动画效果
        super.setCurrentItem(item, true);
    }

    public MainViewPager(Context context) {
        super(context);
        this.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    public MainViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    public class CancelAnimation implements ViewPager.PageTransformer{
        private static final float MIN_SCALE = 0.75f;
        @Override
        public void transformPage(@NonNull View view, float v) {
            if (v <= 0f) {
                view.setTranslationX(0f);
                view.setScaleX(1f);
                view.setScaleY(1f);
            } else if (v <= 1f) {
                final float scaleFactor = MIN_SCALE + (1 - MIN_SCALE) * (1 - Math.abs(v));
                view.setAlpha(1 - v);
                view.setPivotY(0.5f * view.getHeight());
                view.setTranslationX(view.getWidth() * -v);
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);
            }
        }
    }

    public class ZoomOutPageTransformer implements ViewPager.PageTransformer {
        private static final float MIN_SCALE = 0.85f;
        private static final float MIN_ALPHA = 0.5f;

        public void transformPage(View view, float position) {
            int pageWidth = view.getWidth();
            int pageHeight = view.getHeight();

            if (position < -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 1) { // [-1,1]
                // Modify the default slide transition to shrink the page as well
                float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
                float vertMargin = pageHeight * (1 - scaleFactor) / 2;
                float horzMargin = pageWidth * (1 - scaleFactor) / 2;
                if (position < 0) {
                    view.setTranslationX(horzMargin - vertMargin / 2);
                } else {
                    view.setTranslationX(-horzMargin + vertMargin / 2);
                }

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

                // Fade the page relative to its size.
                view.setAlpha(MIN_ALPHA +
                        (scaleFactor - MIN_SCALE) /
                                (1 - MIN_SCALE) * (1 - MIN_ALPHA));

            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        }
    }
}

