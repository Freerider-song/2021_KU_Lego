package com.example.lego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.lego.CaApplication;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;

import org.json.JSONException;

public class ActivitySubstitute extends BaseActivity implements IaResultHandler {

    TextView tvName, tvPickupBefore, tvPickupBeforeTime,tvPickup, tvPickupTime, tvPhone, tvCharging, tvChargeComplete, tvChargeCompleteTime;
    EditText etLocation, etReserveTime, etNotice;
    String strLocation, strReserveTime, strNotice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substitute);

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

        etLocation = findViewById(R.id.et_location);
        etReserveTime = findViewById(R.id.et_reserve_time);
        etNotice = findViewById(R.id.et_notice);

        tvName.setText("     " + CaApplication.m_Info.strName);

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
                strLocation = etLocation.getText().toString();
                strReserveTime = etReserveTime.getText().toString();
                strNotice = etNotice.getText().toString();

                Intent it = new Intent(this, ActivitySubstituteRequest.class);
                startActivity(it);
            }
            break;


        }
    }


    @Override
    public void onResult(CaResult Result) throws JSONException {

    }
}