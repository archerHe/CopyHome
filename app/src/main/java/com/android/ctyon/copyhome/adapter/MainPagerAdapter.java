package com.android.ctyon.copyhome.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.ui.MainViewPager;

import java.util.ArrayList;
import java.util.LinkedList;

public class MainPagerAdapter extends PagerAdapter {
    private ArrayList<View> mViewArrayList;
    private View mView;

    public MainPagerAdapter(){

    }

    public MainPagerAdapter(ArrayList<View> viewArrayList){
        super();
        this.mViewArrayList = viewArrayList;
    }

    @Override
    public int getCount() {
        return mViewArrayList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mViewArrayList.get(position));
        return  mViewArrayList.get(position);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(mViewArrayList.get(position));
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
        mView = (View)object;
    }

    public View getCurrentView(){
        return mView;
    }
}
