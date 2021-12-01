package com.example.lego.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lego.CaApplication;
import com.example.lego.CaEngine;
import com.example.lego.CaResult;
import com.example.lego.EgMonthPicker;
import com.example.lego.IaResultHandler;
import com.example.lego.R;
import com.example.lego.model.CaHistory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.lego.CaApplication.m_Context;

public class ActivityChargeHistory extends AppCompatActivity implements IaResultHandler {

    private ChargeHistoryAdapter m_ChargeHistoryAdapter;
    public Button btnMonth;
    SimpleDateFormat mFormat = new SimpleDateFormat("MM");
    SimpleDateFormat mYearMonth = new SimpleDateFormat("yyyy-MM");
    SimpleDateFormat mMonthDay = new SimpleDateFormat("MM-dd");

    String strYearMonth;

    TextView tvEmpty, tvSavingFee;
    ListView listView;

    ArrayList<CaHistory> alHistory = new ArrayList<>();

    private EgMonthPicker m_dlgMonthPicker;
    public int Month;

    public void timeSetting() {

        btnMonth = (Button) findViewById(R.id.btn_month_picker);


        Calendar calToday = Calendar.getInstance();
        String m_dtToday = mFormat.format(calToday.getTime());
        strYearMonth = mYearMonth.format(calToday.getTime());
        btnMonth.setText(m_dtToday+"월");

    }


    private class ChargeHistoryViewHolder {
        public TextView tvChargeDate;
        public TextView tvChargeType;
        public TextView tvEarningFee;
    }

    private class ChargeHistoryAdapter extends BaseAdapter {

        Typeface tf;

        public ChargeHistoryAdapter() {
            super();


        }

        @Override
        public int getCount() {
            Log.i("ChargeHistory" , "alHistory size in listview" + alHistory.size());
            //return plan.m_alAct.size();
            return alHistory.size();
        }

        @Override
        public Object getItem(int position) {
            //return plan.m_alAct.get(position);
            return alHistory.get(position);

        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ChargeHistoryViewHolder holder;
            if (convertView == null) {
                holder = new ChargeHistoryViewHolder();

                LayoutInflater inflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.list_item_history, null);

                holder.tvChargeDate = convertView.findViewById(R.id.tv_charge_date);
                holder.tvChargeType = convertView.findViewById(R.id.tv_charge_type);
                holder.tvEarningFee = convertView.findViewById(R.id.tv_earning_fee);

                convertView.setTag(holder);
            }
            else {
                holder = (ChargeHistoryViewHolder) convertView.getTag();
            }

            final CaHistory history= alHistory.get(position);
            tf = Typeface.createFromAsset(getAssets(), "fonts/nanumsquareroundr.ttf");
            holder.tvChargeDate.setTypeface(tf);
            holder.tvEarningFee.setTypeface(tf);
            holder.tvChargeType.setTypeface(tf);

            holder.tvChargeDate.setText(mMonthDay.format(history.dtReserve));
            if(history.nReserveType == 1){
                holder.tvChargeType.setText("충전");
            }
            else if(history.nReserveType == 2){
                holder.tvChargeType.setText("방전");
            }
            else if(history.nReserveType == 3){
                holder.tvChargeType.setText("충·방전");
            }
            holder.tvEarningFee.setText(history.nFee +"원");

            return convertView;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_charge_history);

        timeSetting();
        getChargeHistory();

        tvEmpty = findViewById(R.id.tv_empty);
        tvSavingFee = findViewById(R.id.tv_saving_fee);
        listView = findViewById(R.id.lv_charge_history);

        tvSavingFee.setText("+"+CaApplication.m_Info.m_dfWon.format(alHistory.size() * 1562) + "원을\n아꼈어요!");

        m_ChargeHistoryAdapter= new ChargeHistoryAdapter();

        listView.setAdapter(m_ChargeHistoryAdapter);
    }
    public void getChargeHistory() {
        CaApplication.m_Engine.GetChargeHistory(CaApplication.m_Info.strId, this, this );
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_back: {
                finish();
            }
            break;

            case R.id.btn_month_picker: {

                View.OnClickListener LsnConfirmYes = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        m_dlgMonthPicker.dismiss();
                        int nMonth = m_dlgMonthPicker.m_npMonth.getValue();
                        Log.i("MonthPicker", "month=" + nMonth);
                        Month = nMonth;

                        String strMonth = Integer.toString(nMonth);
                        if (nMonth <= 9) strMonth = "0" + strMonth;

                        btnMonth = (Button) findViewById(R.id.btn_month_picker);
                        btnMonth.setText(strMonth+"월");

                        strYearMonth = "2021-" + strMonth;

                        getChargeHistory();

                    }
                };

                View.OnClickListener LsnConfirmNo = new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("ActivityCandidate", "No button clicked...");
                        m_dlgMonthPicker.dismiss();
                    }
                };

                m_dlgMonthPicker = new EgMonthPicker(this, "조회할 날짜를 선택하세요", LsnConfirmYes, LsnConfirmNo);
                m_dlgMonthPicker.show();
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
            case CaEngine.GET_CHARGE_HISTORY: {

                try {
                    JSONObject jo = Result.object;
                    if(jo.getJSONArray("list_history").length() == 0){
                        tvEmpty.setVisibility(View.VISIBLE);
                    }
                    else{
                        tvEmpty.setVisibility(View.INVISIBLE);
                        JSONArray ja = jo.getJSONArray("list_history");

                        alHistory.clear();
                        Log.i("ChargeHistory" , "ja size" + ja.length());

                        for(int i=0;i<ja.length();i++){
                            JSONObject joHistory = ja.getJSONObject(i);
                            CaHistory history = new CaHistory();

                            history.dtReserve = CaApplication.m_Info.parseDate(joHistory.getString("reserve_time"));
                            history.nReserveType = joHistory.getInt("reserve_type");
                            history.nFee = joHistory.getInt("expected_fee");

                            if(mYearMonth.format(history.dtReserve).equals(strYearMonth)){
                                alHistory.add(history);
                            }


                        }
                        Log.i("ChargeHistory" , "alHistory size" + alHistory.size());

                        tvSavingFee.setText("+"+CaApplication.m_Info.m_dfWon.format(alHistory.size() * 1562) + "원을\n아꼈어요!");
                        m_ChargeHistoryAdapter.notifyDataSetChanged();
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