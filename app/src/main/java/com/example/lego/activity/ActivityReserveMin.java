package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.lego.CaApplication;
import com.example.lego.R;

public class ActivityReserveMin extends AppCompatActivity {

    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_min);

        TextView tvMinKm = findViewById(R.id.tv_min_km);


        ArrayAdapter arrayAdapter= ArrayAdapter.createFromResource(this, R.array.min_cap, R.layout.spinner_layout);

        spinner = findViewById(R.id.sp_min_capacity);

        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String str = (String) spinner.getSelectedItem();
                CaApplication.m_Info.nMinCapacity = Integer.parseInt(str.substring(0, str.length()-1));
                Log.i("RESERVEMIN" , "min capacity is " + CaApplication.m_Info.nMinCapacity);
                tvMinKm.setText("주행가능거리: " + String.format("%.0f", CaApplication.m_Info.dBatteryCapacity
                        * (double)CaApplication.m_Info.nMinCapacity
                        / 100 * CaApplication.m_Info.dEfficiency)+ "km");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();

            }
            break;
            case R.id.btn_next: {
                Intent it = new Intent(this, ActivityReserveTime.class);
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