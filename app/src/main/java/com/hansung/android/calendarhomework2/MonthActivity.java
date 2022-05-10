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
            mc.CalendarCyear(); //디폴드값 -1이 반환되면 현재 년의 정보를 Calendar 클래스로부터 받아옴
        }
        else {
            mc.setCyear(gotintent.getIntExtra("nowyear", 0));
            //받아온 값이 있다면 인텐트값을 적용
        }


        ViewPager2 vpPager = findViewById(R.id.vpPager);
        FragmentStateAdapter adapter = new PagerAdapter(this);

        // 시작 시 앱바의 타이틀 설정
        getSupportActionBar().setTitle(Integer.toString(mc.getCyear()) + "년"
                + Integer.toString(mc.getCmonth()) + "월");

        vpPager.setAdapter(adapter); //ViewPager2에 어댑터 설정
        vpPager.setCurrentItem(start_position,false); //시작 페이지 설정

        // 페이지가 바뀌면 해당 날짜의 년, 월로 앱바의 타이틀을 변경
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

    // 가로 회전시 액티비티 재실행을 방지하는 코드
    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    // 메뉴 생성
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // 메뉴 선택시 이벤트 발생
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.month_page:
                Toast.makeText(getApplicationContext(), "월간 페이지", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MonthActivity.class); //MonthActivity인텐트 선언
                intent.putExtra("nowmonth", mc.getCmonth()); //month 값 인텐트로 전달
                intent.putExtra("nowyear", mc.getCyear()); //year 값 인텐트로 전달
                startActivity(intent); //새로운 MonthActivity 실행
                finish(); //이전 Activity 종료

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
    } //프래그먼트에서 년 값을 받아가는 메소드

    public int FragmentGetCmonth(){
        return mc.getCmonth();
    } //프래그먼트에서 월 값을 받아가는 메소드

    public void FragmentsetCyear(int setyear){
        mc.setCyear(setyear);
    } //프래그먼트에서 년 값을 설정하는 메소드

    public void FragmentsetCmonth(int setmonth){
        mc.setCmonth(setmonth);
    } //프래그먼트에서 월 값을 설정하는 메소드

    // 액티비티와 프래그먼트에서 같은 년, 월 값을 공유하기 위해 생성한 객체
    public class MyCalendar {
        int cyear = 0;
        int cmonth = 0;

        public int CalendarCyear(){ // 년 값을 현재 날짜의 년으로 설정
            Calendar cy = Calendar.getInstance();
            cyear = cy.get(Calendar.YEAR);
            return cyear;
        }

        public int CalendarCmonth(){ // 월 값을 현재 날짜의 월로 설정
            Calendar cm = Calendar.getInstance();
            cmonth = cm.get(Calendar.MONTH) + 1;
            return cmonth;
        }

        public int getCyear(){
            return cyear;
        } // 저장되어있는 년값 반환

        public int getCmonth(){
            return cmonth;
        } // 저장되어있는 월값 반환

        public void setCyear(int setyear){
            cyear = setyear;
        } // 년값 설정

        public void setCmonth(int setmonth){
            cmonth = setmonth;
        } // 월값 설정


    }

}

