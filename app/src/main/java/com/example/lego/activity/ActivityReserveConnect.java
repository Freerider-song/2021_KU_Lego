package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lego.CaApplication;
import com.example.lego.R;

public class ActivityReserveConnect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_connect);

        TextView tvStationName = findViewById(R.id.tv_station_name);
        TextView tvChargerName = findViewById(R.id.tv_charger_used);

        tvStationName.setText(CaApplication.m_Info.strStationName);
        tvChargerName.setText(CaApplication.m_Info.strChargerName);
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();

            }
            break;
            case R.id.btn_next: {
                Intent it = new Intent(this, ActivityReserveService.class);
                startActivity(it);
            }
            break;


        }
    }

    @Override
    public void onBackPressed() {
        finish();

    }

}