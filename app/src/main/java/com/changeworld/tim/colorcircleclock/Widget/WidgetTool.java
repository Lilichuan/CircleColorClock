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


public class WidgetTool {

    //筆劃粗細 （單位是Pixel）
    private int stroke_w ;
    //小工具的高度 （單位是Pixel）
    private int widgetH;
    //小圓形的高度 （單位是Pixel）
    private float smaller_stroke_w;

    //一個空心圓要被切成幾段。預設值是十段
    private int SPLIT = 10;

    //用來繪製的畫布物件
    private Canvas canvas;
    private Bitmap bmp;

    //主要畫筆 與 繪製指針區域的畫筆
    private Paint paint ,selectPaint;

    //360-(間隔數量*間隔角度)
    private float totalUnitDegree;
    //單一餅皮的所需角度
    private float unitDegree;

    //外層大圓範圍 與 內層小圓範圍
    private RectF percentRectF ,splitRectF;

    public WidgetTool(Context context,String color){
        init(context, color);
    }

    public WidgetTool(Context context,String color, int split){
        SPLIT = split;
        init(context, color);
    }

    /**
     * 初始化參數
     * @param context
     *
     * @param color
     * 色碼，字串
     */
    private void init(Context context,String color){
        int w_InDP = (int) context.getResources().getDimension(R.dimen.widget_h);
        stroke_w = TypedValue.COMPLEX_UNIT_DIP * 15;
        smaller_stroke_w = (float) (stroke_w * (0.7));
        widgetH = TypedValue.COMPLEX_UNIT_DIP * w_InDP;

        initCanvas();
        String fadeColor = Setting.getFadeColor(color);
        initPercent(color, fadeColor);
        initSplit(color, fadeColor);
    }


    private void initCanvas(){
        bmp = Bitmap.createBitmap(widgetH, widgetH, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bmp);

        percentRectF = new RectF(stroke_w, stroke_w, canvas.getWidth() - stroke_w, canvas.getHeight() - stroke_w);
        int margin = (int)(stroke_w * 2.5);
        splitRectF = new RectF(margin, margin, canvas.getWidth() - margin, canvas.getHeight() - margin);
    }



    //初始化繪製內圈圓圈的所需物件與參數
    private void initSplit(String selectColor, String fadeColor) {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor(fadeColor));
        paint.setStrokeWidth(smaller_stroke_w);
        paint.setStyle(Paint.Style.STROKE);

        selectPaint = new Paint();
        selectPaint.setAntiAlias(true);
        selectPaint.setColor(Color.parseColor(selectColor));
        selectPaint.setStrokeWidth(smaller_stroke_w);
        selectPaint.setStyle(Paint.Style.STROKE);

        float SEPARATE_DEGREE = 3;
        totalUnitDegree = ((float) (360)) / SPLIT;
        if(totalUnitDegree <= SEPARATE_DEGREE){
            SEPARATE_DEGREE = totalUnitDegree / 2;
        }


        unitDegree = totalUnitDegree - SEPARATE_DEGREE;
    }

    //利用迴圈繪製每個餅皮
    private void drawSplitCanvas(int select){
        for (int i = 0 ; i < SPLIT ;i++){
            canvas.drawArc(splitRectF,//弧形範圍長寬與位置座標
                    totalUnitDegree*i - 90,//該餅皮起始角度，90是誤差調整
                    unitDegree,//餅皮結束角度
                    false,//空心圓形
                    i < select ? selectPaint : paint);//繪製顏色
        }

    }


    private Paint percentMainPaint, percentFadePaint;

    private void initPercent(String color, String fadeColor){
        percentMainPaint = new Paint();
        percentMainPaint.setAntiAlias(true);
        percentMainPaint.setColor(Color.parseColor(color));
        percentMainPaint.setStrokeWidth(stroke_w);
        percentMainPaint.setStyle(Paint.Style.STROKE);

        percentFadePaint = new Paint();
        percentFadePaint.setAntiAlias(true);
        percentFadePaint.setColor(Color.parseColor(fadeColor));
        percentFadePaint.setStrokeWidth(stroke_w);
        percentFadePaint.setStyle(Paint.Style.STROKE);

    }

    public Bitmap draw(int select, float percent){
        drawSplitCanvas(select);
        drawPercentCanvas(percent);
        canvas.save();
        return bmp;
    }

    private void drawPercentCanvas(float percent){
        float radius = (percentRectF.width() / 2);
        canvas.drawCircle(radius + stroke_w ,radius + stroke_w , radius  , percentFadePaint);
        canvas.drawArc(percentRectF, -90 , (360*percent) ,false, percentMainPaint );
    }
}
