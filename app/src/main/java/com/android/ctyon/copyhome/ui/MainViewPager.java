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
        super.setCurrentItem(item, false);
    }

    public MainViewPager(Context context) {
        super(context);
        //this.setPageTransformer(true, new CancelAnimation());
    }

    public MainViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        //this.setPageTransformer(true, new CancelAnimation());
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
}

