package com.example.lego;

import android.content.Context;
import android.util.Log;

public class CaEngine {

    public static final int CB_NONE = 0;

    //API 요청
    public static final int CB_CHECK_BLD_LOGIN = 1001;
    public static final int GET_CAR_COMPANY_INFO = 1002;
    public static final int GET_CAR_MODEL_INFO = 1003;
    public static final int SET_SIGN_UP_INFO= 1004;
    public static final int GET_STATION_INFO = 1005;
    public static final int GET_HOME_INFO = 1006;
    public static final int GET_CHARGE_INFO = 1007;
    public static final int STOP_CHARGE = 1008;
    public static final int GET_CHARGE_HISTORY = 1009;
    public static final int GET_CHARGE_RESULT = 1010;
    public static final int SET_RESERVE_INFO = 1011;
    public static final int SET_SERVICE_PAID = 1012;
    public static final int GET_FEE_INFO = 1013;


    public static final int AUTH_TYPE_UNKNOWN = 1000;
    public static final int AUTH_TYPE_SUBSCRIBE = 1001;
    public static final int AUTH_TYPE_CHANGE_PASSWORD = 1002;

    public static final int MENU_USAGE = 100;
    public static final int MENU_USAGE_DAILY = 101;
    public static final int MENU_USAGE_MONTHLY = 102;
    public static final int MENU_USAGE_YEARLY = 103;
    public static final int MENU_HOME = 200;
    public static final int MENU_SAVING = 300;
    public static final int MENU_ALARM = 500;
    public static final int MENU_NOTICE = 600;
    public static final int MENU_SETTING = 800;
    public static final int MENU_LOGOUT = 900;

    public static final String[] NO_CMD_ARGS = new String[]{};

    public static final int ALARM_TYPE_UNKNOWN = 0;
    public static final int ALARM_TEST = 2;
    public static final int ALARM_NEW_NOTICE = 3001; // 새 공지사항발생
    public static final int ALARM_PLAN_ELEM_BEGIN = 3002; //절감항목 시작
    public static final int ALARM_PLAN_ELEM_END = 3003; // 절감항목종료
    public static final int ALARM_SAVE_ACT_MISSED = 3004; // 미시행절감조치 있음 알림
    public static final int ALARM_THIS_MONTH_USAGE_AT = 3005; //정해진 시간에 사용량과 사용요금 알림
    public static final int ALARM_THIS_MONTH_KWH_OVER = 3006; //설정한 사용량 임계치 초과 알림
    public static final int ALARM_THIS_MONTH_WON_OVER = 3007; //설정한 사용요금 임계치 초과 알림
    public static final int ALARM_METER_KWH_OVER_SAVE_REF = 3008; //계측기별 사용량이 절감기준 사용량 초과
    public static final int ALARM_METER_KWH_OVER_SAVE_PLAN = 3009;// 계측기별 사용량이 절감목표 사용량 초과

    public CaEngine() {

    }

