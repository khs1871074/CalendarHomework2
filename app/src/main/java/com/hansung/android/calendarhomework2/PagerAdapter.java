package com.hansung.android.calendarhomework2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=Integer.MAX_VALUE;
    public static int before_position = Integer.MAX_VALUE / 2;
    public static int before_position2 = 0;

    public PagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) {

        if (position==before_position && before_position2==0){
            MonthFragment month = new MonthFragment();
            before_position = position;
            before_position2 = before_position;
            return month;
        }
        else if(position==before_position && position<before_position2){
            MonthPlusFragment plusmonth = new MonthPlusFragment();
            before_position = position;
            before_position2 = before_position - 1;
            return plusmonth;
        }
        else if(position==before_position && position>before_position2){
            MonthMinusFragment minusmonth = new MonthMinusFragment();
            before_position = position;
            before_position2 = before_position + 1;
            return minusmonth;
        }
        else if(position>before_position) {
            MonthPlusFragment plusmonth = new MonthPlusFragment();
            before_position = position;
            before_position2 = before_position - 1;
            return plusmonth;
        }
        else if (position<before_position) {
            MonthMinusFragment minusmonth = new MonthMinusFragment();
            before_position = position;
            before_position2 = before_position + 1;
            return minusmonth;
        }
        else {
            if (position < before_position2) {
                MonthPlusFragment plusmonth = new MonthPlusFragment();
                before_position = position;
                before_position2 = before_position - 1;
                return plusmonth;
            }
            else if (position > before_position2) {
                MonthMinusFragment minusmonth = new MonthMinusFragment();
                before_position = position;
                before_position2 = before_position + 1;
                return minusmonth;
            }
            else {
                return null;
            }
        }

    }

    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}