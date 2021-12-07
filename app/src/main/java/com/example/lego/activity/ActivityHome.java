package com.example.lego.activity;

import com.example.lego.CaEngine;
import com.example.lego.CaApplication;
import com.example.lego.CaPref;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;
import com.example.lego.model.CaHistory;
import com.example.lego.model.CaStation;
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.lego.CaApplication.m_Context;

public class ActivityHome extends BaseActivity implements IaResultHandler {

    CaPref m_Pref;
    TextView tvName, tvStation, tvCar, tvCurrentCap2, tvMargin, tvCurrentCap, tvEfficiency, tvEfficiency2, tvNoCar;
    TextView tvToday, tvGuide, tvChargeTime, tvChargeTime2;
    ImageView ivCar;
    Button btnChargeTime, btnCar, btnStation;

    long now;
    Date mNow;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    ArrayList<CaHistory> alHistory = new ArrayList<>();

    int nCurrentCap=62;

    SimpleDateFormat mYearMonth = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat mMonthDay = new SimpleDateFormat("MM월 dd일");
    SimpleDateFormat mAm= new SimpleDateFormat("E요일 a hh시 mm분");
    SimpleDateFormat mMonthDayHourMin = new SimpleDateFormat("MM월 dd일 HH시 mm분");

    String strYearMonth;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        prepareDrawer();


        m_Context = getApplicationContext();
        m_Pref = new CaPref(m_Context);
        now = System.currentTimeMillis();
        mNow = new Date(now);

        nCurrentCap = CaApplication.m_Info.nCurrentCap; //nCurrentCap = 45





        //윗부분
        tvName = findViewById(R.id.tv_name);
        tvStation = findViewById(R.id.tv_station);
        tvCar = findViewById(R.id.tv_car_model);
        tvCurrentCap = findViewById(R.id.tv_current_capacity);
        tvCurrentCap2= findViewById(R.id.tv_current_capacity2);
        tvEfficiency2 = findViewById(R.id.tv_efficiency2);
        tvEfficiency = findViewById(R.id.tv_efficiency);
        ivCar = findViewById(R.id.iv_car);
        tvNoCar = findViewById(R.id.tv_no_car);
        btnCar = findViewById(R.id.btn_car);
        tvName.setText("안녕하세요,\n" + CaApplication.m_Info.strName +" 님");
        tvCar.setText(CaApplication.m_Info.strCarModel);

        //아랫부분
        btnChargeTime =findViewById(R.id.btn_charge_time);
        btnStation = findViewById(R.id.btn_station);
        tvToday = findViewById(R.id.tv_guide2);
        tvGuide = findViewById(R.id.tv_guide);
        tvChargeTime = findViewById(R.id.tv_charge_time);
        tvChargeTime2 = findViewById(R.id.tv_charge_time2);


        if(CaApplication.m_Info.dEfficiency !=0.1){
            tvEfficiency.setText(String.format("%.0f", CaApplication.m_Info.dBatteryCapacity
                    * (double)nCurrentCap
                    / 100 * CaApplication.m_Info.dEfficiency)+ " km");
        }
        else{
            tvEfficiency.setVisibility(View.INVISIBLE);
        }

        viewSetting();

    }

    @Override
    protected  void onResume() {

        super.onResume();
        CaApplication.m_Engine.GetHomeInfo(CaApplication.m_Info.strId, this,this);
        CaApplication.m_Engine.GetScheduleInfo(CaApplication.m_Info.strId, this,this);

    }

    public void viewSetting(){

        now = System.currentTimeMillis();
        mNow = new Date(now);

        Log.i("HOME", "bPaid is " + CaApplication.m_Info.bPaid);
        //tvMargin

        if(CaApplication.m_Info.strCarModel.equals("")){ //차가 없을때
            Log.i("Home", "이용 중인 서비스가 없습니다");

            tvEfficiency.setVisibility(View.INVISIBLE);
            tvEfficiency2.setVisibility(View.INVISIBLE);
            tvCar.setVisibility(View.INVISIBLE);
            tvCurrentCap2.setVisibility(View.INVISIBLE);
            tvCurrentCap.setVisibility(View.INVISIBLE);
            ivCar.setVisibility(View.INVISIBLE);

            tvNoCar.setVisibility(View.VISIBLE);
            btnCar.setVisibility(View.VISIBLE);

        }
        else {
            tvEfficiency.setVisibility(View.VISIBLE);
            tvEfficiency2.setVisibility(View.VISIBLE);
            tvCar.setVisibility(View.VISIBLE);
            tvCurrentCap2.setVisibility(View.VISIBLE);
            tvCurrentCap.setVisibility(View.VISIBLE);
            ivCar.setVisibility(View.VISIBLE);

            tvNoCar.setVisibility(View.INVISIBLE);
            btnCar.setVisibility(View.INVISIBLE);

            tvCurrentCap.setText(Integer.toString(nCurrentCap)+ "%");
        }

        if(CaApplication.m_Info.ChargeTime.equals("")){ //스케주링 정보가 없다면
            btnChargeTime.setVisibility(View.VISIBLE);
            btnStation.setVisibility(View.INVISIBLE);
            tvToday.setVisibility(View.INVISIBLE);
            tvGuide.setVisibility(View.INVISIBLE);
            tvChargeTime.setVisibility(View.INVISIBLE);
            tvChargeTime2.setVisibility(View.INVISIBLE);

            tvGuide.setText("아직 충전 정보가 입력되지 않았어요!");

        }
        else{
            btnChargeTime.setVisibility(View.INVISIBLE);
            btnStation.setVisibility(View.VISIBLE);
            tvToday.setVisibility(View.VISIBLE);
            tvGuide.setVisibility(View.VISIBLE);
            tvChargeTime.setVisibility(View.VISIBLE);
            tvChargeTime2.setVisibility(View.VISIBLE);

            Calendar calToday = Calendar.getInstance();
            tvToday.setText(mMonthDay.format(calToday.getTime()) + "\n오늘의 충전 스케줄");
            tvChargeTime.setText(mAm.format(CaApplication.m_Info.ChargeTime) + "입니다");
            tvChargeTime2.setText(mMonthDayHourMin.format(CaApplication.m_Info.MinBatteryTime)+"의\n" +
                    "예상 배터리 잔량은 "+ CaApplication.m_Info.PreferBattery +"% 입니다");
            if(mMonthDay.format(calToday.getTime()).equals(mMonthDay.format(CaApplication.m_Info.ChargeTime))){
                tvGuide.setText("오늘은 내 차 충전하는 날!");
            }
        }


    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityHome.this);
        dlg.setTitle("경고"); //제목
        dlg.setMessage("앱을 종료하시겠습니까?"); // 메시지
        //dlg.setIcon(R.drawable.deum); // 아이콘 설정
