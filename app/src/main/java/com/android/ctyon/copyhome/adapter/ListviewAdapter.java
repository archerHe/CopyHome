package com.android.ctyon.copyhome.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.ctyon.copyhome.R;
import com.android.ctyon.copyhome.adapter.data.AppClassName;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.LinkedList;

public class ListviewAdapter extends BaseAdapter {

    private LinkedList<AppClassName> mData;
    private Context mContext;

    public ListviewAdapter(LinkedList<AppClassName> mData, Context mContext){
        this.mContext = mContext;
        this.mData = mData;
    }
    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_item_layout, parent, false);
        TextView tv_number = (TextView) convertView.findViewById(R.id.item_num);
        TextView tv_title = (TextView) convertView.findViewById(R.id.item_title);
        tv_number.setText(String.valueOf(position));
        tv_title.setText(mData.get(position).getName());

        return convertView;
    }
}
