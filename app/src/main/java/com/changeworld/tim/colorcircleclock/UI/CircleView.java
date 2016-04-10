package com.changeworld.tim.colorcircleclock.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.changeworld.tim.colorcircleclock.Data.Setting;

/**
 * Created by tim on 2016/4/10.
 */
public class CircleView extends View{

    private DrawCircleTool drawCircleTool;

    public CircleView(Context context) {
        super(context);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(){
        Setting setting = new Setting(getContext());
        if(setting.getShow2Layer()){
            drawCircleTool = new DrawCircleTool(setting.get2ndLayerSplit());
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(drawCircleTool != null){
            drawCircleTool.drawCanvas(canvas);
        }
    }

    //Draw 2nd circle
    private class DrawCircleTool{

        private Paint paint ,selectPaint;
        private RectF rectF;

        private int selectUnit = 0;

        private int arcCount, totalUnitDegree, unitDegree;
        private int PAINT_STROKE_W = 20, SEPARATE_DEGREE = 3;

        public DrawCircleTool(int count){
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#ffa16a"));
            paint.setStrokeWidth((float) 20.0);
            paint.setStyle(Paint.Style.STROKE);

            selectPaint = new Paint();
            selectPaint.setAntiAlias(true);
            selectPaint.setColor(Color.parseColor("#ff6a13"));
            selectPaint.setStrokeWidth((float) 20.0);
            selectPaint.setStyle(Paint.Style.STROKE);

            arcCount = count;
            totalUnitDegree = 360 / count;
            if(totalUnitDegree <= SEPARATE_DEGREE){
                SEPARATE_DEGREE = totalUnitDegree / 2;
            }


            unitDegree = totalUnitDegree - SEPARATE_DEGREE;
        }

        public void drawCanvas(Canvas canvas){

            if(rectF == null){
                rectF = new RectF(PAINT_STROKE_W, PAINT_STROKE_W, canvas.getWidth() - PAINT_STROKE_W, getHeight() - PAINT_STROKE_W);
            }

            selectUnit++;
            if(selectUnit > arcCount){
                selectUnit = 0;
            }

            for (int i = 0 ; i < arcCount ;i++){

                if(i == selectUnit){
                    canvas.drawArc(rectF, totalUnitDegree*i, unitDegree, false, selectPaint );
                }else {
                    canvas.drawArc(rectF, totalUnitDegree*i, unitDegree, false, paint );
                }
            }

//            Log.d("DrawCircleTool", String.format("selectUnit=%d, arcCount=%d, totalUnitDegree=%d, unitDegree=%d",
//                    selectUnit, arcCount, totalUnitDegree, unitDegree));
            canvas.save();
        }

    }

}
