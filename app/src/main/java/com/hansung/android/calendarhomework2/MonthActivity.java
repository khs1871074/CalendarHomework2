package com.hansung.android.calendarhomework2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.Calendar;

public class MonthActivity extends AppCompatActivity {

    MyCalendar mc = new MyCalendar();
    public static int start_position = Integer.MAX_VALUE / 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mc.CalendarCyear();
        mc.CalendarCmonth();

        ViewPager2 vpPager = findViewById(R.id.vpPager);
        FragmentStateAdapter adapter = new PagerAdapter(this);

        getSupportActionBar().setTitle(Integer.toString(mc.getCyear()) + "년"
                + Integer.toString(mc.getCmonth()) + "월");

        vpPager.setAdapter(adapter);
        vpPager.setCurrentItem(start_position,false);

        vpPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                if (mc.getCmonth()==13){
                    mc.setCyear(mc.getCyear()+1);
                    mc.setCmonth(1);
                }
                else if (mc.getCmonth()==0){
                    mc.setCyear(mc.getCyear()-1);
                    mc.setCmonth(12);
                }
                getSupportActionBar().setTitle(Integer.toString(mc.getCyear()) + "년"
                        + Integer.toString(mc.getCmonth()) + "월");
            }
        });
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

    public int FragmentGetCyear(){
        return mc.getCyear();
    }

    public int FragmentGetCmonth(){
        return mc.getCmonth();
    }

    public void FragmentsetCyear(int setyear){
        mc.setCyear(setyear);
    }

    public void FragmentsetCmonth(int setmonth){
        mc.setCmonth(setmonth);
    }

    public class MyCalendar {
        int cyear = 0;
        int cmonth = 0;

        public int CalendarCyear(){
            Calendar cy = Calendar.getInstance();
            cyear = cy.get(Calendar.YEAR);
            return cyear;
        }

        public int CalendarCmonth(){
            Calendar cm = Calendar.getInstance();
            cmonth = cm.get(Calendar.MONTH) + 1;
            return cmonth;
        }

        public int getCyear(){
            return cyear;
        }

        public int getCmonth(){
            return cmonth;
        }

        public void setCyear(int setyear){
            cyear = setyear;
        }

        public void setCmonth(int setmonth){
            cmonth = setmonth;
        }


    }

}