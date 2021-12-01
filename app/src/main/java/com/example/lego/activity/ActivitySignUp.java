package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

//import com.example.kuime.ActivityLogin;
import com.example.lego.CaApplication;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;

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
                    Intent it = new Intent(this, ActivitySignUpCar.class);
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

    }
}