package com.changeworld.tim.colorcircleclock.WallPaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Handler;
import android.os.Message;
import android.service.wallpaper.WallpaperService;
import android.util.TypedValue;
import android.view.SurfaceHolder;

import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.R;
import com.changeworld.tim.colorcircleclock.UI.DrawSecondTool;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by tim on 2016/4/18.
 */
public class ClockWallpaperService extends WallpaperService{

    private String color;
    private int bigCircleStrokeW;

    private int secondSplit;

    private boolean showText, showSecondCircle;

    private Paint textPaint;


    @Override
    public Engine onCreateEngine() {
        Setting setting = new Setting(this);
        color = setting.getColor();
        bigCircleStrokeW = TypedValue.COMPLEX_UNIT_DIP * getResources().getInteger(R.integer.main_circle_stroke_w);
        showText = setting.isShowClock();
        showSecondCircle = setting.getShow2Layer();
        secondSplit = setting.get2ndLayerSplit();

        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor(color));
        Typeface type = Typeface.createFromAsset(getAssets(),"square_sans_serif_7.ttf");
        textPaint.setTypeface(type);
        textPaint.setTextSize(TypedValue.COMPLEX_UNIT_SP * getResources().getInteger(R.integer.clockTextSizeInt));

        return null;
    }

    private class ClockWallPaperEngine extends WallpaperService.Engine{

        private boolean visible;
        private SurfaceHolder surfaceHolder;

        private DrawSecondTool drawSecondTool;
        private Timer timer;

        private Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                draw(surfaceHolder.lockCanvas());
                super.handleMessage(msg);
            }
        };

        public ClockWallPaperEngine(){
            drawSecondTool = new DrawSecondTool(secondSplit, color);


        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.surfaceHolder = surfaceHolder;

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.obtainMessage().sendToTarget();
                }
            },200,1000);
        }

        private void draw(Canvas canvas){
            if(showText){

            }

            if(showSecondCircle){

            }


        }
    }
}
