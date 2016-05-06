package com.changeworld.tim.colorcircleclock.Data;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created by tim on 2016/4/28.
 */
public class BatteryTool {

    private Context context;
    private IntentFilter ifilter;
    private float batteryPct = 0;

    private final int CYCLE = 30;
    private int count = 0;

    public BatteryTool(Context context){
        this.context = context;
        ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
    }

    public float getBatteryPct(){
        if(batteryPct == 0 || count == 0){
            getBatteryAgain();
        }

        count++;
        if(count > CYCLE){
            count = 0;
        }

        return batteryPct;
    }

    public float getBatteryAgain(){
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        batteryPct = level / (float)scale;
        return batteryPct;
    }
}
