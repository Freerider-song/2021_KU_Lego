package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

//import com.example.kuime.ActivityLogin;
import com.example.lego.CaApplication;
import com.example.lego.CaPref;
import com.example.lego.R;

public class ActivityMyPage extends AppCompatActivity {

    TextView tvVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        tvVersion = findViewById(R.id.tv_version);
        tvVersion.setText(getVersion());

        TextView tvPoint = findViewById(R.id.tv_point);
        tvPoint.setText("P " + CaApplication.m_Info.nPoint);
    }

    private String getVersion()
    {
        String version = "";
        try {
            PackageInfo packageInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
            version = packageInfo.versionName;
            version += " ";
        }
        catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("version : " + version);
        return version;
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();
            }
            break;

            case R.id.cl_point: {
                Intent it = new Intent(this, ActivityPoint.class);
                startActivity(it);
            }
            break;

            case R.id.cl_car: {
                Intent it = new Intent(this, ActivityCar.class);
                startActivity(it);
            }
            break;

            case R.id.cl_card: {
                Intent it = new Intent(this, ActivityCard.class);
                startActivity(it);

            }
            break;

            case R.id.btn_logout: {

                AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityMyPage.this);
                dlg.setTitle("확인"); //제목
                dlg.setMessage("로그아웃 하시겠습니까?"); // 메시지

                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        final Context Ctx=getApplicationContext();

                        CaPref pref = new CaPref(Ctx);

                        pref.setValue(CaPref.PREF_MEMBER_ID, "");
                        pref.setValue(CaPref.PREF_PASSWORD, "");

                        final Class Clazz= ActivityLogin.class;

                        Intent nextIntent = new Intent(Ctx, Clazz);
                        startActivity(nextIntent);
                    }
                });

                dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
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

}