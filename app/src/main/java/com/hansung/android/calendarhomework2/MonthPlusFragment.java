package com.hansung.android.calendarhomework2;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

// 내용은 MonthFragment와 같으나 월 값이 증가하는 Fragment
public class MonthPlusFragment extends Fragment {

    static int selectedItemPosition = -1;
    static int year=0;
    static int month=0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Display display = ((MonthActivity)getActivity()).getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y/7;

        class DayAdapter extends BaseAdapter {
            ArrayList<String> items = new ArrayList<String>();

            @Override
            public int getCount() {
                return items.size();
            }

            public void addItem(String d){
                items.add(d);
            }

            @Override
            public String getItem(int i) {
                return items.get(i);
            }

            @Override
            public long getItemId(int i) {
                return i;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                TextView dayview = new TextView(getActivity().getApplicationContext());
                dayview.setHeight(height);
                dayview.setText(items.get(i));
                dayview.setGravity(Gravity.CENTER_HORIZONTAL);
                dayview.setBackgroundColor(Color.WHITE);
                return dayview;
            }
        }



        DayAdapter items = new DayAdapter();

        year = ((MonthActivity)getActivity()).FragmentGetCyear();
        month = ((MonthActivity)getActivity()).FragmentGetCmonth() + 1; //월 값 증가

        if (month == 13) { // 12월에서 다음 페이지로 넘어가면 1월로 초기화 한 후 년 값을 증가
            ((MonthActivity) getActivity()).FragmentsetCmonth(month);
            year =+ 1;
            month = 1;
        }
        else {
            ((MonthActivity) getActivity()).FragmentsetCmonth(month);
        }

        int fullitem = 1;

        int start_day = getDays(year, month) % 7;

        String cday = "";
        if(start_day!=0) {
            for (int i = 0; i < start_day; i++) {
                cday = "";
                items.addItem(cday);
            }
        }
        if (month == 2) {
            if (year % 400 == 0) {
                for (int i = 1; i <= 29; i++) {
                    fullitem++;
                    cday = Integer.toString(i);
                    items.addItem(cday);
                }
                for (int i = start_day+fullitem; i <= 42; i++){
                    cday = "";
                    items.addItem(cday);
                }
                fullitem = 0;
            } else if (year % 4 == 0 && year % 100 != 0) {
                for (int i = 1; i <= 29; i++) {
                    fullitem++;
                    cday = Integer.toString(i);
                    items.addItem(cday);
                }
                for (int i = start_day+fullitem; i <= 42; i++){
                    cday = "";
                    items.addItem(cday);
                }
                fullitem = 0;
            } else {
                for (int i = 1; i <= 28; i++) {
                    fullitem++;
                    cday = Integer.toString(i);
                    items.addItem(cday);
                }
                for (int i = start_day+fullitem; i <= 42; i++){
                    cday = "";
                    items.addItem(cday);
                }
                fullitem = 0;
            }
        }
        else if (month == 4 || month == 6 || month == 9 || month == 11) {
            for (int i = 1; i <= 30; i++) {
                fullitem++;
                cday = Integer.toString(i);
                items.addItem(cday);
            }
            for (int i = start_day+fullitem; i <= 42; i++){
                cday = "";
                items.addItem(cday);
            }
            fullitem = 0;
        } else {
            for (int i = 1; i <= 31; i++) {
                fullitem++;
                cday = Integer.toString(i);
                items.addItem(cday);
            }
            for (int i = start_day+fullitem; i <= 42; i++){
                cday = "";
                items.addItem(cday);
            }
            fullitem = 0;
        }





        View rootView = inflater.inflate(R.layout.fragment_month, container, false);

        GridView gridView = rootView.findViewById(R.id.calendarGridView);

        gridView.setAdapter(items);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if(selectedItemPosition != position) {
                    for(int i=0;i<42;i++) {
                        adapterView.getChildAt(i).setBackgroundColor(Color.WHITE);
                    }
                    view.setBackgroundColor(Color.CYAN);
                    ((MonthActivity)getActivity()).FragmentsetItem(position-start_day+1);
                }
                else if(selectedItemPosition==position){
                    view.setBackgroundColor(Color.WHITE);
                    ((MonthActivity)getActivity()).FragmentsetItem(0);
                }
                Toast.makeText(((MonthActivity)getActivity()).getApplicationContext(),
                        Integer.toString(year)+"."+
                                Integer.toString(month)+"."+
                                Integer.toString(position-start_day+1), Toast.LENGTH_SHORT).show();
                selectedItemPosition = position;
            }
        });
        return rootView;
    }



    public static int getDays(int year, int month) {
        int total = 0;

        total += (year - 1) * 365;
        total += (int)(year / 4) - (int)(year / 100) + (int)(year / 400);
        if (year % 400 == 0) {
            if (month < 3)
                total -= 1;
        } else if (year % 4 == 0 && year % 100 != 0) {
            if (month < 3)
                total -= 1;
        }

        switch (month) {
            case 1:
                break;
            case 2:
                total += 31;
                break;
            case 3:
                total += 31 + 28;
                break;
            case 4:
                total += 31 + 28 + 31;
                break;
            case 5:
                total += 31 + 28 + 31 + 30;
                break;
            case 6:
                total += 31 + 28 + 31 + 30 + 31;
                break;
            case 7:
                total += 31 + 28 + 31 + 30 + 31 + 30;
                break;
            case 8:
                total += 31 + 28 + 31 + 30 + 31 + 30 + 31;
                break;
            case 9:
                total += 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31;
                break;
            case 10:
                total += 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30;
                break;
            case 11:
                total += 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31;
                break;
            case 12:
                total += 31 + 28 + 31 + 30 + 31 + 30 + 31 + 31 + 30 + 31 + 30;
                break;
        }

        return total + 1;
    }


}