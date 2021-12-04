package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lego.R;

public class ActivityReserveEnd extends AppCompatActivity {

    TextView tvTitle, tvFee, tvFeeTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_end);

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

            case R.id.btn_info: {

            }
            break;



        }
    }


    @Override
    public void onBackPressed() {
        finish();

    }

}