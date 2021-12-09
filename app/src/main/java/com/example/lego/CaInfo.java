package com.example.lego;


import com.example.lego.model.CaCustomer;
import com.example.lego.model.CaStation;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class CaInfo {

    public static final int DEFAULT_REQUEST_NOTICE_COUNT = 10;

    public SimpleDateFormat m_dfStd=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public SimpleDateFormat m_dfyyyyMMddhhmmStd=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public SimpleDateFormat m_dfyyyyMMddhhmm=new SimpleDateFormat("yyyyMMddHHmm");
    public SimpleDateFormat m_dfyyyyMMddhhmmss=new SimpleDateFormat("yyyyMMddHHmmss");
    public SimpleDateFormat m_dfyyyyMMdd=new SimpleDateFormat("yyyyMMdd");
    public SimpleDateFormat m_dfyyyyMMddhhmm_ampm=new SimpleDateFormat("yyyy-MM-dd hh:mm a");
    public SimpleDateFormat m_dfAPI = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    public DecimalFormat m_dfKwh = new DecimalFormat("0.#"); // 12345.7
    public DecimalFormat m_dfPercent = new DecimalFormat("0.##"); // 12345.7
    public DecimalFormat m_dfWon = new DecimalFormat("#,##0"); // 87,654


    public int m_nResultCode =0;
    public int m_nSeqAdmin=0;
    public int m_nTeamType=0;

    public String m_strAdminName="";
    public String m_strAdminPhone="";
    public int m_nUnreadNoticeCount=0;
    public int m_nUnreadAlarmCount=0;
    public Date m_dtLastLogin=null;
    public Date m_dtCreated=null;
    public Date m_dtModified=null;
    public Date m_dtChangePassword =null;



    public int nPoint = 0;

    //
    // GetSaveResult
    public int m_nTotalSaveActCount = 0;
    public int m_nTotalSaveActWithHistoryCount=0;
    public double m_dAvgKwhForAllMeter=0.0;
    public double m_dAvgWonForAllMeter=0.0;

    public String strId="";
    public String strPassword="";
    public String strName = "";
    public String strPhone = "";

    public String strCarCompany = "";
    public String strCarModel = "";
    public String strCarNumber = "";
    public int nModelId = 0;

    public int nTimeType = 0;
    public int Station0= 0;
    public int Station1 = 0;
    public int Station2= 0;

    public Date MinBatteryTime =null;
    public Date ChargeTime = null;
    public int PreferBattery = 20;

    public String strStationName = "";
    public int nStationId = 0;
    public int nFastCharger = 0;
    public int nSlowCharger = 0;
    public int nV2gCharger = 0;

    public double dReserveTimeRatio = 1.0;

    public String strChargerName = "";

    public int nCurrentCap = 45;
    public int nMinCapacity = 0;

    public double dBatteryCapacity = 0.0;
    public double dEfficiency = 0.1;

    public int nReserveType = 0; //1=충전, 2=방전, 3=충/방전
    public int bBeingCharged = -1;

    public Date dtStart = null;
    public Date dtEnd = null;

    public String nServiceReservation = "";
    public int bPaid = 0;
    public double dDx = 0.0;
    public double dDy = 0.0;
    public int nExpectedFee = 0;

    public boolean bCarRegistered = false;

    public ArrayList<CaStation> alStation = new ArrayList<>();
    public ArrayList<CaCustomer> alCustomer = new ArrayList<>();
    /*
    public int m_nAuthType=CaEngine.AUTH_TYPE_UNKNOWN;

    public CaProject m_caProject=new CaProject();

    public ArrayList<CaNotice> m_alNotice=new ArrayList<>();
    public ArrayList<CaSafety> m_alSafety=new ArrayList<>();
    public ArrayList<CaProject> m_alProject=new ArrayList<>();
    public ArrayList<CaMms> m_alMms=new ArrayList<>();
    public ArrayList<CaReport> m_alReport=new ArrayList<>();
    public ArrayList<CaSurvey> m_alSurvey=new ArrayList<>();
    public ArrayList<CaJobCode> m_alJobCode=new ArrayList<>();

     */

    public int m_nAuthType = CaEngine.AUTH_TYPE_UNKNOWN;

   /*
    public String getNoticeReadListString() {
        String strResult="";

        for (CaNotice notice : m_alNotice) {
            if (notice.m_bReadStateChanged) {
                strResult = (strResult + Integer.toString(notice.m_nSeqNotice)+",");
            }
        }

        if (strResult.isEmpty()) return strResult;

        strResult = strResult.substring(0, strResult.length() - 1);
        return strResult;
    }
*/


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


    public static String getDecoPhoneNumber(String src) {
        if (src == null) {
            return "";
        }
        if (src.length() == 8) {
            return src.replaceFirst("^([0-9]{4})([0-9]{4})$", "$1-$2");
        } else if (src.length() == 12) {
            return src.replaceFirst("(^[0-9]{4})([0-9]{4})([0-9]{4})$", "$1-$2-$3");
        }
        return src.replaceFirst("(^02|[0-9]{3})([0-9]{3,4})([0-9]{4})$", "$1-$2-$3");
    }
}
