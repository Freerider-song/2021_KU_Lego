package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lego.CaApplication;
import com.example.lego.R;

public class ActivityReserveEnd extends AppCompatActivity {

    TextView tvTitle, tvFee, tvFeeTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_end);

        tvTitle = findViewById(R.id.tv_reserve_title);
        tvFee = findViewById(R.id.tv_fee);
        tvFeeTitle = findViewById(R.id.tv_fee_title);

        tvFee.setText(CaApplication.m_Info.nExpectedFee + " 원");
        if(CaApplication.m_Info.nReserveType == 2){
            tvFeeTitle.setText("최소 예상 금액");
            tvTitle.setText("쿠이미가 똑똑한 \n방전을 시작합니다");
        }
        else if(CaApplication.m_Info.nReserveType == 3){
            tvTitle.setText("쿠이미가 똑똑한 \n충·방전을 시작합니다");
        }

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();

            }
            break;
            case R.id.btn_next: {
                Intent it = new Intent(this, ActivityHome.class);
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