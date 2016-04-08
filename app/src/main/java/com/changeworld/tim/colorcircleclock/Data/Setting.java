package com.changeworld.tim.colorcircleclock.Data;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by tim on 2016/4/8.
 */
public class Setting {

    private static final String SP_NAME = "set";

    private SharedPreferences sharedPreferences;

    public Setting(Context context){
        sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
    }
}
