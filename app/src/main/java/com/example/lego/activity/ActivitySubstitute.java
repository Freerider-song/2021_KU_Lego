package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lego.CaApplication;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;

import org.json.JSONException;

public class ActivitySubstitute extends BaseActivity implements IaResultHandler {

    TextView tvName, tvId, tvVoucher, tvVoucherDetail, tvPhone, tvCarModel, tvCarNumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substitute);

        prepareDrawer();

        tvName = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_phone);
        tvCarNumber = findViewById(R.id.tv_car_number);

        tvName.setText("     " + CaApplication.m_Info.strName);
        tvId.setText("     "+ CaApplication.m_Info.strId);

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


        }
    }


    @Override
    public void onResult(CaResult Result) throws JSONException {

    }
}