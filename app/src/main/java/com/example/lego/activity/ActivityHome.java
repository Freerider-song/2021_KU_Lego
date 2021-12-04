package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lego.CaEngine;
import com.example.lego.CaApplication;
import com.example.lego.CaPref;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;
import com.example.lego.model.CaHistory;
import com.example.lego.model.CaStation;

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
    ImageView ivCar;
    Button btnMap, btnCar;

    long now;
    Date mNow;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    ArrayList<CaHistory> alHistory = new ArrayList<>();

    int nCurrentCap=62;

    SimpleDateFormat mYearMonth = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat mMonthDay = new SimpleDateFormat("MM-dd");

    String strYearMonth;

    public void calRatio(){
        if(CaApplication.m_Info.dtStart != null && CaApplication.m_Info.dtEnd != null){
            long calDate = CaApplication.m_Info.dtEnd.getTime() - CaApplication.m_Info.dtStart.getTime();
            long calNow = now-CaApplication.m_Info.dtStart.getTime();
            if(calNow>=0){
                CaApplication.m_Info.dReserveTimeRatio = calNow / (double) calDate;
                Log.d("HOME", "ReserveTimeRatio is " + CaApplication.m_Info.dReserveTimeRatio);
                if(mNow.before(CaApplication.m_Info.dtEnd)){
                    CaApplication.m_Info.dtStart = mNow; //시작시간을 현재시간으로 바꿔주어서 나중에 다시 이 화면에 들어오게 되었을 때 RTRatio 가 현실 반영하게끔 바꿔줌
                }
            }

        }
        nCurrentCap = CaApplication.m_Info.nCurrentCap; //nCurrentCap = 45
        Log.i("HOME", "current cap is " +nCurrentCap + "ratio is " + CaApplication.m_Info.dReserveTimeRatio);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        prepareDrawer();

        m_Context = getApplicationContext();
        m_Pref = new CaPref(m_Context);
        now = System.currentTimeMillis();
        mNow = new Date(now);



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

        if(CaApplication.m_Info.dEfficiency !=0.1){
            tvEfficiency.setText(String.format("%.0f", CaApplication.m_Info.dBatteryCapacity
                    * (double)nCurrentCap
                    / 100 * CaApplication.m_Info.dEfficiency)+ " km");
        }
        else{
            tvEfficiency.setVisibility(View.INVISIBLE);
        }

        calRatio();

    }

    @Override
    protected  void onResume() {

        super.onResume();
        CaApplication.m_Engine.GetHomeInfo(CaApplication.m_Info.strId, this,this);
        CaApplication.m_Engine.GetChargeHistory(CaApplication.m_Info.strId, this,this);

    }

    public void viewSetting(){

        now = System.currentTimeMillis();
        mNow = new Date(now);

        Log.i("HOME", "bPaid is " + CaApplication.m_Info.bPaid);
        //tvMargin

        if(CaApplication.m_Info.bPaid == 1 || CaApplication.m_Info.bPaid == -1){ //차가 없을때
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

            case R.id.btn_station: {

            }
            break;

            case R.id.iv_more: {
                Intent it = new Intent(this, ActivityFee.class);
                startActivity(it);
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
            case CaEngine.GET_STATION_INFO: {
                Log.i("MAP" , "GetStationInfo called");
                try {
                    JSONObject jo = Result.object;
                    JSONArray ja = jo.getJSONArray("features");

                    CaApplication.m_Info.alStation.clear();

                    for(int i=0;i<ja.length();i++){
                        JSONObject joStation = ja.getJSONObject(i);
                        CaStation station = new CaStation();
                        JSONObject joGeometry = joStation.getJSONObject("geometry");
                        JSONObject joProperties = joStation.getJSONObject("properties");

                        //JSONObject joGeometry = jaGeometry.getJSONObject(0);
                        JSONArray jaDxy =joGeometry.getJSONArray("coordinates");
                        station.dx = jaDxy.getDouble(0);
                        station.dy = jaDxy.getDouble(1);


                        //JSONObject joProperties = jaProperties.getJSONObject(0);
                        station.nFastCharger = joProperties.getInt("fast_charger");
                        station.nSlowCharger = joProperties.getInt("slow_charger");
                        station.nStationId = joProperties.getInt("station_id");
                        station.strStationName = joProperties.getString("station_name");
                        station.nV2gCharger = joProperties.getInt("v2g");
                        Log.i("MAP", "StationNAme = " +station.strStationName  + " " + station.dx + " " +station.dy);
                        CaApplication.m_Info.alStation.add(station);
                    }
                    Intent it = new Intent(this, ActivityMap.class);
                    startActivity(it);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            case CaEngine.GET_CHARGE_INFO: {

                try {
                    JSONObject jo = Result.object;
                    CaApplication.m_Info.nReserveType = jo.getInt("reserve_type");
                    CaApplication.m_Info.dtEnd = CaApplication.m_Info.parseDate(jo.getString("finish_time"));
                    CaApplication.m_Info.nExpectedFee = jo.getInt("expected_fee");
                    CaApplication.m_Info.dDy = jo.getDouble("dx");
                    CaApplication.m_Info.dDx = jo.getDouble("dy");

                    Intent it = new Intent(this, ActivityCharge.class);
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
                    CaApplication.m_Info.strCarModel = jo.getString("car_model_name");
                    if(!jo.getString("efficiency").equals("정보없음")){
                        CaApplication.m_Info.dEfficiency = jo.getDouble("efficiency");
                    }
                    CaApplication.m_Info.dBatteryCapacity = jo.getDouble("battery_capacity");
                    CaApplication.m_Info.bPaid = jo.getInt("is_paid");

                    if(CaApplication.m_Info.bPaid != -1){
                        CaApplication.m_Info.nServiceReservation = jo.getString("service_reservation_id");
                        CaApplication.m_Info.dtStart = CaApplication.m_Info.parseDate(jo.getString("start_time"));
                        CaApplication.m_Info.dtEnd = CaApplication.m_Info.parseDate(jo.getString("finish_time"));
                        CaApplication.m_Info.strStationName = jo.getString("station_name");
                        CaApplication.m_Info.nReserveType = jo.getInt("reserve_type");
                        if (jo.isNull("minimum_capacity")) {
                            //Usage.m_dUsage=0.0;
                            CaApplication.m_Info.nMinCapacity = 0;
                        } else {
                            CaApplication.m_Info.nMinCapacity = jo.getInt("minimum_capacity");
                        }
                    }

                    viewSetting();
                    calRatio();


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