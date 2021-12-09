package com.example.lego.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.lego.CaApplication;
import com.example.lego.CaEngine;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;
import com.example.lego.model.CaStation;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.lego.R.drawable.v2g_marker;

public class ActivitySignUpPreferStation extends AppCompatActivity implements IaResultHandler, OnMapReadyCallback, ActivityCompat.OnRequestPermissionsResultCallback {

    private GoogleMap mMap;
    private Marker currentMarker = null;
    public ArrayList<CaStation> alStation = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_prefer_station);

        alStation = CaApplication.m_Info.alStation;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull @NotNull GoogleMap googleMap) {

        mMap = googleMap;
        LatLng JEJU = new LatLng(126.524045, 33.499956);

        //marker size 조절
        int height = 150;
        int width = 150;
        BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.electricity_marker);
        BitmapDrawable bitmapdraw2 = (BitmapDrawable)getResources().getDrawable(v2g_marker);
        Bitmap b=bitmapdraw.getBitmap();
        Bitmap b2=bitmapdraw2.getBitmap();
        Bitmap smallMarker = Bitmap.createScaledBitmap(b, width, height, false);
        Bitmap v2gMarker = Bitmap.createScaledBitmap(b2, width, height, false);


        MarkerOptions markerOptions = new MarkerOptions();
        Log.i("MAP", "마커설정시 alStation 길이 " + alStation.size());

        for(int i = 0; i<alStation.size(); i++){
            CaStation station = alStation.get(i);
            markerOptions
                    .position(new LatLng(station.dx, station.dy))
                    .title(station.strStationName)
                    .snippet("급속 "+station.nFastCharger + " 완속 " + station.nSlowCharger + " V2G " + station.nV2gCharger);
            if(station.nV2gCharger>0){
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(smallMarker));
            }
            else{
                markerOptions.icon(BitmapDescriptorFactory.fromBitmap(v2gMarker));
            }
            mMap.addMarker(markerOptions);
        }
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(JEJU, 16));

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull @NotNull Marker marker) {
                MapBottom bottomSheet = new MapBottom();
                Bundle bundle = new Bundle();
                bundle.putString("station_name", marker.getTitle());
                for(int i = 0; i<alStation.size(); i++){
                    CaStation station = alStation.get(i);
                    //Log.i("Map", "충전소 이름찾기 " + marker.getTitle() + " = " + station.strStationName);
                    if(station.strStationName.equals(marker.getTitle())){
                        CaApplication.m_Info.strStationName = station.strStationName;
                        CaApplication.m_Info.nStationId = station.nStationId;
                        CaApplication.m_Info.nSlowCharger = station.nSlowCharger;
                        CaApplication.m_Info.nFastCharger = station.nFastCharger;
                        CaApplication.m_Info.nV2gCharger = station.nV2gCharger;

                        Log.i("Map", "충전기 갯수들 " + CaApplication.m_Info.nFastCharger + " " + CaApplication.m_Info.nV2gCharger +
                                " " + CaApplication.m_Info.nSlowCharger);
                    }
                }
                bundle.putString("charger_num", marker.getSnippet());

                bottomSheet.setArguments(bundle);
                bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                return false;
            }
        });
        //참조 https://codinginflow.com/tutorials/android/modal-bottom-sheet

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();
            }
            break;
            case R.id.btn_next: {

                Intent it = new Intent(this, ActivitySignUpPreferBattery.class);
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
            case CaEngine.SET_SIGN_UP_INFO: {

                try {
                    JSONObject jo = Result.object;
                    int nResultCode = jo.getInt("result_code");

                    if (nResultCode == 1) {

                        Intent it = new Intent(this, ActivitySignUpComplete.class);
                        startActivity(it);

                    } else {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(ActivitySignUpPreferStation.this);
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