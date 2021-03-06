package com.changeworld.tim.colorcircleclock.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.changeworld.tim.colorcircleclock.Data.BatteryTool;
import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.R;


public class BigCircleView extends View{

    private int strokeW;
    private Paint paint, shadowPaint;
    private boolean pause = false;
    private BatteryTool batteryTool;
    private RectF rectF;

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

        if(setting.isShowBattery()){
            batteryTool = new BatteryTool(context);
            shadowPaint = new Paint(paint);
            shadowPaint.setColor(Color.parseColor(setting.getFadeColor()));
        }


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#00000000"));
        if(!pause){
            int radius = canvas.getWidth() / 2;

            if(batteryTool != null){
                if(rectF == null){
                    rectF = new RectF(strokeW, strokeW, canvas.getWidth() - strokeW, canvas.getHeight() - strokeW);
                }

                canvas.drawCircle(radius ,radius , radius - strokeW , shadowPaint);
                canvas.drawArc(rectF, -90 , (360*batteryTool.getBatteryPct()) ,false, paint );
            }else {
                canvas.drawCircle(radius ,radius , radius - strokeW , paint);
            }

        }

        canvas.save();
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }
}
