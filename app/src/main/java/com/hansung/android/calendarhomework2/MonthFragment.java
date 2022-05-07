package com.hansung.android.calendarhomework2;

import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;

import java.util.ArrayList;


public class MonthFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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
                DayTextView dayview = new DayTextView(getActivity().getApplicationContext());
                dayview.setItem(items.get(i));
                return dayview;
            }
        }

        DayAdapter dayAdapter = new DayAdapter();

        for(int i=1; i<=42; i++){
            dayAdapter.addItem(Integer.toString(i));
        }
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_month, container, false);

        // id가 listview인 리스트뷰 객체를 얻어옴
        GridView gridView = rootView.findViewById(R.id.calendarGridView);
        // 리스트뷰 객체에 Shakespear.TITLES 배열을 데이터원본으로 설정한 ArrayAdapter 객체를 연결

        gridView.setAdapter(dayAdapter);  // 데이터 원본
        // 리스트뷰 항목이 선택되었을 때, 항목 클릭 이벤트 처리
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                // 선택된 항목 위치 (position)을 이 프래그먼트와 연결된 액티비티로 전달
            }
        });
        return rootView;
    }

}