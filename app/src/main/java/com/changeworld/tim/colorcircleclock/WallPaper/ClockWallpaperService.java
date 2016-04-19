package com.changeworld.tim.colorcircleclock.WallPaper;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.TypedValue;
import android.view.SurfaceHolder;

import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.R;
import com.changeworld.tim.colorcircleclock.UI.ClockFragment;
import com.changeworld.tim.colorcircleclock.UI.DrawSecondTool;

/**
 * Created by tim on 2016/4/18.
 */
public class ClockWallpaperService extends WallpaperService{

    private String color;
    private int bigCircleStrokeW;
    private float bigCircleRadius;

    private int secondSplit;

    private boolean showText, showSecondCircle;

    private Paint textPaint, bigCirclePaint;
    private int textH, splitCircleH;


    @Override
    public Engine onCreateEngine() {
        Setting setting = new Setting(this);
        color = setting.getColor();
        bigCircleStrokeW = TypedValue.COMPLEX_UNIT_DIP * getResources().getInteger(R.integer.main_circle_stroke_w);
        showText = setting.isShowClock();
        showSecondCircle = setting.getShow2Layer();
        secondSplit = setting.get2ndLayerSplit();

        textH = TypedValue.COMPLEX_UNIT_SP * getResources().getInteger(R.integer.clockTextSizeInt);
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor(color));
        Typeface type = Typeface.createFromAsset(getAssets(),"square_sans_serif_7.ttf");
        textPaint.setTypeface(type);
        textPaint.setTextSize(textH);

        bigCirclePaint = new Paint();
        bigCirclePaint.setAntiAlias(true);
        bigCirclePaint.setColor(Color.parseColor(color));
        bigCirclePaint.setStrokeWidth(bigCircleStrokeW);
        bigCirclePaint.setStyle(Paint.Style.STROKE);

        int bigCircleW = TypedValue.COMPLEX_UNIT_DIP * getResources().getInteger(R.integer.big_circle_h_int);
        bigCircleRadius = bigCircleW / 2;
        splitCircleH = TypedValue.COMPLEX_UNIT_DIP * getResources().getInteger(R.integer.split_circle_h_int);
        return new ClockWallPaperEngine();
    }

    private class ClockWallPaperEngine extends WallpaperService.Engine{

        private boolean visible;
        private SurfaceHolder surfaceHolder;

        private DrawSecondTool drawSecondTool;

        private Handler handler = new Handler();
        private Runnable runnable = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        public ClockWallPaperEngine(){

            drawSecondTool = new DrawSecondTool(secondSplit, color);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            surfaceHolder = holder;
        }

        private void draw(){

            if(visible){
                Canvas canvas = surfaceHolder.lockCanvas();
                canvas.drawColor(Color.parseColor("#000000"));

                float x = canvas.getWidth()/ 2;
                float y = canvas.getHeight() / 2;
                canvas.drawCircle(x, y, bigCircleRadius, bigCirclePaint);

                if(showText){
                    String s = ClockFragment.createNowTimeText();
                    float textLen = textPaint.measureText(s);
                    float x2 = (canvas.getWidth() - textLen)/ 2;
                    float y2 = (canvas.getHeight() - textH)/ 2;
                    canvas.drawText(s,x2,y2 + textH,textPaint);
                }

                if(showSecondCircle){
                    float leftMargin = (canvas.getWidth() - splitCircleH)/2;
                    float topMargin = (canvas.getHeight() - splitCircleH)/2;
                    RectF rectF = new RectF(leftMargin, topMargin, leftMargin + splitCircleH , topMargin + splitCircleH);
                    drawSecondTool.setRectF(rectF);
                    drawSecondTool.drawCanvas(canvas);
                }

                surfaceHolder.unlockCanvasAndPost(canvas);
            }

            handler.removeCallbacks(runnable);
            handler.postDelayed(runnable, 1000);

        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            run(visible);
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            handler.removeCallbacks(runnable);
        }

        private void run(boolean isRun){
            this.visible = isRun;
            if (isRun) {
                handler.post(runnable);
            } else {
                handler.removeCallbacks(runnable);
            }
        }
    }
}
