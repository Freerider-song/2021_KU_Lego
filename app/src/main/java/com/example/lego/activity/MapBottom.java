package com.example.lego.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.lego.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapBottom#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapBottom extends BottomSheetDialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MapBottom() {
        // Required empty public constructor
    }


    public static MapBottom newInstance(String param1, String param2) {
        MapBottom fragment = new MapBottom();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_map_bottom, container, false);
        Button btnReserve = rootView.findViewById(R.id.btn_reserve);
        mParam1 = getArguments().getString("station_name");
        mParam2 = getArguments().getString("charger_num");

        TextView tvStationName = rootView.findViewById(R.id.tv_station_name);
        TextView tvStationCharger = rootView.findViewById(R.id.tv_station_charger);

        tvStationCharger.setText(mParam2);
        tvStationName.setText(mParam1);


        btnReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("MapBottom", "move to home");
               // CaApplication.m_Info.strStationName = mParam1;
                //CaApplication.m_Info.nFastCharger = Integer.parseInt(mParam2.substring(3,4));
               // CaApplication.m_Info.nSlowCharger = Integer.parseInt(mParam2.substring(8,9));
                //CaApplication.m_Info.nV2gCharger = Integer.parseInt(mParam2.substring(14,15));
                Intent it = new Intent(getActivity(), ActivityReserveCharger.class);
                startActivity(it);
            }
        });


        return rootView;
    }

}