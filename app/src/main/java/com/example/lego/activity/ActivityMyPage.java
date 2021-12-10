package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.kuime.ActivityLogin;
import com.example.lego.CaApplication;
import com.example.lego.CaEngine;
import com.example.lego.CaPref;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityMyPage extends BaseActivity implements IaResultHandler {

    TextView tvName, tvId, tvVoucher, tvVoucherDetail, tvPhone, tvCarModel, tvCarNumber;
    String Name, Id, Phone, CarModel, CarNumber, VoucherName, VoucherPeriod, TotalCount, PayDate, Count;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        //CaApplication.m_Engine.GetMyPageInfo(CaApplication.m_Info.strId, this,this);

        prepareDrawer();

        tvName = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_phone);
        tvId = findViewById(R.id.tv_id);
        tvVoucher = findViewById(R.id.tv_voucher);
        tvVoucherDetail = findViewById(R.id.tv_voucher_detail);
        tvCarModel = findViewById(R.id.tv_car_model);
        tvCarNumber = findViewById(R.id.tv_car_number);

        tvName.setText("     " + CaApplication.m_Info.strName);
        tvId.setText("     "+ CaApplication.m_Info.strId);
        tvCarModel.setText(CaApplication.m_Info.strCarModel);
        tvCarNumber.setText(CaApplication.m_Info.strCarNumber);


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
    public void onBackPressed() {
        finish();

    }

    @Override
    public void onResult(CaResult Result) throws JSONException {

        if (Result.object == null) {
            Toast.makeText(getApplicationContext(), "Check Network", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (Result.m_nCallback) {
            case CaEngine.GET_MY_PAGE_INFO: {

                try {
                    Log.i("SignUpCar", "GetCarCompanyInfo called..");
                    JSONObject jo = Result.object;

                    Name = jo.getString("name");
                    Phone = jo.getString("phone");
                    CarModel = jo.getString("car_model_name");
                    CarNumber = jo.getString("car_number");
                    Id = jo.getString("customer_id");


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            default: {
                //Log.i(TAG, "Unknown type result received");
            }
            break;

        }

    }
}