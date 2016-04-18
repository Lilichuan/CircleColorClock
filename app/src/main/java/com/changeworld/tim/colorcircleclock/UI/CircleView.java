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

    private DrawSecondTool drawCircleTool;

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
            drawCircleTool = new DrawSecondTool(setting.get2ndLayerSplit(), setting.getColor());
        }

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(drawCircleTool != null){
            drawCircleTool.drawCanvas(canvas);
        }
    }

}
