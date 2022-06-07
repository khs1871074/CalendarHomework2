package com.hansung.android.calendarhomework2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class ScheduleActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final int REQUEST_PERMISSIONS_FOR_LOCATION_UPDATES = 1;
    private static final int REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION = 2;
    private FusedLocationProviderClient mFusedLocationClient;

    EditText title;
    EditText map_search;
    EditText memo;
    TimePicker tps;
    TimePicker tpe;
    private static int year;
    private static int month;
    private static int day;
    private static int saved_location;

    private DBHelper mDbHelper;
    String DBid;
    GoogleMap mgoogleMap = null;

    LatLng hansung = new LatLng(37.5817891, 127.008175);
    LatLng myeongil = new LatLng(37.55139065560204, 127.14399075648122);
    LatLng hansung_station = new LatLng(37.58856951803093, 127.00586408259376);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        Intent gotintent = getIntent();

        year = gotintent.getIntExtra("nowyear",0);
        month = gotintent.getIntExtra("nowmonth",0);
        day = gotintent.getIntExtra("nowday",0);

        getLastLocation();

        title = (EditText)findViewById(R.id.schedule_title);
        map_search = (EditText)findViewById(R.id.map_search);
        memo = (EditText)findViewById(R.id.memo);

        mDbHelper = new DBHelper(this);

        tps = (TimePicker)findViewById(R.id.start_time);
        tpe = (TimePicker)findViewById(R.id.end_time);

        search_db();

        Button save_button = (Button)findViewById(R.id.save);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int start_hour = tps.getHour();
                int start_minute = tps.getMinute();
                int end_hour = tpe.getHour();
                int end_minute = tpe.getMinute();
                insertRecord(start_hour, start_minute, end_hour, end_minute);
                finish();
            }
        });

        Button delete_button = (Button)findViewById(R.id.delete);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder dlg = new AlertDialog.Builder(ScheduleActivity.this);
                dlg.setTitle("일정 삭제"); //제목
                dlg.setMessage("정말 삭제하시겠습니까?"); // 메시지
//                버튼 클릭시 동작
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        deleteRecord(DBid);
                        finish();
                    }
                });
                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                dlg.show();
            }
        });

        Button cancel_button = (Button)findViewById(R.id.cancel);
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button location_button = (Button)findViewById(R.id.location_button);
        location_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String map = map_search.getText().toString();

                if (map.equals("한성대학교")){
                    mgoogleMap.addMarker(new MarkerOptions().position(hansung).title("한성대학교"));
                    // move the camera
                    mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hansung,15));
                }
                else if(map.equals("한성대입구역")){
                    mgoogleMap.addMarker(new MarkerOptions().position(hansung_station).title("한성대입구역"));
                    // move the camera
                    mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hansung_station,15));
                }
                else if(map.equals("명일역")){
                    mgoogleMap.addMarker(new MarkerOptions().position(myeongil).title("명일역"));
                    // move the camera
                    mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myeongil,15));
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "장소를 찾을 수 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    Location mLocation;

    private void getLastLocation() {
        // 1. 위치 접근에 필요한 권한 검사 및 요청
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    ScheduleActivity.this,            // MainActivity 액티비티의 객체 인스턴스를 나타냄
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},        // 요청할 권한 목록을 설정한 String 배열
                    REQUEST_PERMISSIONS_FOR_LAST_KNOWN_LOCATION    // 사용자 정의 int 상수. 권한 요청 결과를 받을 때
            );
            return;
        }

        // 2. Task<Location> 객체 반환
        Task task = mFusedLocationClient.getLastLocation();

        // 3. Task가 성공적으로 완료 후 호출되는 OnSuccessListener 등록
        task.addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                mLocation = location;
                SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
                mapFragment.getMapAsync(ScheduleActivity.this);


            }
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        mgoogleMap = googleMap;

        if (saved_location==0){
            mgoogleMap.addMarker(new MarkerOptions().position(hansung).title("한성대학교"));
            // move the camera
            mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hansung,15));
        }
        else if(saved_location==1){
            mgoogleMap.addMarker(new MarkerOptions().position(hansung_station).title("한성대입구역"));
            // move the camera
            mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hansung_station,15));
        }
        else if(saved_location==2){
            mgoogleMap.addMarker(new MarkerOptions().position(myeongil).title("명일역"));
            // move the camera
            mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myeongil,15));
        }
        else{
            mgoogleMap.addMarker(new MarkerOptions().position(hansung).title("한성대학교"));
            // move the camera
            mgoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(hansung,15));
        }
        saved_location=3;

    }



    private void deleteRecord(String t) {
        mDbHelper.deleteUserBySQL(t);
        DBid = "";
    }


    private void insertRecord(int sth, int stm, int eth, int etm) {
        EditText t = (EditText)findViewById(R.id.schedule_title);
        EditText l = (EditText)findViewById(R.id.map_search);
        EditText m = (EditText)findViewById(R.id.memo);


        mDbHelper.insertUserBySQL(t.getText().toString(),
                Integer.toString(year),Integer.toString(month),Integer.toString(day),
                Integer.toString(sth),Integer.toString(stm),
                Integer.toString(eth),Integer.toString(etm),
                l.getText().toString(),m.getText().toString());
    }

    public void search_db() {
        Cursor cursor = mDbHelper.getAllUsersBySQL();

        while (cursor.moveToNext()){
            if(year == Integer.parseInt(cursor.getString(2)) &&
                    month == Integer.parseInt(cursor.getString(3)) &&
                    day == Integer.parseInt(cursor.getString(4))){
                title.setText(cursor.getString(1));
                tps.setHour(Integer.parseInt(cursor.getString(5)));
                tps.setMinute(Integer.parseInt(cursor.getString(6)));
                tpe.setHour(Integer.parseInt(cursor.getString(7)));
                tpe.setMinute(Integer.parseInt(cursor.getString(8)));
                map_search.setText(cursor.getString(9));
                memo.setText(cursor.getString(10));
                DBid = Integer.toString(cursor.getInt(0));

                start_location(map_search.getText().toString());

                break;
            }
        }
    }

    public void start_location(String location){
        if (location.equals("한성대학교")){
            saved_location=0;
        }
        else if(location.equals("한성대입구역")){
            saved_location=1;
        }
        else if(location.equals("명일역")){
            saved_location=2;
        }
        else{ saved_location=3; }
    }
}
