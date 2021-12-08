package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

//import com.example.kuime.ActivityLogin;
import com.example.lego.CaApplication;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;

public class ActivityAuth extends AppCompatActivity implements IaResultHandler {

    EditText etName, etPhone;
    String strName, strPhone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        etName = findViewById(R.id.et_name);
        etPhone = findViewById(R.id.et_phone);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();

            }
            break;
            case R.id.btn_next: {

                strName = etName.getText().toString();
                strPhone = etPhone.getText().toString();

                if (strName.isEmpty() || strPhone.isEmpty()) {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityAuth.this);
                    //dlg.setTitle("경고"); //제목
                    dlg.setMessage("이름과 번호를 입력해주세요"); // 메시지
                    //dlg.setIcon(R.drawable.deum); // 아이콘 설정
//                버튼 클릭시 동작

                    dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dlg.show();
                }
                else {
                    CaApplication.m_Info.strName = strName;
                    CaApplication.m_Info.strPhone = strPhone;
                    Intent it = new Intent(this, ActivitySignUp.class);
                    startActivity(it);
                }


            }
            break;
            case R.id.btn_auth: {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityAuth.this);
                //dlg.setTitle("경고"); //제목
                dlg.setMessage("아직 준비 중입니다. 다음 단계로 진행해주세요"); // 메시지
                //dlg.setIcon(R.drawable.deum); // 아이콘 설정
//                버튼 클릭시 동작

                dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dlg.show();
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