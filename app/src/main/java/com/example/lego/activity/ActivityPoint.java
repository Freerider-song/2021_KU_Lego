package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lego.CaApplication;
import com.example.lego.R;

public class ActivityPoint extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);

        TextView tvPoint = findViewById(R.id.tv_point);
        tvPoint.setText("P " + CaApplication.m_Info.nPoint);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();
            }
            break;

        }
    }

    @Override
    public void onBackPressed() {
        finish();

    }
}