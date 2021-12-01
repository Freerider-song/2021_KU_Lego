package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

//import com.example.kuime.ActivityLogin;
import com.example.lego.R;

public class ActivitySignUpComplete extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_sign_up_complete);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back_login: {
                Intent it = new Intent(this, ActivityLogin.class);
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