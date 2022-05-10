package com.hansung.android.calendarhomework2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Configuration;
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

        Intent gotintent = getIntent();
        if(gotintent.getIntExtra("nowmonth",0)==0) { //받아온 값이 없다면 디폴트값 0을 반환
            mc.CalendarCmonth();
            //디폴드값 0이 반환되면 현재 월의 정보를 Calendar 클래스로부터 받아옴
        }
        else {
            mc.setCmonth(gotintent.getIntExtra("nowmonth", 0));
            //받아온 값이 있다면 인텐트값을 적용
        }

        if(gotintent.getIntExtra("nowyear",-1)==-1) {
            mc.CalendarCyear();
        }
        else {
            mc.setCyear(gotintent.getIntExtra("nowyear", 0));
        }


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
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
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
                Intent intent = new Intent(getApplicationContext(), MonthActivity.class); //MonthViewActivity인텐트 선언
                intent.putExtra("nowmonth", mc.getCmonth()); //증가한 month 값 인텐트로 전달
                intent.putExtra("nowyear", mc.getCyear()); //해당 year 혹은 증가한 year 값 인텐트로 전달
                startActivity(intent); //새로운 MonthViewActivity 실행
                finish(); //이전 MonthViewActivity 종료

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

