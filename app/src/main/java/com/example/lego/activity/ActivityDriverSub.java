package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;

import org.json.JSONException;

public class ActivityDriverSub extends AppCompatActivity implements IaResultHandler {

    TextView tvName, tvTime, tvLocation, tvCarModel, tvCarNumber, tvPhone, tvNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_sub);

        tvName = findViewById(R.id.tv_name);
        tvTime = findViewById(R.id.tv_time);
        tvLocation = findViewById(R.id.tv_location);
        tvCarModel = findViewById(R.id.tv_car_model);
        tvCarNumber = findViewById(R.id.tv_car_number);
        tvPhone = findViewById(R.id.tv_phone);
        tvNotice = findViewById(R.id.tv_notice);

        Intent intent = getIntent();
        int nReserveId = intent.getIntExtra("customer", 0);

    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_back: {
                finish();
            }
            break;

            case R.id.btn_pickup: {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityDriverSub.this);

                dlg.setMessage("고객님에게 픽업 완료를 전하였습니다."); // 메시지
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.show();
            }
            break;

            case R.id.btn_complete: {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityDriverSub.this);
                dlg.setMessage("고객님에게 충전 완료를 전하였습니다."); // 메시지
                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.show();
            }
            break;


        }
    }


    @Override
    public void onResult(CaResult Result) throws JSONException {

    }
}