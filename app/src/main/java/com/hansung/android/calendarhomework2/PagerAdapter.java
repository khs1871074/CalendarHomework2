package com.hansung.android.calendarhomework2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=Integer.MAX_VALUE; //무한 페이지를 나타내기 위해 최댓값 적용
    public static int before_position = Integer.MAX_VALUE / 2;

    public PagerAdapter(FragmentActivity fa) {
        super(fa);
    }
    // 달력을 표현하는 프래그먼트에 슬라이드전환 적용

    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) {
        // 시작 페이지일 경우
        if (position==Integer.MAX_VALUE / 2){
            MonthFragment month = new MonthFragment();
            return month;
        }
        // 다음 페이지로 넘겼을 경우 다음 달로 증가
        else if(position>before_position) {
            MonthPlusFragment plusmonth = new MonthPlusFragment();
            before_position = position;
            return plusmonth;
        }
        // 이전 페이지로 넘겼을 경우 지난 달로 감소
        else if (position<before_position) {
            MonthMinusFragment minusmonth = new MonthMinusFragment();
            before_position = position;
            return minusmonth;
        }
        else {
            return null;
        }
    }

    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}