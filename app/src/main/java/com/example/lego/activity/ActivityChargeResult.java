package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lego.CaApplication;
import com.example.lego.CaEngine;
import com.example.lego.CaPref;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.lego.CaApplication.m_Context;

public class ActivityChargeResult extends AppCompatActivity implements IaResultHandler {

    CaPref m_Pref;
    TextView tvTitle, tvFeeTitle, tvFee, tvPay, tvFinalFee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_result);

        m_Context = getApplicationContext();
        m_Pref = new CaPref(m_Context);


        CaApplication.m_Engine.GetChargeResult(CaApplication.m_Info.nServiceReservation, this,this);

        tvTitle = findViewById(R.id.tv_final_reserve_type);
        tvFeeTitle = findViewById(R.id.tv_final_fee_title);
        tvFee = findViewById(R.id.tv_expected_fee);
        tvFinalFee = findViewById(R.id.tv_final_fee);
        tvPay = findViewById(R.id.tv_pay);



        if(CaApplication.m_Info.nReserveType == 2){ // 방전이라면
            tvTitle.setText("쿠이미가 똑똑한 \n 방전을 완료했어요");
            tvFeeTitle.setText("최종 금액");
            tvPay.setText("포인트로 전환됩니다.\n 언제든지 지갑으로 옮길 수 있어요");
        }

        else if(CaApplication.m_Info.nReserveType ==3){
            tvTitle.setText("쿠이미가 똑똑한 \n 충·방전을 완료했어요");
        }

        if(CaApplication.m_Info.dReserveTimeRatio >1) CaApplication.m_Info.dReserveTimeRatio = 1;

        tvFee.setText(CaApplication.m_Info.m_dfWon.format(CaApplication.m_Info.nExpectedFee*CaApplication.m_Info.dReserveTimeRatio) + "원");
        tvFee.setPaintFlags(tvFee.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
        if(CaApplication.m_Info.nReserveType !=2){
            tvFinalFee.setText(CaApplication.m_Info.m_dfWon.format(CaApplication.m_Info.nExpectedFee* CaApplication.m_Info.dReserveTimeRatio * 0.8) +"원");
        }
        else {
            CaApplication.m_Info.nPoint = CaApplication.m_Info.nPoint + Integer.parseInt(String.valueOf(Math.round(CaApplication.m_Info.dReserveTimeRatio*CaApplication.m_Info.nExpectedFee / 0.8)));
            tvFinalFee.setText(CaApplication.m_Info.m_dfWon.format(CaApplication.m_Info.dReserveTimeRatio*CaApplication.m_Info.nExpectedFee / 0.8) +"원");
        }


    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_back: {
                Intent it = new Intent(this, ActivityHome.class);
                startActivity(it);
            }
            break;

            case R.id.btn_next: {

                CaApplication.m_Engine.SetServicePaid(CaApplication.m_Info.nServiceReservation, this,this);

            }
            break;


        }
    }

    @Override
    public void onResult(CaResult Result) {
        if (Result.object == null) {
            Toast.makeText(m_Context, "Check Network", Toast.LENGTH_SHORT).show();
            return;
        }

        switch (Result.m_nCallback) {
            case CaEngine.GET_CHARGE_RESULT: {

                try {
                    JSONObject jo = Result.object;
                    CaApplication.m_Info.nExpectedFee = jo.getInt("expected_fee");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            case CaEngine.SET_SERVICE_PAID: {

                try {
                    JSONObject jo = Result.object;
                    int nResultCode = jo.getInt("result_code");

                    if(nResultCode == 1){
                        AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityChargeResult.this);
                        dlg.setMessage("서비스가 완료되었습니다"); // 메시지

                        dlg.setPositiveButton("확인",new DialogInterface.OnClickListener(){
                            public void onClick(DialogInterface dialog, int which) {
                                m_Pref.setValue(CaPref.PREF_CURRENT_CAP, 45);
                                Intent it = new Intent(ActivityChargeResult.this, ActivityHome.class);
                                startActivity(it);
                            }
                        });
                        dlg.show();
                    }

                    else{
                        AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityChargeResult.this);
                        dlg.setMessage("결제에 실패했습니다"); // 메시지

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

    @Override
    public void onBackPressed() {
        finish();

    }
}