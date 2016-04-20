package com.changeworld.tim.colorcircleclock.UI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.changeworld.tim.colorcircleclock.Data.Setting;


public class DrawSecondTool {
    private Paint paint ,selectPaint;
    public RectF rectF;

    private int selectUnit = 0;

    private int arcCount;
    private float totalUnitDegree, unitDegree;


    public DrawSecondTool(int count, String selectColor){

        String color;

        switch (selectColor){
            case Setting.COLOR_GREEN:
                color = "#c8e6c9";
                break;
            case Setting.COLOR_ORANGE:
                color = "#b24a0d";
                break;
            case Setting.COLOR_PURPLE:
                color = "#e1bee7";
                break;
            case Setting.COLOR_BLUE:
                color = "#b3e5fc";
                break;
            default:
                color = "#ffffff";
        }

        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(color));
        paint.setStrokeWidth((float) 20.0);
        paint.setStyle(Paint.Style.STROKE);

        selectPaint = new Paint();
        selectPaint.setAntiAlias(true);
        selectPaint.setColor(Color.parseColor(selectColor));
        selectPaint.setStrokeWidth((float) 20.0);
        selectPaint.setStyle(Paint.Style.STROKE);

        arcCount = count;
        float SEPARATE_DEGREE = 3;
        totalUnitDegree = ((float) (360)) / count;
        if(totalUnitDegree <= SEPARATE_DEGREE){
            SEPARATE_DEGREE = totalUnitDegree / 2;
        }


        unitDegree = totalUnitDegree - SEPARATE_DEGREE;
    }

    public void drawCanvas(Canvas canvas){
        int PAINT_STROKE_W = 20 ;
        if(rectF == null){
            rectF = new RectF(PAINT_STROKE_W, PAINT_STROKE_W, canvas.getWidth() - PAINT_STROKE_W, canvas.getHeight() - PAINT_STROKE_W);
        }

        selectUnit++;
        if(selectUnit + 1 > arcCount){
            selectUnit = 0;
        }

        for (int i = 0 ; i < arcCount ;i++){

            if(i == selectUnit){
                canvas.drawArc(rectF, totalUnitDegree*i, unitDegree, false, selectPaint );
            }else {
                canvas.drawArc(rectF, totalUnitDegree*i, unitDegree, false, paint );
            }
        }
        canvas.save();
    }

//    public void resetSplitCount(int count){
//        selectUnit = 0;
//        arcCount = count;
//    }

    public void setRectF(RectF rectF) {
        this.rectF = rectF;
    }
}
