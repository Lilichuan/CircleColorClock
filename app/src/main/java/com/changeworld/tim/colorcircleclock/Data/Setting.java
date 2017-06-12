package com.changeworld.tim.colorcircleclock.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;


public class Setting {

    private static final String SP_NAME = "set";

    private SharedPreferences sp;

    private static final String KEY_SHOW_CLOCK = "showClock";
    private static final String KEY_DISPLAY_2_LAYER = "secondLayer";
    private static final String KEY_2_LAYER_COUNT = "secondLayerCount";
    private static final String KEY_COLOR = "color";
    private static final String KEY_SHOW_BATTERY = "showBattery";

    public static final int CIRCLE_SPLIT_MAX = 180;
    public static final int CIRCLE_SPLIT_MINI = 3;

    public static final String COLOR_ORANGE = "#ff6a13";
    public static final String COLOR_GREEN = "#91E061";
    public static final String COLOR_PURPLE = "#ba68c8";
    public static final String COLOR_BLUE = "#4c97ed";
    public static final String COLOR_RAD = "#b20000";
    public static final String COLOR_BARNEY = "#B00140";
    public static final String COLOR_YELLOW = "#e0af54";



    public Setting(Context context){
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    /*
    *
    * Display clock setting
    * 是否呈現時鐘數字
    * */
    public void setShowClock(boolean show){
        sp.edit().putBoolean(KEY_SHOW_CLOCK, show).apply();
    }

    public boolean isShowClock(){
        return sp.getBoolean(KEY_SHOW_CLOCK, true);
    }


    /*
    *
    * Display two circles setting
    * 是否呈現第二圈內圈
    * */
    public void setShow2Layer(boolean show){
        sp.edit().putBoolean(KEY_DISPLAY_2_LAYER, show).apply();
    }

    public boolean getShow2Layer(){
        return sp.getBoolean(KEY_DISPLAY_2_LAYER, false);
    }

    /*
    *
    * 2nd circle split count setting
    * 內圈第二圈分裂多少段
    * */
    public void set2ndLayerSplit(int count){
        if(count < 2){
            return;
        }
        sp.edit().putInt(KEY_2_LAYER_COUNT, count).apply();
    }

    public int get2ndLayerSplit(){
        return sp.getInt(KEY_2_LAYER_COUNT, CIRCLE_SPLIT_MINI);
    }

    /*
    *
    * Color
    * 設定主題顏色
    *
    * */
    public void setColor(String colorCode){
        if(TextUtils.isEmpty(colorCode)){
            return;
        }
        sp.edit().putString(KEY_COLOR,colorCode).apply();
    }

    public String getColor(){
        return sp.getString(KEY_COLOR, COLOR_ORANGE);
    }

    //比主題顏色稍微淡一點的顏色
    public String getFadeColor(){
        return getFadeColor(getColor());
    }

    public static String getFadeColor(String selectColor){
        String color;

        switch (selectColor){
            case COLOR_GREEN:
                color = "#57863A";
                break;
            case COLOR_ORANGE:
                color = "#6b2c07";
                break;
            case COLOR_PURPLE:
                color = "#371F3C";
                break;
            case COLOR_BLUE:
                color = "#264B76";
                break;
            case COLOR_RAD :
                color = "#470000";
                break;
            case COLOR_BARNEY:
                color = "#340013";
                break;
            case Setting.COLOR_YELLOW:
                color = "#9C7A3A";
                break;
            default:
                color = "#ffffff";
        }

        return color;
    }

    /*
    *
    * 是否呈現電池電量
    * */
    public void setShowBattery(boolean showBattery){
        sp.edit().putBoolean(KEY_SHOW_BATTERY, showBattery).apply();
    }

    public boolean isShowBattery(){
        return sp.getBoolean(KEY_SHOW_BATTERY, false);
    }

    public String[] getColorList(){
        String[] l = new String[7];
        l[0] = COLOR_GREEN;
        l[1] = COLOR_PURPLE;
        l[2] = COLOR_ORANGE;
        l[3] = COLOR_BLUE;
        l[4] = COLOR_RAD;
        l[5] = COLOR_BARNEY;
        l[6] = COLOR_YELLOW;
        return l;
    }
}
