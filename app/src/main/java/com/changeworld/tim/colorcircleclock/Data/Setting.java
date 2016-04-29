package com.changeworld.tim.colorcircleclock.Data;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.changeworld.tim.colorcircleclock.MainActivity;

/**
 * Created by tim on 2016/4/8.
 */
public class Setting {

    private static final String SP_NAME = "set";

    private SharedPreferences sharedPreferences;

    private static final String KEY_SHOW_CLOCK = "showClock";
    private static final String KEY_DISPLAY_2_LAYER = "secondLayer";
    private static final String KEY_2_LAYER_COUNT = "secondLayerCount";
    private static final String KEY_COLOR = "color";
    private static final String KEY_ERROR = "error";
    private static final String KEY_MAIN_SHADOW = "mainShadow";
    private static final String KEY_SHOW_BATTERY = "showBattery";

    public static final int CIRCLE_SPLIT_MAX = 180;
    public static final int CIRCLE_SPLIT_MINI = 3;

    public static final String COLOR_ORANGE = "#ff6a13";
    public static final String COLOR_GREEN = "#81c784";
    public static final String COLOR_PURPLE = "#ba68c8";
    public static final String COLOR_BLUE = "#81d4fa";
    public static final String COLOR_RAD = "#b20000";
    public static final String COLOR_BARNEY = "#B00140";



    public Setting(Context context){
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    /*
    *
    * Display clock setting
    * */
    public void setShowClock(boolean show){
        sharedPreferences.edit().putBoolean(KEY_SHOW_CLOCK, show).apply();
    }

    public boolean isShowClock(){
        return sharedPreferences.getBoolean(KEY_SHOW_CLOCK, true);
    }


    /*
    *
    * Display two circles setting
    * */
    public void setShow2Layer(boolean show){
        sharedPreferences.edit().putBoolean(KEY_DISPLAY_2_LAYER, show).apply();
    }

    public boolean getShow2Layer(){
        return sharedPreferences.getBoolean(KEY_DISPLAY_2_LAYER, false);
    }

    /*
    *
    * 2nd circle split count setting
    * */
    public void set2ndLayerSplit(int count){
        if(count < 2){
            return;
        }
        sharedPreferences.edit().putInt(KEY_2_LAYER_COUNT, count).apply();
    }

    public int get2ndLayerSplit(){
        return sharedPreferences.getInt(KEY_2_LAYER_COUNT, CIRCLE_SPLIT_MINI);
    }

    /*
    *
    * Color
    * */
    public void setColor(String colorCode){
        if(TextUtils.isEmpty(colorCode)){
            return;
        }
        sharedPreferences.edit().putString(KEY_COLOR,colorCode).apply();
    }

    public String getColor(){
        return sharedPreferences.getString(KEY_COLOR, COLOR_ORANGE);
    }

    public String getFadeColor(){
        String selectColor = getColor();
        String color;

        switch (selectColor){
            case Setting.COLOR_GREEN:
                color = "#19271a";
                break;
            case Setting.COLOR_ORANGE:
                color = "#6b2c07";
                break;
            case Setting.COLOR_PURPLE:
                color = "#371F3C";
                break;
            case Setting.COLOR_BLUE:
                color = "#263F4B";
                break;
            case Setting.COLOR_RAD :
                color = "#470000";
                break;
            case Setting.COLOR_BARNEY:
                color = "#340013";
                break;
            default:
                color = "#ffffff";
        }

        return color;
    }

    /*
    *
    * Main circle shadow setting
    * */
    public void setShowMainCircleShadow(boolean show){
        sharedPreferences.edit().putBoolean(KEY_MAIN_SHADOW, show).apply();
    }

    public boolean isShowMainCircleShadow(){
        return sharedPreferences.getBoolean(KEY_MAIN_SHADOW, false);
    }

    /*
    *
    * */
    public void setShowBattery(boolean showBattery){
        sharedPreferences.edit().putBoolean(KEY_SHOW_BATTERY, showBattery).apply();
    }

    public boolean isShowBattery(){
        return sharedPreferences.getBoolean(KEY_SHOW_BATTERY, false);
    }
}
