package com.example.lego.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lego.CaApplication;
import com.example.lego.CaEngine;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;
import com.example.lego.model.CaCustomer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.lego.CaApplication.m_Context;

public class ActivitySubstitute extends BaseActivity implements IaResultHandler {

    TextView tvName, tvPickupBefore, tvPickupBeforeTime,tvPickup, tvPickupTime, tvPhone, tvCharging, tvChargeComplete, tvChargeCompleteTime;

    int nReserveId;
    Date dtReserve;
    String strLocation;
    String strNotice;
    String strDriverName,strDriverPhone;
    Date dtPickUp = null;
    Date dtComplete= null;
    boolean bPick = false, bCom = false;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substitute);
        CaApplication.m_Engine.GetSubInfo(CaApplication.m_Info.strId,this,this);

        prepareDrawer();

        tvName = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_phone);
        tvPickupBefore = findViewById(R.id.tv_pickup_before);
        tvPickupBeforeTime = findViewById(R.id.tv_pickup_before_time);
        tvPickup = findViewById(R.id.tv_pickup);
        tvPickupTime = findViewById(R.id.tv_pickup_time);
        tvCharging = findViewById(R.id.tv_charging);
        tvChargeComplete = findViewById(R.id.tv_charge_complete);
        tvChargeCompleteTime = findViewById(R.id.tv_charge_complete_time);

        tvName.setText("     " + CaApplication.m_Info.strName);

    }
    public void viewSetting(){
        tvName.setText("     "+ strDriverName);
        tvPhone.setText("     "+ strDriverPhone);



        if(dtReserve!= null){
            tvPickupBeforeTime.setText(mFormat.format(dtReserve));
            if(!bPick){
                tvPickupBefore.setTextColor(getResources().getColor(R.color.bright_blue));
                tvPickupTime.setVisibility(View.INVISIBLE);
                tvChargeCompleteTime.setVisibility(View.INVISIBLE);
            }
            else if(!bCom){
                tvPickupTime.setText(mFormat.format(dtPickUp));
                tvCharging.setTextColor(getResources().getColor(R.color.bright_blue));
                tvChargeCompleteTime.setVisibility(View.INVISIBLE);
            }
            else if(bCom){
                tvPickupTime.setText(mFormat.format(dtPickUp));
                tvChargeComplete.setTextColor(getResources().getColor(R.color.bright_blue));
                tvChargeCompleteTime.setText(mFormat.format(dtComplete));
            }
        }


    }

    @Override
    protected  void onResume() {

        super.onResume();
        CaApplication.m_Engine.GetSubInfo(CaApplication.m_Info.strId,this,this);

    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();
            }
            break;

            case R.id.btn_menu: {
                m_Drawer.openDrawer();
            }
            break;

            case R.id.btn_reserve: {

                Intent it = new Intent(this, ActivitySubstituteRequest.class);
                startActivity(it);
            }
            break;


        }
    }


    @Override
    public void onResult(CaResult Result) throws JSONException {
        if (Result.object == null) {
            Toast.makeText(m_Context, "Check Network", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (Result.m_nCallback) {
            case CaEngine.GET_SUB_INFO: {

                try {
                    Log.i("Substitute", "GetSubInfo Called...");
                    JSONObject jo = Result.object;

                    int nResultCode= jo.getInt("result_code");
                    if(nResultCode == 1){
                        nReserveId= jo.getInt("reserve_id");
                        if(!jo.isNull("reserve_time") && !jo.getString("reserve_time").equals("None")){
                            dtReserve = CaApplication.m_Info.parseDate(jo.getString("reserve_time"));
                        }

                        strLocation = jo.getString("location");
                        strNotice = jo.getString("notice");
                        strDriverName = jo.getString("driver_name");
                        strDriverPhone = jo.getString("driver_phone");
                        if(!jo.isNull("pick_up_time") && !jo.getString("pick_up_time").equals("None")){
                            bPick = true;
                            dtPickUp = CaApplication.m_Info.parseDate(jo.getString("pick_up_time"));
                        }
                        if(!jo.isNull("complete_time")&& !jo.getString("complete_time").equals("None")){
                            bCom = true;
                            dtComplete = CaApplication.m_Info.parseDate(jo.getString("complete_time"));
                        }
                        Log.i("Substitute", "dtPickup and bPickup " + dtPickUp + bPick + " , dtComplete: " + dtComplete);

                    }
                    else{
                        nReserveId= 0;
                        dtReserve = null;
                        strLocation = "";
                        strNotice = "";
                        strDriverName = "정보없음";
                        strDriverPhone = "정보없음";
                        dtPickUp = null;
                        dtComplete = null;

                    }
                    viewSetting();



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