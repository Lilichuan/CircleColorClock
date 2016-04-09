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


    public Setting(Context context){
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }

    public void setShowClock(boolean show){
        sharedPreferences.edit().putBoolean(KEY_SHOW_CLOCK, show).apply();
    }

    public boolean isShowClock(){
        return sharedPreferences.getBoolean(KEY_SHOW_CLOCK, true);
    }
}
