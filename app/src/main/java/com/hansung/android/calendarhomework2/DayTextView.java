package com.hansung.android.calendarhomework2;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class DayTextView extends LinearLayout {

    TextView textView;
    public DayTextView(Context context) {
        super(context);

        init(context);
    }

    public void init(Context context){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.gridview_list_item,this,true);

        textView = (TextView)findViewById(R.id.setday);
    }

    public void setItem(String d){
        textView.setText(d);
    }
}