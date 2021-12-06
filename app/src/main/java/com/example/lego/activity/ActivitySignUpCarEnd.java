package com.example.lego.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lego.R;

public class ActivitySignUpCarEnd extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_car_end);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_home: {
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