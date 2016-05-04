package com.changeworld.tim.colorcircleclock.Widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.TypedValue;

import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.R;

/**
 * Created by tim on 2016/5/4.
 */
public class WidgetTool {

    private int stroke_w,  widgetH;
    private float smaller_stroke_w;

    private int SPLIT = 10;

    private Canvas canvas;

    private RectF percentRectF ,splitRectF;


    public WidgetTool(Context context,String color){
        stroke_w = TypedValue.COMPLEX_UNIT_DIP * context.getResources().getInteger(R.integer.widget_stroke_w);
        smaller_stroke_w = (float) (stroke_w * (0.7));
        widgetH = TypedValue.COMPLEX_UNIT_DIP * context.getResources().getInteger(R.integer.widget_h);

        initCanvas();

        String fadeColor = Setting.getFadeColor(color);

        initPercent(color, fadeColor);
        initSplit(color, fadeColor);
    }



    private void initCanvas(){

        Bitmap bmp = Bitmap.createBitmap(widgetH, widgetH, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bmp);

        percentRectF = new RectF(stroke_w, stroke_w, canvas.getWidth() - stroke_w, canvas.getHeight() - stroke_w);
        int margin = stroke_w * 2;
        splitRectF = new RectF(margin, margin, canvas.getWidth() - margin, canvas.getHeight() - margin);
    }

    private Paint paint ,selectPaint;
    private float totalUnitDegree, unitDegree;

    public void initSplit(String selectColor, String fadeColor) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(fadeColor));
        paint.setStrokeWidth(smaller_stroke_w);
        paint.setStyle(Paint.Style.STROKE);

        selectPaint = new Paint(paint);
        selectPaint.setColor(Color.parseColor(selectColor));

        float SEPARATE_DEGREE = 3;
        totalUnitDegree = ((float) (360)) / SPLIT;
        if(totalUnitDegree <= SEPARATE_DEGREE){
            SEPARATE_DEGREE = totalUnitDegree / 2;
        }


        unitDegree = totalUnitDegree - SEPARATE_DEGREE;
    }


    public void drawSplitCanvas(Canvas canvas, int select){

        for (int i = 0 ; i < select ;i++){

            if(i <= select){
                canvas.drawArc(splitRectF, totalUnitDegree*i, unitDegree, false, selectPaint );
            }else {
                canvas.drawArc(splitRectF, totalUnitDegree*i, unitDegree, false, paint );
            }
        }

    }


    private Paint percentMainPaint, percentFadePaint;

    private void initPercent(String color, String fadeColor){
        percentMainPaint = new Paint();
        percentMainPaint.setAntiAlias(true);
        percentMainPaint.setColor(Color.parseColor(color));
        percentMainPaint.setStrokeWidth(stroke_w);
        percentMainPaint.setStyle(Paint.Style.STROKE);

        percentFadePaint = new Paint(paint);
        percentFadePaint.setColor(Color.parseColor(fadeColor));

    }

    public void drawPercentCanvas(Canvas canvas, float percent){
        float radius = (percentRectF.width() / 2);
        canvas.drawCircle(radius ,radius , radius - stroke_w , percentFadePaint);
        canvas.drawArc(percentRectF, -90 , (360*percent) ,false, paint );
    }
}