package com.changeworld.tim.colorcircleclock.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.R;


public class BigCircleView extends View{

    private int strokeW;
    private Paint paint, shadowPaint;
    private boolean pause = false;

    public BigCircleView(Context context) {
        super(context);
        init(context);
    }

    public BigCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BigCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        Setting setting = new Setting(context);
        strokeW = TypedValue.COMPLEX_UNIT_DIP * context.getResources().getInteger(R.integer.main_circle_stroke_w);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(setting.getColor()));
        paint.setStrokeWidth(strokeW);
        paint.setStyle(Paint.Style.STROKE);
//        if(setting.isShowMainCircleShadow()){
//            shadowPaint = new Paint();
//            shadowPaint.setAntiAlias(true);
//            shadowPaint.setColor(Color.parseColor(setting.getFadeColor()));
//            shadowPaint.setStrokeWidth(strokeW);
//            shadowPaint.setStyle(Paint.Style.STROKE);
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#00000000"));
        if(!pause){
            int radius = canvas.getWidth() / 2;

//            if(shadowPaint != null){
//                canvas.drawCircle(radius ,radius , radius - (strokeW / 2), shadowPaint);
//            }

            canvas.drawCircle(radius ,radius , radius - strokeW , paint);
        }

        canvas.save();
    }

    public boolean isPause(){
        return pause;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
