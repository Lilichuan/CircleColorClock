package com.changeworld.tim.colorcircleclock.UI;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

import com.changeworld.tim.colorcircleclock.R;

/**
 * Created by tim on 2016/4/25.
 */
public class ErrorCodeDisplayer {

    private static final String TEXT_COLOR = "#bf1c24";
    private static final String BG_COLOR = "#8fbf1c24";

    private String[] errorText;
    private int ERROR_COUNT;
    private int countDown ;
    private static final int COUNT_MAX = 2;
    private String displayCode;
    private boolean show = false;
    private boolean isRad = false;

    public ErrorCodeDisplayer(Context context){
        errorText = context.getResources().getStringArray(R.array.error_code);
        ERROR_COUNT = errorText.length;
        reset();
    }

    private void reset(){
        countDown = COUNT_MAX;
    }

    private String getText(int sec){
        String s;
        if(isRad){
            s = "OPERATION SHAY";
        }else {
            int a = sec % ERROR_COUNT;
            s = errorText[a];
        }

        return s;
    }

    public void display(TextView textView, int sec){
        textView.setBackgroundColor(Color.parseColor(BG_COLOR));
        textView.setTextColor(Color.parseColor(TEXT_COLOR));
        if(COUNT_MAX == countDown){
            displayCode = getText(sec);
        }
        textView.setText(displayCode);
        countDown--;
        if(countDown < 0){
            setShow(false);
            reset();
        }

    }

    public void setShow(boolean show) {
        this.show = show;
    }

    public boolean isShow() {
        return show;
    }

    public void setRad(boolean rad) {
        isRad = rad;
    }
}
