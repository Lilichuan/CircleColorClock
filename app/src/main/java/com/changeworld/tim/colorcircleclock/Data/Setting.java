package com.changeworld.tim.colorcircleclock.Data;

import android.content.Context;
import android.content.SharedPreferences;

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

    public static final int CIRCLE_SPLIT_MAX = 180;
    public static final int CIRCLE_SPLIT_MINI = 3;


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
}
