package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lego.CaApplication;
import com.example.lego.CaEngine;
import com.example.lego.CaPref;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.example.lego.CaApplication.m_Context;

public class ActivityReserveTime extends AppCompatActivity implements IaResultHandler{

    private EditText tvDateStart,tvDateEnd,tvTimeStart,tvTimeEnd;

    int year, month, day, hour, minute;

    private DatePickerDialog.OnDateSetListener callbackMethod1;
    private DatePickerDialog.OnDateSetListener callbackMethod2;
    private TimePickerDialog.OnTimeSetListener callbackMethod3;
    private TimePickerDialog.OnTimeSetListener callbackMethod4;

    CaPref m_Pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reserve_time);

        m_Context = getApplicationContext();
        m_Pref = new CaPref(m_Context);

        tvDateStart = findViewById(R.id.tv_date_picker1);
        tvDateEnd = findViewById(R.id.tv_date_picker2);
        tvTimeStart = findViewById(R.id.tv_time_picker1);
        tvTimeEnd = findViewById(R.id.tv_time_picker2);

        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        day = calendar.get(Calendar.DATE);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        minute = calendar.get(Calendar.MINUTE);


        if(month>9 && day>=10){
            tvDateStart.setText(month + "/" + day);
            tvDateEnd.setText(month + "/" + day);
        }
        else if(month<=9 && day>=10){
            tvDateStart.setText("0"+month + "/" + day);
            tvDateEnd.setText("0"+month + "/" + day);
        }
        else if(month>9 && day<10){
            tvDateStart.setText(month+ "/0" + day);
            tvDateEnd.setText(month+ "/0" + day);
        }
        else{
            tvDateStart.setText("0"+month + "/0" + day);
            tvDateEnd.setText("0"+month + "/0" + day);
        }

        if(hour>=10 & minute >=10){
            tvTimeStart.setText(hour + ":" + minute);
            tvTimeEnd.setText(hour + ":" + minute);
        }
        else if(hour >=10 && minute <10){
            tvTimeStart.setText(hour + ":0" + minute);
            tvTimeEnd.setText(hour + ":0" + minute);
        }
        else if(hour<10 && minute>=10){
            tvTimeStart.setText("0"+hour + ":" + minute);
            tvTimeEnd.setText("0"+hour + ":" + minute);
        }
        else {
            tvTimeStart.setText("0"+hour + ":0" + minute);
            tvTimeEnd.setText("0"+hour + ":0" + minute);
        }


    }

    public void refresh(){
        try {
            //TODO 액티비티 화면 재갱신 시키는 코드
            Intent intent = getIntent();
            finish(); //현재 액티비티 종료 실시
            overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
            startActivity(intent); //현재 액티비티 재실행 실시
            overridePendingTransition(0, 0); //인텐트 애니메이션 없애기
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();

            }
            break;
            case R.id.tv_date_picker1: {
                DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod1, year, month, day);
                dialog.show();

                callbackMethod1 = new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        monthOfYear = monthOfYear +1;
                        if(monthOfYear>9 && dayOfMonth>=10){
                            tvDateStart.setText(monthOfYear + "/" + dayOfMonth);
                        }
                        else if(monthOfYear<=9 && dayOfMonth>=10){
                            tvDateStart.setText("0"+monthOfYear + "/" + dayOfMonth);
                        }
                        else if(monthOfYear>9 && dayOfMonth<10){
                            tvDateStart.setText(monthOfYear+ "/0" + dayOfMonth);
                        }
                        else{
                            tvDateStart.setText("0"+monthOfYear + "/0" + dayOfMonth);
                        }

                    }
                };

            }
            break;

            case R.id.tv_date_picker2: {
                DatePickerDialog dialog = new DatePickerDialog(this, callbackMethod2, year, month, day);
                dialog.show();

                callbackMethod2 = new DatePickerDialog.OnDateSetListener()
                {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
                    {
                        monthOfYear = monthOfYear +1;
                        if(monthOfYear>9 && dayOfMonth>=10){
                            tvDateEnd.setText(monthOfYear + "/" + dayOfMonth);
                        }
                        else if(monthOfYear<=9 && dayOfMonth>=10){
                            tvDateEnd.setText("0"+monthOfYear + "/" + dayOfMonth);
                        }
                        else if(monthOfYear>9 && dayOfMonth<10){
                            tvDateEnd.setText(monthOfYear + "/0" + dayOfMonth);
                        }
                        else{
                            tvDateEnd.setText("0"+monthOfYear + "/0" + dayOfMonth);
                        }
                    }
                };

            }
            break;

            case R.id.tv_time_picker1: {
                TimePickerDialog dialog = new TimePickerDialog(this, callbackMethod3, hour, minute, false);

                dialog.show();
                callbackMethod3 = new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        if(hourOfDay>=10 & minute >=10){
                            tvTimeStart.setText(hourOfDay + ":" + minute);
                        }
                        else if(hourOfDay >=10 && minute <10){
                            tvTimeStart.setText(hourOfDay + ":0" + minute);
                        }
                        else if(hourOfDay<10 && minute>=10){
                            tvTimeStart.setText("0"+hourOfDay + ":" + minute);
                        }
                        else {
                            tvTimeStart.setText("0"+hourOfDay + ":0" + minute);
                        }

                    }
                };

            }
            break;

            case R.id.tv_time_picker2: {

                TimePickerDialog dialog = new TimePickerDialog(this, callbackMethod4,  hour, minute, false);

                dialog.show();
                callbackMethod4 = new TimePickerDialog.OnTimeSetListener()
                {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
                    {
                        if(hourOfDay>=10 & minute >=10){
                            tvTimeEnd.setText(hourOfDay + ":" + minute);
                        }
                        else if(hourOfDay >=10 && minute <10){
                            tvTimeEnd.setText(hourOfDay + ":0" + minute);
                        }
                        else if(hourOfDay<10 && minute>=10){
                            tvTimeEnd.setText("0"+hourOfDay + ":" + minute);
                        }
                        else {
                            tvTimeEnd.setText("0"+hourOfDay + ":0" + minute);
                        }
                    }
                };

            }
            break;

            case R.id.btn_next: {

                String from = "2021-"+tvDateStart.getText().toString().substring(0,2)+"-"
                        +tvDateStart.getText().toString().substring(3,5) + "-"+ tvTimeStart.getText().toString().substring(0,2)+
                        "-" + tvTimeStart.getText().toString().substring(3,5)+"-00";
                Log.d("ReserveTime", "from is: " +from);
                SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                    CaApplication.m_Info.dtStart = CaApplication.m_Info.parseDate(from);


                String to = "2021-"+tvDateEnd.getText().toString().substring(0,2)+"-"
                        +tvDateEnd.getText().toString().substring(3,5) + "-"+ tvTimeEnd.getText().toString().substring(0,2)+
                        "-" + tvTimeEnd.getText().toString().substring(3,5)+"-00";
                Log.d("ReserveTime", "to is: " +to);

                CaApplication.m_Info.dtEnd = CaApplication.m_Info.parseDate(to);

                CaApplication.m_Engine.SetReserveInfo(CaApplication.m_Info.strId, CaApplication.m_Info.nStationId, CaApplication.m_Info.nReserveType, from, to
                , CaApplication.m_Info.nMinCapacity, 45, this, this);
                m_Pref.setValue(CaPref.PREF_CURRENT_CAP, 45);


            }
            break;


            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    @Override
    public void onBackPressed() {
        finish();

    }

    @Override
    public void onResult(CaResult Result) {
        if (Result.object == null) {
            Toast.makeText(m_Context, "Check Network", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (Result.m_nCallback) {
            case CaEngine.SET_RESERVE_INFO: {

                try {
                    JSONObject jo = Result.object;
/*
                    if(jo.getInt("result_code")==0){
                        Toast.makeText(this, "서비스 예약에 실패하였습니다.", Toast.LENGTH_LONG).show();
                    }*/


                    CaApplication.m_Info.nServiceReservation = jo.getString("service_reservation_id");
                    CaApplication.m_Info.nExpectedFee = jo.getInt("expected_fee");

                    Intent it = new Intent(this, ActivityReserveEnd.class);
                    startActivity(it);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            default: {
                //Log.i(TAG, "Unknown type result received");
            }
            break;

        }
    }
}