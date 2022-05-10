package com.hansung.android.calendarhomework2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.AttributeSet;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;


public class MonthFragment extends Fragment {

    static int selectedItemPosition = -1;
    static int year = 0;
    static int month = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);

        // id가 listview인 리스트뷰 객체를 얻어옴
        GridView gridView = rootView.findViewById(R.id.calendarGridView);

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

        year = ((MonthActivity)getActivity()).FragmentGetCyear();
        month = ((MonthActivity)getActivity()).FragmentGetCmonth();

        DayAdapter items = new DayAdapter();

        int fullitem = 1;

        int start_day = getDays(year, month) % 7; //사용자정의 메소드 getDays()에서 총 일수를 반환받은 후
        // 7로 나눈 나머지를 이용하여 해당 달의 시작 요일을 구하는 코드

        String cday = "";
        //구한 start_day가 0(일요일)이 아닐 경우 start_day-1만큼 ArrayList에 공백을 추가하는 코드
        if(start_day!=0) {
            for (int i = 0; i < start_day; i++) {
                cday = "";
                items.addItem(cday);
            }
        }
        //2월은 28,29일, 4,6,9,11월은 30일, 그 외에는 31일인것을 이용하여 해당 월의 날짜값들을 ArrayList에 추가하는 코드
        //코드는 getDays()메소드와 같이 1871074김혁순이 과거 Java프로그래밍 과목에서 프로젝트 과제로 제출했던 코드를 일부 변형하여 재사용하였음
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



        // 리스트뷰 객체에 Shakespear.TITLES 배열을 데이터원본으로 설정한 ArrayAdapter 객체를 연결
        gridView.setAdapter(items);  // 데이터 원본
        // 리스트뷰 항목이 선택되었을 때, 항목 클릭 이벤트 처리
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 선택된 항목 위치 (position)을 이 프래그먼트와 연결된 액티비티로 전달
                if(selectedItemPosition != position) {
                    //Resets old item to original color
                    for(int i=0;i<42;i++) {
                        adapterView.getChildAt(i).setBackgroundColor(Color.WHITE);
                    }
                    view.setBackgroundColor(Color.CYAN);
                }
                else if(selectedItemPosition==position){
                    view.setBackgroundColor(Color.WHITE);
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



    // 현재 달의 시작 날짜를 계산하기 위해 총 날짜를 알아내는 함수 정의
    //코드는 1871074김혁순이 과거 Java프로그래밍 과목에서 프로젝트 과제로 제출했던 코드를 재사용하였음
    public static int getDays(int year, int month) { // 총 날짜수를 구하는 메소드
        int total = 0;

        total += (year - 1) * 365;
        total += (int)(year / 4) - (int)(year / 100) + (int)(year / 400); //윤년 계산
        if (year % 400 == 0) {
            if (month < 3)
                total -= 1;
        } else if (year % 4 == 0 && year % 100 != 0) {
            if (month < 3)
                total -= 1;
        }

        switch (month) {  //그 해의 1월 1일부터 그 달의 이전 달의 마지막날까지를 구하는 코드
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

        return total + 1; // 그 달의 1일까지의 총 일수를 반환
    }


}



