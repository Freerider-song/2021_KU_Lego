package com.example.lego.activity;

import static com.example.lego.CaApplication.m_Context;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lego.CaApplication;
import com.example.lego.CaEngine;
import com.example.lego.CaPref;
import com.example.lego.CaResult;
import com.example.lego.IaResultHandler;
import com.example.lego.R;
import com.example.lego.model.CaCharger;
import com.example.lego.model.CaCustomer;
import com.example.lego.model.CaHistory;
import com.example.lego.model.CaStation;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ActivityDriverHome extends BaseActivity implements IaResultHandler {

    CaPref m_Pref;
    TextView tvRemain, tvToday;
    ListView listView;
    private CustomerAdapter CustomerAdapter;
    long now;
    Date mNow;
    SimpleDateFormat mFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

    ArrayList<CaCustomer> alCustomer = new ArrayList<>();

    SimpleDateFormat mMonth = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat mMonthDay = new SimpleDateFormat("MM월 dd일");
    SimpleDateFormat mAm= new SimpleDateFormat("a hh시 mm분");
    String strYearMonth;


    private class CustomerViewHolder {
        public TextView tvName;
        public TextView tvTime, tvLocation, tvCarModel, tvCarNumber;

    }

    private class CustomerAdapter extends BaseAdapter {

        Typeface tf;

        public CustomerAdapter() {
            super();


        }

        @Override
        public int getCount() {
            Log.i("DirverHOme" , "alcustomer size: " + alCustomer.size());
            //return 4;
            return alCustomer.size();
        }

        @Override
        public Object getItem(int position) {
            //return 0;
            return alCustomer.get(position);

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            CustomerViewHolder holder;
            if (convertView == null) {
                holder = new CustomerViewHolder();

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_customer, null);

                holder.tvName = convertView.findViewById(R.id.tv_name);
                holder.tvTime= convertView.findViewById(R.id.tv_time);
                holder.tvLocation = convertView.findViewById(R.id.tv_location);
                holder.tvCarModel= convertView.findViewById(R.id.tv_car_model);
                holder.tvCarNumber = convertView.findViewById(R.id.tv_car_number);

                convertView.setTag(holder);
            }
            else {
                holder = (CustomerViewHolder) convertView.getTag();
            }

            final CaCustomer customer= alCustomer.get(position);
            tf = Typeface.createFromAsset(getAssets(), "fonts/nanumsquareroundr.ttf");
            holder.tvName.setTypeface(tf);
            holder.tvTime.setTypeface(tf);
            holder.tvLocation.setTypeface(tf);
            holder.tvCarModel.setTypeface(tf);
            holder.tvCarNumber.setTypeface(tf);

            Log.i("DirverHOme" , "alcustomer: " + customer.strName);

            holder.tvName.setText(customer.strName);
            holder.tvTime.setText(mAm.format(customer.dtReserveTime) + " 예정");
            holder.tvLocation.setText(customer.strLocation);
            holder.tvCarModel.setText(customer.strCarModel);
            holder.tvCarNumber.setText(customer.strCarNumber);

            return convertView;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home);

        prepareDrawer();

        m_Context = getApplicationContext();
        m_Pref = new CaPref(m_Context);
        now = System.currentTimeMillis();
        mNow = new Date(now);
        Calendar calToday = Calendar.getInstance();


        tvRemain = findViewById(R.id.tv_remain);
        tvToday = findViewById(R.id.tv_today);

        listView = findViewById(R.id.lv_customer_list);

        tvToday.setText(mMonthDay.format(calToday.getTime()) + "\n오늘의 고객 리스트");

        CustomerAdapter= new CustomerAdapter();

        listView.setAdapter(CustomerAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final CaCustomer customer= alCustomer.get(position);

                Intent intent = new Intent(ActivityDriverHome.this, ActivityDriverSub.class);
                intent.putExtra("customer", customer.nReserveId);
                startActivity(intent);


            }
        });


    }

    @Override
    protected  void onResume() {

        super.onResume();
        CaApplication.m_Engine.GetDriverHomeInfo(CaApplication.m_Info.strId,this,this);

    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder dlg = new AlertDialog.Builder(ActivityDriverHome.this);
        dlg.setTitle("경고"); //제목
        dlg.setMessage("앱을 종료하시겠습니까?"); // 메시지
        dlg.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // kill app
                finishAffinity();
                System.runFinalization();
                System.exit(0);
            }
        });

        dlg.show();

    }

    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_menu: {
                m_Drawer.openDrawer();
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
            case CaEngine.GET_DRIVE_HOME_INFO: {

                try {
                    Log.i("LOGIN", "GetDriverHomeInfo Called...");
                    JSONObject jo = Result.object;

                    if(jo.getJSONArray("list").length() == 0){
                       tvRemain.setText("0");
                    }
                    else{
                        JSONArray ja = jo.getJSONArray("list");

                        CaApplication.m_Info.alCustomer.clear();

                        for(int i=0;i<ja.length();i++){
                            JSONObject joCustomer = ja.getJSONObject(i);
                            CaCustomer customer = new CaCustomer();

                            customer.nReserveId = joCustomer.getInt("reserve_id");
                            customer.dtReserveTime= CaApplication.m_Info.parseDate(joCustomer.getString("reserve_time"));
                            customer.strLocation = joCustomer.getString("location");
                            customer.strCarModel = joCustomer.getString("car_model_name");
                            customer.strCarNumber = joCustomer.getString("car_number");
                            customer.strNotice = joCustomer.getString("notice");
                            customer.strName = joCustomer.getString("customer_name");
                            customer.strPhone = joCustomer.getString("customer_phone");

                            CaApplication.m_Info.alCustomer.add(customer);
                            Log.i("DriverHome", "customer name: " +customer.strName);
                        }
                        tvRemain.setText(String.valueOf(CaApplication.m_Info.alCustomer.size()));
                        alCustomer = CaApplication.m_Info.alCustomer;
                        CustomerAdapter.notifyDataSetChanged();
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