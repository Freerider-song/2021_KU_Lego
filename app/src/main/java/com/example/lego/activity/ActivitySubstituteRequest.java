package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.lego.CaApplication;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;

import org.json.JSONException;

public class ActivitySubstituteRequest extends BaseActivity implements IaResultHandler {

    TextView tvName, tvId, tvVoucher, tvVoucherDetail, tvPhone, tvCarModel, tvCarNumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substitute_request);

        prepareDrawer();

        tvName = findViewById(R.id.tv_name);
        tvPhone = findViewById(R.id.tv_phone);
        tvCarNumber = findViewById(R.id.tv_car_number);

        tvName.setText("     " + CaApplication.m_Info.strName);
        //tvPhone.setText("     " + CaApplication.m_Info.strPhone);
        tvCarNumber.setText("     " + CaApplication.m_Info.strCarNumber);

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

            case R.id.btn_reserve: {
                // 대리 api
                AlertDialog.Builder dlg = new AlertDialog.Builder(ActivitySubstituteRequest.this);
                dlg.setMessage("대리 충전 예약이 정상적으로 진행되었습니다");
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent it = new Intent(ActivitySubstituteRequest.this, ActivitySubstitute.class);
                        startActivity(it);
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