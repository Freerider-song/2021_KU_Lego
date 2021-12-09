package com.example.lego.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lego.CaApplication;
import com.example.lego.CaEngine;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityChargeTime extends AppCompatActivity implements IaResultHandler {

    EditText etCharge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_time);

        etCharge = findViewById(R.id.et_charge);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();
            }
            break;
            case R.id.btn_next: {
                String strChargeTime = etCharge.getText().toString()+":00";



                if (strChargeTime.isEmpty()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityChargeTime.this);
                    //dlg.setTitle("경고"); //제목
                    dlg.setMessage("충전 완료 시각을 입력해주세요"); // 메시지
                    //dlg.setIcon(R.drawable.deum); // 아이콘 설정
//                버튼 클릭시 동작

                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dlg.show();
                }
                else{
                    CaApplication.m_Engine.SetChargeCompleteInfo(CaApplication.m_Info.strId, strChargeTime,this,this);

                }

            }
            break;


        }
    }

    @Override
    public void onBackPressed() {
        finish();

    }

    @Override
    public void onResult(CaResult Result) {
        if (Result.object == null) {
            Toast.makeText(getApplicationContext(), "Check Network", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (Result.m_nCallback) {
            case CaEngine.SET_CHARGE_COMPLETE_INFO: {

                try {
                    JSONObject jo = Result.object;
                    int nResultCode = jo.getInt("result_code");

                    if (nResultCode == 1) {

                        Intent it = new Intent(this, ActivityHome.class);
                        startActivity(it);

                    } else {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityChargeTime.this);
                        dlg.setMessage("충전 시간이 정상적으로 입력되지 않았습니다. 다시 시도해 주세요");
                        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                            }
                        });
                        dlg.show();
                    }
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