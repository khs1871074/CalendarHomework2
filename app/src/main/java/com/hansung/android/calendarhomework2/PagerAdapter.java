package com.hansung.android.calendarhomework2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter extends FragmentStateAdapter {
    private static int NUM_ITEMS=3;

    public PagerAdapter(FragmentActivity fa) {
        super(fa);
    }

    // 각 페이지를 나타내는 프래그먼트 반환
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                MonthFragment month1 = new MonthFragment();
                return month1;
            case 1:
                MonthFragment month2 = new MonthFragment();
                return month2;
            case 2:
                MonthFragment month3 = new MonthFragment();
                return month3;
            default:
                return null;
        }
    }

    // 전체 페이지 개수 반환
    @Override
    public int getItemCount() {
        return NUM_ITEMS;
    }
}