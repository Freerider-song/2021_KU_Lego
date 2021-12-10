package com.example.lego.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lego.CaApplication;
import com.example.lego.CaEngine;
import com.example.lego.CaPref;
import com.example.lego.EgDialogLogout;
import com.example.lego.R;
import com.example.lego.StringUtil;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class BaseActivity extends AppCompatActivity {

    public Drawer m_Drawer;

    private EgDialogLogout m_dlgLogout;

    private static Typeface mTypeface = null;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (mTypeface == null) {
            // font_open_sans_regular or font_nanumgothic
            mTypeface = Typeface.createFromAsset(getAssets(), "fonts/nanumsquareroundr.ttf"); // 외부폰트 사용

            //mTypeface = Typeface.MONOSPACE; // 내장 폰트 사용
        }
        setGlobalFont(getWindow().getDecorView());
        // 또는
        // View view = findViewById(android.R.id.content);
        // setGlobalFont(view);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getWindow().getDecorView();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(view!=null){
                view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                getWindow().setStatusBarColor(Color.parseColor("#F8F8F8"));
            }
        }

    }

    private void setGlobalFont(View view) {
        if (view != null) {
            if(view instanceof ViewGroup){
                ViewGroup vg = (ViewGroup)view;
                int vgCnt = vg.getChildCount();
                for(int i=0; i < vgCnt; i++){
                    View v = vg.getChildAt(i);
                    if(v instanceof TextView){
                        ((TextView) v).setTypeface(mTypeface);
                    }
                    setGlobalFont(v);
                }
            }
        }
    }

    public Date parseDate(String str) {
        Calendar cal=new GregorianCalendar(1970, 1, 1, 0, 0, 0);

        Date dt;

        try {
            dt = CaApplication.m_Info.m_dfStd.parse(str);
        }
        catch (ParseException e) {
            // e.printStackTrace();
            dt=cal.getTime();
        }

        return dt;
    }

    public void prepareDrawer() {

        AccountHeaderBuilder ahb = new AccountHeaderBuilder();

        ahb.withActivity(this);
        ahb.withSelectionListEnabled(false);
        ahb.addProfiles(
                new ProfileDrawerItem().withIcon(getResources().getDrawable(R.drawable.gg_1)));

        AccountHeader ah=ahb.build();

        PrimaryDrawerItem itemHome = new PrimaryDrawerItem();
        itemHome.withIdentifier(CaEngine.MENU_HOME);
        itemHome.withName("홈");
        itemHome.withTypeface(mTypeface);
        itemHome.withTextColor(Color.rgb(0, 0, 0));
        itemHome.withSelectable(false);
        //itemUsage.withIcon(R.drawable.menu_usage);
        //itemUsage.withDescription("우리집 전기 사용");
        itemHome.withDescriptionTextColor(Color.rgb(0, 0, 0));

        PrimaryDrawerItem itemSubstitute = new PrimaryDrawerItem();
        itemSubstitute.withIdentifier(CaEngine.MENU_SUBSTITUTE);
        itemSubstitute.withName("대리충전 서비스");
        itemSubstitute.withTextColor(Color.rgb(0, 0, 0));
        itemSubstitute.withSelectable(false);
        itemSubstitute.withTypeface(mTypeface);
        //temAlarm.withIcon(R.drawable.menu_alarm);

        PrimaryDrawerItem itemMypage = new PrimaryDrawerItem();
        itemMypage.withIdentifier(CaEngine.MENU_MYPAGE);
        itemMypage.withName("내정보");
        itemMypage.withTextColor(Color.rgb(0, 0, 0));
        itemMypage.withSelectable(false);
        itemMypage.withTypeface(mTypeface);
        //itemSiteState.withIcon(R.drawable.menu_site_state);

        PrimaryDrawerItem itemPay = new PrimaryDrawerItem();
        itemPay.withIdentifier(CaEngine.MENU_PAY);
        itemPay.withName("결제 관리");
        itemPay.withTextColor(Color.rgb(0, 0, 0));
        itemPay.withSelectable(false);
        itemPay.withTypeface(mTypeface);
        //itemNotice.withIcon(R.drawable.menu_notice);

        PrimaryDrawerItem itemSetting = new PrimaryDrawerItem();
        itemSetting.withIdentifier(CaEngine.MENU_SETTING);
        itemSetting.withName("설정");
        itemSetting.withTextColor(Color.rgb(0, 0, 0));
        itemSetting.withSelectable(false);
        itemSetting.withTypeface(mTypeface);

        PrimaryDrawerItem itemLogout = new PrimaryDrawerItem();
        itemLogout.withIdentifier(CaEngine.MENU_LOGOUT);
        itemLogout.withName("로그아웃");
        itemLogout.withTextColor(Color.rgb(0, 0, 0));
        itemLogout.withSelectable(false);
        itemLogout.withTypeface(mTypeface);

        SecondaryDrawerItem itemChange = new SecondaryDrawerItem();
        itemChange.withIdentifier(1001);
        itemChange.withName("기사 화면으로 전환");
        itemChange.withTextColor(Color.rgb(177, 177, 177));
        itemChange.withSelectable(false);
        itemChange.withTypeface(mTypeface);

        final BaseActivity This=this;
        final Context Ctx=getApplicationContext();

        m_Drawer = new DrawerBuilder()
                .withActivity(this).withSliderBackgroundColor(getResources().getColor(R.color.lego_side_menu)).withAccountHeader(ah)
                .addDrawerItems(itemHome, itemSubstitute,
                        itemMypage,
                        itemPay, itemSetting, itemLogout, itemChange)
                .withDrawerWidthDp(180)
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        int nId=(int)drawerItem.getIdentifier();
                        Log.i("Drawer", "position="+position+", id="+nId);

                        switch (nId) {
                            case CaEngine.MENU_HOME: {
                                Intent it = new Intent(This, ActivityHome.class);
                                startActivity(it);
                            }
                            break;

                            case CaEngine.MENU_SUBSTITUTE: {
                                Intent it = new Intent(This, ActivitySubstitute.class);
                                startActivity(it);
                            }
                            break;

                            case CaEngine.MENU_MYPAGE: {
                                Intent it = new Intent(This, ActivityMyPage.class);
                                startActivity(it);
                            }
                            break;

                            case CaEngine.MENU_PAY: {
                                //Intent it = new Intent(This, ActivityUsageYearly.class);
                                //startActivity(it);
                            }
                            break;

                            case CaEngine.MENU_SETTING: {
                                //Intent it = new Intent(This, ActivitySetting.class);
                                //startActivity(it);
                            }
                            break;

                            case 1001: {
                                CaApplication.m_Info.strId = "driver";
                                Intent it = new Intent(This, ActivityDriverHome.class);
                                startActivity(it);
                            }

                            case CaEngine.MENU_LOGOUT: {

                                View.OnClickListener LsnConfirmYes=new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.i("BaseActivity", "Yes button clicked...");
                                        m_dlgLogout.dismiss();

                                        CaPref pref = new CaPref(Ctx);

                                        pref.setValue(CaPref.PREF_MEMBER_ID, "");
                                        pref.setValue(CaPref.PREF_PASSWORD, "");

                                        final Class Clazz=ActivityLogin.class;

                                        Intent nextIntent = new Intent(Ctx, Clazz);
                                        startActivity(nextIntent);
                                    }
                                };

                                View.OnClickListener LsnConfirmNo=new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Log.i("BaseActivity", "No button clicked...");
                                        m_dlgLogout.dismiss();
                                    }
                                };

                                m_dlgLogout=new EgDialogLogout(This, LsnConfirmYes, LsnConfirmNo);
                                m_dlgLogout.setOnKeyListener(new DialogInterface.OnKeyListener() {
                                    @Override
                                    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                                        if (keyCode== KeyEvent.KEYCODE_BACK) {
                                            dialog.dismiss();
                                            return true;
                                        }
                                        return false;
                                    }
                                });

                                m_dlgLogout.show();
                            }
                            break;

                            default: {
                                Log.d("Drawer", "Unknon menu with id="+nId);
                            }
                            break;
                        }
                        m_Drawer.setSelection(-1);
                        m_Drawer.closeDrawer();

                        return true;
                    }
                }).withSelectedItem(-1).withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        Log.d("Drawer", "onDrawerOpened called...");

                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        Log.d("Drawer", "onDrawerClosed called...");
                    }

                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        if (m_Drawer.isDrawerOpen()) Log.d("Drawer", "onDrawerSlide called...in Drawer open state");
                        else Log.d("Drawer", "onDrawerSlide called...in Drawer close state");
                    }
                })
                .build();

    }
}