    public final CaResult executeCommand(CaArg Arg, final int nCallMethod, final boolean bSync, final boolean bShowWaitDialog, Context Ctx, IaResultHandler ResultHandler) {

        CaTask Task = new CaTask(nCallMethod, bShowWaitDialog, Ctx, ResultHandler);
        Task = (CaTask) Task.execute(Arg);

        CaResult Result = null;

        if (bSync) {
            try {
                Result = Task.get();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return Result;
    }

    public void CheckLogin(final String Id, final String Password, Context Ctx, IaResultHandler ResultHandler){
        Log.i("ENGINE", "Id=" + Id + ", Password=" + Password);

        CaArg Arg = new CaArg("CheckLogin", NO_CMD_ARGS, null);
        Arg.addArg("Id", Id);
        Arg.addArg("Password", Password);



        executeCommand(Arg, CB_CHECK_BLD_LOGIN, false, true, Ctx, ResultHandler);
    }

    public void GetCarCompanyInfo(Context Ctx, IaResultHandler ResultHandler){
        Log.i("ENGINE", "GetCarCompanyInfo called");

        CaArg Arg = new CaArg("GetCarCompanyInfo", NO_CMD_ARGS, null);

        executeCommand(Arg, GET_CAR_COMPANY_INFO, false, true, Ctx, ResultHandler);
    }

    public void GetCarModelInfo(String CarCompany, Context Ctx, IaResultHandler ResultHandler){
        Log.i("ENGINE", "GetCarModelInfo called");

        CaArg Arg = new CaArg("GetCarModelInfo", NO_CMD_ARGS, null);
        Arg.addArg("Car_company", CarCompany);

        executeCommand(Arg, GET_CAR_MODEL_INFO, false, true, Ctx, ResultHandler);
    }

    public void SetSignUpInfo(String Name, String Id,String Password,int CarModelId,Context Ctx, IaResultHandler ResultHandler){
        Log.i("ENGINE", "SetSignUpInfo called");

        CaArg Arg = new CaArg("SetSignUpInfo", NO_CMD_ARGS, null);
        Arg.addArg("Car_model", CarModelId);
        Arg.addArg("Name", Name);
        Arg.addArg("Id", Id);
        Arg.addArg("Password", Password);

        executeCommand(Arg, SET_SIGN_UP_INFO, false, true, Ctx, ResultHandler);
    }

    public void GetStationInfo(Context Ctx, IaResultHandler ResultHandler){
        Log.i("ENGINE", "GetStationInfo called");

        CaArg Arg = new CaArg("GetStationInfo", NO_CMD_ARGS, null);

        executeCommand(Arg, GET_STATION_INFO, false, true, Ctx, ResultHandler);
    }

    public void SetReserveInfo(String Id, int StationId, int ReserveType, String StartTime, String FinishTime, int MinimumCap, int CurrentCap, Context Ctx, IaResultHandler ResultHandler){
        Log.i("ENGINE", "GetStationInfo called");

        CaArg Arg = new CaArg("SetReserveInfo", NO_CMD_ARGS, null);
        Arg.addArg("Id", Id);
        Arg.addArg("ReserveType", ReserveType);
        Arg.addArg("StartTime", StartTime);
        Arg.addArg("FinishTime", FinishTime);
        Arg.addArg("MinimumCap", MinimumCap);
        Arg.addArg("CurrentCap", CurrentCap);
        Arg.addArg("StationId", StationId);

        executeCommand(Arg, SET_RESERVE_INFO, false, true, Ctx, ResultHandler);
    }

    public void GetHomeInfo(String Id, Context Ctx, IaResultHandler ResultHandler){
        Log.i("ENGINE", "GetHomeInfo called");

        CaArg Arg = new CaArg("GetHomeInfo", NO_CMD_ARGS, null);
        Arg.addArg("Id", Id);

        executeCommand(Arg, GET_HOME_INFO, false, true, Ctx, ResultHandler);
    }

    public void GetChargeInfo(String ServiceReservationId, Context Ctx, IaResultHandler ResultHandler){
        Log.i("ENGINE", "GetChargeInfo called");

        CaArg Arg = new CaArg("GetChargeInfo", NO_CMD_ARGS, null);
        Arg.addArg("Service_reservation_Id", ServiceReservationId);

        executeCommand(Arg, GET_CHARGE_INFO, false, true, Ctx, ResultHandler);
    }

    public void StopCharge(String ServiceReservationId, Context Ctx, IaResultHandler ResultHandler){
        Log.i("ENGINE", "StopCharge called");

        CaArg Arg = new CaArg("StopCharge", NO_CMD_ARGS, null);
        Arg.addArg("Service_reservation_id", ServiceReservationId);

        executeCommand(Arg, STOP_CHARGE, false, true, Ctx, ResultHandler);
    }

    public void GetChargeHistory(String Id, Context Ctx, IaResultHandler ResultHandler){
        Log.i("ENGINE", "GetChargeHistory called");

        CaArg Arg = new CaArg("GetChargeHistory", NO_CMD_ARGS, null);
        Arg.addArg("Id", Id);

        executeCommand(Arg, GET_CHARGE_HISTORY, false, true, Ctx, ResultHandler);
    }

    public void GetChargeResult(String ServiceReservationId, Context Ctx, IaResultHandler ResultHandler){
        Log.i("ENGINE", "GetChargeResult called");

        CaArg Arg = new CaArg("GetChargeResult", NO_CMD_ARGS, null);
        Arg.addArg("Service_reservation_id", ServiceReservationId);

        executeCommand(Arg, GET_CHARGE_RESULT, false, true, Ctx, ResultHandler);
    }

    public void SetServicePaid(String ServiceReservationId, Context Ctx, IaResultHandler ResultHandler){
        Log.i("ENGINE", "SetServicePaid called");

        CaArg Arg = new CaArg("SetServicePaid", NO_CMD_ARGS, null);
        Arg.addArg("Service_reservation_id", ServiceReservationId);

        executeCommand(Arg, SET_SERVICE_PAID, false, true, Ctx, ResultHandler);
    }

    public void GetFeeInfo(Context Ctx, IaResultHandler ResultHandler){
        Log.i("ENGINE", "GetFeeInfo called");

        CaArg Arg = new CaArg("GetFeeInfo", NO_CMD_ARGS, null);

        executeCommand(Arg, GET_FEE_INFO, false, true, Ctx, ResultHandler);
    }




}
