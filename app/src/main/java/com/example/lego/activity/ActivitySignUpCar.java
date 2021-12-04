package com.example.lego.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
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

import java.util.ArrayList;

public class ActivitySignUpCar extends AppCompatActivity implements IaResultHandler {

    public ArrayList<String> alCompany=new ArrayList<>();
    public ArrayList<CaCarModel> alModel=new ArrayList<>();
    public ArrayList<String> alStrModel=new ArrayList<>();

    public Spinner spCompany;
    public Spinner spModel;

    int nCompany = 0;
    int nModel = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_car);

        spCompany = findViewById(R.id.sp_car_company);
        spModel = findViewById(R.id.sp_car_model);
        CaApplication.m_Engine.GetCarCompanyInfo(this,this);

    }

    public void getCarModelInfo(int nCompany){
        CaApplication.m_Engine.GetCarModelInfo(alCompany.get(nCompany), this, this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();

            }
            break;
            case R.id.btn_next: {
                CaApplication.m_Info.bCarRegistered = true;
                Intent it = new Intent(this, ActivitySignUpCarNum.class);
                startActivity(it);
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
            case CaEngine.GET_CAR_COMPANY_INFO: {

                try {
                    Log.i("SignUpCar", "GetCarCompanyInfo called..");
                    JSONObject jo = Result.object;
                    JSONArray ja = jo.getJSONArray("manufacturers");

                    for (int i=0; i<ja.length(); i++) {
                        JSONObject joCompany=ja.getJSONObject(i);

                        alCompany.add(joCompany.getString("manufacturer"));

                    }

                    ArrayAdapter<String> AdapterCompany = new ArrayAdapter<String>(this, R.layout.spinner_layout, alCompany){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                            View v = super.getView(position, convertView, parent);
                            ((TextView) v).setTextSize(22.0f);
                            ((TextView) v).setTextColor(getResources().getColor(R.color.ks_gray));
                            return v;

                        }
                    };

                    spCompany.setEnabled(true);
                    spCompany = findViewById(R.id.sp_car_company);
                    spCompany.setAdapter(AdapterCompany);
                    spCompany.setSelection(nCompany);

                    spCompany.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            nCompany=position;
                            CaApplication.m_Info.strCarCompany = alCompany.get(nCompany);
                            ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
                            CaApplication.m_Engine.GetCarModelInfo(alCompany.get(nCompany), ActivitySignUpCar.this, ActivitySignUpCar.this);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            break;

            case CaEngine.GET_CAR_MODEL_INFO: {

                Log.i("SignUpCar", "GetCarModelInfo called..");
                try {
                    JSONObject jo = Result.object;
                    JSONArray ja = jo.getJSONArray("models");

                    alModel.clear();
                    alStrModel.clear();
                    for (int i=0; i<ja.length(); i++) {

                        JSONObject joModel=ja.getJSONObject(i);
                        CaCarModel model=new CaCarModel();
                        model.nModelId = joModel.getInt("model_id");
                        model.strModelName = joModel.getString("model_name");

                        alStrModel.add(joModel.getString("model_name"));

                        alModel.add(model);
                    }

                    spModel = findViewById(R.id.sp_car_model);
                    ArrayAdapter AdapterModel = new ArrayAdapter<String>(this, R.layout.spinner_layout, alStrModel){
                        @NonNull
                        @Override
                        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                            View v = super.getView(position, convertView, parent);

                            ((TextView) v).setTextSize(22.0f);
                            ((TextView) v).setTextColor(getResources().getColor(R.color.ks_gray));
                            return v;

                        }
                    };
                    spModel.setEnabled(true);
                    spModel.setAdapter(AdapterModel);
                    spModel.setSelection(nModel);
                    spModel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            nModel=position;
                            CaCarModel model = alModel.get(nModel);
                            CaApplication.m_Info.strCarModel = model.strModelName;
                            CaApplication.m_Info.nModelId = model.nModelId;
                            ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


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