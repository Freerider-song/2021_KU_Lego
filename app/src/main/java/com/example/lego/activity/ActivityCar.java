package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lego.CaApplication;
import com.example.lego.R;

public class ActivityCar extends AppCompatActivity {

    TextView tvCarCompany, tvCarModel, tvEfficiency;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        tvCarCompany = findViewById(R.id.tv_car_company);
        tvCarModel = findViewById(R.id.tv_car_model);
        tvEfficiency = findViewById(R.id.tv_efficiency);

        tvCarCompany.setText(CaApplication.m_Info.strCarCompany);
        tvCarModel.setText(CaApplication.m_Info.strCarModel);
        if(CaApplication.m_Info.dEfficiency != 0.1){
            tvEfficiency.setText("연비: " + CaApplication.m_Info.dEfficiency +"km/kW\n배터리 용량: " + CaApplication.m_Info.dBatteryCapacity+"kW");
        }
        else{
            tvEfficiency.setText("연비: 정보없음\n배터리 용량: " + CaApplication.m_Info.dBatteryCapacity+"kW");
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();
            }
            break;

            case R.id.btn_car: {

                AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityCar.this);
                dlg.setTitle("확인"); //제목
                dlg.setMessage("해당 기능을 준비 중입니다."); // 메시지

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                dlg.show();

            }
            break;


        }
    }

    @Override
    public void onBackPressed() {
        finish();

    }
}