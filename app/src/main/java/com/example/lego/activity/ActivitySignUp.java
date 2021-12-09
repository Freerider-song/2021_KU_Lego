package com.example.lego.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import com.example.kuime.ActivityLogin;
import com.example.lego.CaApplication;
import com.example.lego.CaEngine;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;
import com.example.lego.model.CaCarModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivitySignUp extends AppCompatActivity implements IaResultHandler {

    TextView tvCheck;
    String strId, strPassword, strPasswordCheck;
    private EditText etId;
    private EditText etPassword, etPasswordCheck;
    String preText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etId = findViewById(R.id.et_id);
        etPassword = findViewById(R.id.et_password);
        etPasswordCheck = findViewById(R.id.et_password_check);
        tvCheck = findViewById(R.id.tv_check);

        strPassword = etPassword.getText().toString();
        strPasswordCheck = etPasswordCheck.getText().toString();

        if(!strPassword.equals(strPasswordCheck)){
            tvCheck.setVisibility(View.VISIBLE);
        }
        else{
            tvCheck.setVisibility(View.INVISIBLE);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();

            }
            break;
            case R.id.btn_next: {
                strId = etId.getText().toString();
                strPassword = etPassword.getText().toString();
                strPasswordCheck = etPasswordCheck.getText().toString();

                if (strId.isEmpty() || strPassword.isEmpty() || strPasswordCheck.isEmpty()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(ActivitySignUp.this);
                    dlg.setMessage("아이디와 비밀번호를 입력해주세요"); // 메시지

                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dlg.show();
                }

                else if(!strPassword.equals(strPasswordCheck)){
                    AlertDialog.Builder dlg = new AlertDialog.Builder(ActivitySignUp.this);
                    dlg.setMessage("비밀번호가 일치하지 않습니다."); // 메시지

                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dlg.show();
                }
                else {
                    CaApplication.m_Info.strId = strId;
                    CaApplication.m_Info.strPassword = strPassword;
                    CaApplication.m_Engine.SetSignUpInfo(CaApplication.m_Info.strName, CaApplication.m_Info.strPhone,
                            strId, strPassword, this, this);

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
                    Log.i("SignUpCar", "GetCarCompanyInfo called..");
                    JSONObject jo = Result.object;
                    int nResultCode = jo.getInt("result_code");
                    if(nResultCode == 1){

                        Intent it = new Intent(this, ActivitySignUpComplete.class);
                        startActivity(it);
                    }

                    else{
                        AlertDialog.Builder dlg = new AlertDialog.Builder(ActivitySignUp.this);
                        dlg.setMessage("회원가입이 정상적으로 진행되지 않았습니다."); // 메시지

                        dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
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