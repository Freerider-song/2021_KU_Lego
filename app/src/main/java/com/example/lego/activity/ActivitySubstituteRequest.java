package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lego.CaApplication;
import com.example.lego.CaEngine;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.lego.CaApplication.m_Context;

public class ActivitySubstituteRequest extends BaseActivity implements IaResultHandler {

    TextView tvName, tvId, tvVoucher, tvVoucherDetail, tvPhone, tvCarModel, tvCarNumber;
    EditText etLocation, etReserveTime, etNotice;
    String strLocation, strReserveTime, strNotice;
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

        etLocation = findViewById(R.id.et_location);
        etReserveTime = findViewById(R.id.et_reserve_time);
        etNotice = findViewById(R.id.et_notice);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();
            }
            break;



            case R.id.btn_reserve: {

                if (strLocation.isEmpty() || strReserveTime.isEmpty() || strNotice.isEmpty()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(ActivitySubstituteRequest.this);
                    //dlg.setTitle("경고"); //제목
                    dlg.setMessage("빠짐없이 입력해주세요"); // 메시지

                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dlg.show();
                }
                else {
                    CaApplication.m_Engine.SetSubInfo(CaApplication.m_Info.strId,strReserveTime,strLocation, strNotice, this,this);
                }

            }
            break;


        }
    }


    @Override
    public void onResult(CaResult Result) throws JSONException {
        if (Result.object == null) {
            Toast.makeText(m_Context, "Check Network", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (Result.m_nCallback) {
            case CaEngine.SET_SUB_INFO: {

                try {
                    Log.i("LOGIN", "GetHomeInfo Called...");
                    JSONObject jo = Result.object;
                    int nResultCode = jo.getInt("result_code");

                    if(nResultCode == 1){

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


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            default: {

            }
            break;

        }

    }
}