//                버튼 클릭시 동작

        dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // kill app
                finishAffinity();
                System.runFinalization();
                System.exit(0);
            }
        });

        dlg.show();

    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_menu: {
                m_Drawer.openDrawer();
            }
            break;

            case R.id.btn_car: {
                Intent it = new Intent(this, ActivitySignUpCar.class);
                startActivity(it);
            }
            break;

            case R.id.btn_charge_time: {
                Intent it = new Intent(this, ActivityChargeTime.class);
                startActivity(it);
            }
            break;

            case R.id.iv_more: {

            }
            break;

            case R.id.btn_station: {

            }
            break;

        }
    }


    @Override
    public void onResult(CaResult Result) {
        if (Result.object == null) {
            Toast.makeText(m_Context, "Check Network", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (Result.m_nCallback) {
            case CaEngine.GET_SCHEDULE_INFO: {

                try {
                    Log.i("LOGIN", "GetSchedule Called...");
                    JSONObject jo = Result.object;

                    if(!jo.getString("charge_time").equals("정보없음")){
                        CaApplication.m_Info.PreferBattery = jo.getInt("prefer_battery");
                        CaApplication.m_Info.MinBatteryTime= CaApplication.m_Info.parseDate(jo.getString("min_battery_time"));
                        CaApplication.m_Info.ChargeTime = CaApplication.m_Info.parseDate(jo.getString("charge_time"));
                        //history.dtReserve = CaApplication.m_Info.parseDate(joHistory.getString("reserve_time")); date로 변환 참조
                    }

                    CaApplication.m_Engine.GetScheduleInfo(CaApplication.m_Info.strId,this,this);
                    Intent it = new Intent(this, ActivityHome.class);
                    startActivity(it);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            case CaEngine.GET_HOME_INFO: {

                try {
                    Log.i("LOGIN", "GetHomeInfo Called...");
                    JSONObject jo = Result.object;
                    CaApplication.m_Info.strName = jo.getString("name");

                    if(!jo.getString("strCarModel").equals("정보없음")){
                        CaApplication.m_Info.dEfficiency = jo.getDouble("efficiency");
                        CaApplication.m_Info.strCarModel = jo.getString("car_model_name");
                        CaApplication.m_Info.dBatteryCapacity = jo.getDouble("battery_capacity");
                        CaApplication.m_Info.nCurrentCap = jo.getInt("current_capacity");
                    }

                    CaApplication.m_Engine.GetScheduleInfo(CaApplication.m_Info.strId,this,this);
                    Intent it = new Intent(this, ActivityHome.class);
                    startActivity(it);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            case CaEngine.GET_CHARGE_HISTORY: {

                try {
                    JSONObject jo = Result.object;
                    if(jo.getJSONArray("list_history").length() == 0){
                    }
                    else{
                        JSONArray ja = jo.getJSONArray("list_history");

                        alHistory.clear();
                        Log.i("ChargeHistory" , "ja size" + ja.length());

                        Calendar calToday = Calendar.getInstance();

                        strYearMonth = mYearMonth.format(calToday.getTime());

                        for(int i=0;i<ja.length();i++){
                            JSONObject joHistory = ja.getJSONObject(i);
                            CaHistory history = new CaHistory();

                            history.dtReserve = CaApplication.m_Info.parseDate(joHistory.getString("reserve_time"));
                            history.nReserveType = joHistory.getInt("reserve_type");
                            history.nFee = joHistory.getInt("expected_fee");


                            if(mYearMonth.format(history.dtReserve).equals(strYearMonth)){
                                alHistory.add(history);
                            }


                        }
                        Log.i("ChargeHistory" , "alHistory size" + alHistory.size());

                    }
                    tvMargin.setText(CaApplication.m_Info.m_dfWon.format(alHistory.size() * 1562) +" 원을 벌었어요");


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