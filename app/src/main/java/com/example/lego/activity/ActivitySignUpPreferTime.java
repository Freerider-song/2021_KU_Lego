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

public class ActivitySignUpPreferTime extends AppCompatActivity implements IaResultHandler {

    EditText etHour, etMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_prefer_time);

        etHour = findViewById(R.id.et_hour);
        etMinute = findViewById(R.id.et_minute);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();
            }
            break;
            case R.id.btn_next: {
                String strHour = etHour.getText().toString();
                String strMinute = etMinute.getText().toString();

                if (strHour.isEmpty() || strMinute.isEmpty()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(ActivitySignUpPreferTime.this);
                    //dlg.setTitle("경고"); //제목
                    dlg.setMessage("선호하는 시각을 입력해주세요"); // 메시지
                    //dlg.setIcon(R.drawable.deum); // 아이콘 설정
//                버튼 클릭시 동작

                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dlg.show();
                }
                else{
                    Intent it = new Intent(this, ActivitySignUpPreferStation.class);
                    startActivity(it);
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
            case CaEngine.SET_SIGN_UP_INFO: {

                try {
                    JSONObject jo = Result.object;
                    int nResultCode = jo.getInt("result_code");

                    if (nResultCode == 1) {

                        Intent it = new Intent(this, ActivitySignUpComplete.class);
                        startActivity(it);

                    } else {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(ActivitySignUpPreferTime.this);
                        dlg.setMessage("회원가입이 정상적으로 진행되지 않았습니다. 처음부터 다시 진행해주세요");
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