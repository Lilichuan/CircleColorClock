package com.changeworld.tim.colorcircleclock.WallPaper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.view.SurfaceHolder;

import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.UI.ClockFragment;
import com.changeworld.tim.colorcircleclock.UI.DrawSecondTool;


public class ClockWallpaperService extends WallpaperService{

    @Override
    public Engine onCreateEngine() {
        return new ClockWallPaperEngine(this);
    }




    private class ClockWallPaperEngine extends WallpaperService.Engine{

        private SurfaceHolder surfaceHolder;

        private float textH, splitCircleH;

        private DrawSecondTool drawSecondTool;

        private int secondSplit;

        private boolean visible, showText, showSecondCircle;

        private Paint textPaint, bigCirclePaint;

        private Handler handler = new Handler();
        private Runnable runnable = new Runnable() {
            @Override
            public void run() {
                draw();
            }
        };

        public ClockWallPaperEngine(Context context){
            init(context);
        }

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            this.surfaceHolder = surfaceHolder;
            init(getApplicationContext());
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            surfaceHolder = holder;
            init(getApplicationContext());
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            run(false);
        }

        private void init(Context context){
            Setting setting = new Setting(context);
            String color = setting.getColor();
            showText = setting.isShowClock();
            showSecondCircle = setting.getShow2Layer();
            secondSplit = setting.get2ndLayerSplit();

            textPaint = new Paint();
            textPaint.setAntiAlias(true);
            textPaint.setColor(Color.parseColor(color));
            Typeface type = Typeface.createFromAsset(getAssets(),"square_sans_serif_7.ttf");
            textPaint.setTypeface(type);

            bigCirclePaint = new Paint();
            bigCirclePaint.setAntiAlias(true);
            bigCirclePaint.setColor(Color.parseColor(color));
            bigCirclePaint.setStyle(Paint.Style.STROKE);

            drawSecondTool = new DrawSecondTool(secondSplit, color);
        }

        private void draw(){

            if(visible && surfaceHolder != null){

                Canvas canvas = surfaceHolder.lockCanvas();
                canvas.drawColor(Color.parseColor("#000000"));
                bigCirclePaint.setStrokeWidth(getCircleStrokeW(canvas));
                float x = canvas.getWidth()/ 2;
                float y = canvas.getHeight() / 2;
                canvas.drawCircle(x, y, getBigCircleRadius(canvas), bigCirclePaint);
                splitCircleH = getSecondCircleH(canvas);
                if(showText){
                    textH = countTextSize(splitCircleH);
                    textPaint.setTextSize(textH);
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

        private float getBigCircleRadius(Canvas canvas){
            int shortSite = getShortSide(canvas);
            return (float) (shortSite * 0.4);
        }

        private float getSecondCircleH(Canvas canvas){
            int shortSite = getShortSide(canvas);
            return (float) (shortSite * 0.7);
        }

        private float getCircleStrokeW(Canvas canvas){
            int shortSite = getShortSide(canvas);
            return (float) (shortSite * 0.05);
        }

        private float countTextSize(float secondCircleH){
            return (float)(secondCircleH * 0.1);
        }

        private int getShortSide(Canvas canvas){
            int h = canvas.getHeight();
            int w = canvas.getWidth();
            return (h < w) ? h : w;
        }
    }
}
