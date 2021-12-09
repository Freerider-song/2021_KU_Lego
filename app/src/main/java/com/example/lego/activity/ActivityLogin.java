package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lego.CaApplication;
import com.example.lego.CaEngine;
import com.example.lego.CaPref;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;
import com.example.lego.model.CaCustomer;
import com.example.lego.model.CaStation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ActivityLogin extends AppCompatActivity implements IaResultHandler {

    private EditText m_etUserId;
    private EditText m_etPassword;

    private Context m_Context;
    private CaPref m_Pref;

    String m_strMemberId;
    String m_strPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        m_Context = getApplicationContext();
        m_Pref = new CaPref(m_Context);

        m_etUserId = findViewById(R.id.input_id);
        m_etPassword = findViewById(R.id.input_pw);

        String savedLoginId = m_Pref.getValue(CaPref.PREF_MEMBER_ID, "");
        if (!savedLoginId.equals("")) {
            m_etUserId.setText(savedLoginId);
        }

        String savedPassword = m_Pref.getValue(CaPref.PREF_PASSWORD, "");
        if (!savedPassword.equals("")) {
            m_etPassword.setText(savedPassword);
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login: {

                m_strMemberId = m_etUserId.getText().toString();
                m_strPassword = m_etPassword.getText().toString();

                if (m_strMemberId.isEmpty() || m_strPassword.isEmpty()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityLogin.this);
                    //dlg.setTitle("경고"); //제목
                    dlg.setMessage("아이디와 비밀번호를 입력해주세요"); // 메시지
                    //dlg.setIcon(R.drawable.deum); // 아이콘 설정
//                버튼 클릭시 동작

                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dlg.show();
                }
                else {
                    Calendar calToday = Calendar.getInstance();
                    calToday.add(Calendar.DATE, 0);
                    SimpleDateFormat myyyyMMddFormat = new SimpleDateFormat("yyMMdd");
                    String m_dtToday = myyyyMMddFormat.format(calToday.getTime())+"1";

                    CaApplication.m_Engine.CheckLogin(m_strMemberId, m_strPassword, this, this);
                }

                //Intent it = new Intent(this, ActivityHome.class);
                //startActivity(it);
            }
            break;

            case R.id.btn_sign_up: {
                Intent it = new Intent(this, ActivityAuth.class);
                startActivity(it);
            }


        }
    }



    @Override
    public void onResult(CaResult Result) {
        if (Result.object == null) {
            Toast.makeText(m_Context, "Check Network", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (Result.m_nCallback) {
            case CaEngine.CB_CHECK_BLD_LOGIN: {

                try {
                    Log.i("LOGIN", "CheckLogin Called...");
                    JSONObject jo = Result.object;
                    int nResultCode = jo.getInt("result_code");

                    if (nResultCode == 1) {
                        m_Pref.setValue(CaPref.PREF_MEMBER_ID, m_strMemberId);
                        m_Pref.setValue(CaPref.PREF_PASSWORD, m_strPassword);

                        CaApplication.m_Info.strId = m_strMemberId;
                        CaApplication.m_Info.strPassword = m_strPassword;

                        CaApplication.m_Engine.GetHomeInfo(m_strMemberId, this,this);


                    }
                    else if (nResultCode == 2) {
                        m_Pref.setValue(CaPref.PREF_MEMBER_ID, m_strMemberId);
                        m_Pref.setValue(CaPref.PREF_PASSWORD, m_strPassword);

                        CaApplication.m_Info.strId = m_strMemberId;
                        CaApplication.m_Info.strPassword = m_strPassword;
                        CaApplication.m_Engine.GetDriverHomeInfo(m_strMemberId, this,this);
                    }
                    else {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityLogin.this);
                        dlg.setMessage("아이디와 비밀번호를 확인해주세요");
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dlg.show();
                    }
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

                    if(!jo.getString("car_model_name").equals("정보없음")){
                        CaApplication.m_Info.dEfficiency = jo.getDouble("efficiency");
                        CaApplication.m_Info.strCarModel = jo.getString("car_model_name");
                        CaApplication.m_Info.dBatteryCapacity = jo.getDouble("battery_capacity");
                        CaApplication.m_Info.nCurrentCap = jo.getInt("current_capacity");
                    }

                    CaApplication.m_Engine.GetScheduleInfo(CaApplication.m_Info.strId,this,this);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            case CaEngine.GET_SCHEDULE_INFO: {

                try {
                    Log.i("LOGIN", "GetSchedule Called...");
                    JSONObject jo = Result.object;

                    if(!jo.getString("charge_time").equals("정보없음")){
                        CaApplication.m_Info.PreferBattery = jo.getInt("prefer_battery");
                        CaApplication.m_Info.MinBatteryTime= CaApplication.m_Info.parseDate(jo.getString("min_battery_time"));
                        CaApplication.m_Info.ChargeTime = CaApplication.m_Info.parseDate(jo.getString("charge_time"));
                    }

                    Intent it = new Intent(this, ActivityHome.class);
                    startActivity(it);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            case CaEngine.GET_DRIVE_HOME_INFO: {

                try {
                    Log.i("LOGIN", "GetHomeInfo Called...");
                    JSONObject jo = Result.object;
                    JSONArray ja = jo.getJSONArray("list");

                    CaApplication.m_Info.alCustomer.clear();

                    for(int i=0;i<ja.length();i++){
                        JSONObject joCustomer = ja.getJSONObject(i);
                        CaCustomer customer = new CaCustomer();

                        customer.nReserveId = joCustomer.getInt("reserve_id");
                        customer.dtReserveTime= CaApplication.m_Info.parseDate(joCustomer.getString("reserve_time"));
                        customer.strLocation = joCustomer.getString("location");
                        customer.strCarModel = joCustomer.getString("car_model_name");
                        customer.strCarNumber = joCustomer.getString("car_number");
                        customer.strNotice = joCustomer.getString("notice");
                        customer.strName = joCustomer.getString("customer_name");
                        customer.strPhone = joCustomer.getString("customer_phone");

                        CaApplication.m_Info.alCustomer.add(customer);
                    }
                    Intent it = new Intent(this, ActivityDriverHome.class);
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