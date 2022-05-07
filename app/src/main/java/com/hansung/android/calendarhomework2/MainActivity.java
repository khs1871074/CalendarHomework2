package com.hansung.android.calendarhomework2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Calendar c = Calendar.getInstance();  //Calendar 클래스의 객체 생성

        ViewPager2 vpPager = findViewById(R.id.vpPager);
        FragmentStateAdapter adapter = new PagerAdapter(this);

        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback()
        {
            private int myState;
            private int currentPosition;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {
                if (myState == ViewPager2.SCROLL_STATE_DRAGGING && currentPosition == position && currentPosition == 0)
                    vpPager.setCurrentItem(2);
                else if (myState == ViewPager2.SCROLL_STATE_DRAGGING && currentPosition == position && currentPosition == 2)
                    vpPager.setCurrentItem(0);

                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position)
            {
                currentPosition = position;

                super.onPageSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {
                myState = state;

                super.onPageScrollStateChanged(state);
            }
        });
        vpPager.setCurrentItem(1,true);
        vpPager.setAdapter(adapter);


        getSupportActionBar().setTitle(Integer.toString(c.get(Calendar.YEAR))+"년"
                +Integer.toString(c.get(Calendar.MONTH)+1)+"월");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.month_page:
                Toast.makeText(getApplicationContext(), "월간 페이지", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.week_page:
                Toast.makeText(getApplicationContext(), "주간 페이지", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}