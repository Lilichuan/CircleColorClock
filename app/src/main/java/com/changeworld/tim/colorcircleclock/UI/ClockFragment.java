package com.changeworld.tim.colorcircleclock.UI;


import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.MainActivity;
import com.changeworld.tim.colorcircleclock.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class ClockFragment extends Fragment {

    private static SimpleDateFormat format;
    private Timer timer;
    private static TextView textView;
    private Setting setting;
    private static DrawCircleTool drawCircleTool;
    private View mainCircle;

    private static ImageView splitCircle;


    public ClockFragment() {
        // Required empty public constructor
    }


    public static ClockFragment newInstance() {
        ClockFragment fragment = new ClockFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clock, container, false);
        root.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity)getActivity();
                activity.changePage(MainActivity.PAGE_SET);
            }
        });
        setting = new Setting(getContext());
        init(root);
        initSplitCircle(root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(setting.isShowClock()){
            startDrawTimeText();
        }else {
            textView.setVisibility(View.INVISIBLE);
        }





    }

    private void init(View root){
        format = new SimpleDateFormat(FORMAT);
        textView = (TextView)root.findViewById(R.id.text);
        Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"square_sans_serif_7.ttf");
        textView.setTypeface(type);
        mainCircle = root.findViewById(R.id.mainCircle);


    }

    private void initSplitCircle(View root){
        splitCircle = (ImageView)root.findViewById(R.id.splitCircle);

        if(!setting.getShow2Layer()){
            splitCircle.setVisibility(View.GONE);
            return;
        }

        splitCircle.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("onStart", "splitCircle hight="+ splitCircle.getMeasuredWidth());
                drawCircleTool = new DrawCircleTool(setting.get2ndLayerSplit(), splitCircle.getMeasuredWidth());
            }
        },1000);
    }

    private static final String FORMAT = "HH:mm:ss";

    private static String createNowTimeText(){
        Calendar calendar = Calendar.getInstance();
        return format.format(calendar.getTime());
    }

    private void startDrawTimeText(){
        timer = new Timer();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.obtainMessage().sendToTarget();
            }
        },200,1000);
    }

    private static Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            textView.setText(createNowTimeText());
            if(drawCircleTool != null){
                splitCircle.draw(drawCircleTool.outputCanvas());
            }

            super.handleMessage(msg);
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        if(timer != null){
            timer.purge();
            timer.cancel();
            timer = null;
        }
    }

    //Draw 2nd circle
    private class DrawCircleTool{

        private Paint paint ,selectPaint;
        private Canvas canvas ;
        private RectF rectf;

        private int selectUnit = 0;//start with 1

        private int separateDegree = 3;
        private int diameter, radius;
        private int arcCount, totalUnitDegree, unitDegree;

        public DrawCircleTool(int count, int diameter){
            paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.parseColor("#ffa16a"));
            paint.setStrokeWidth((float) 10.0);
            paint.setStyle(Paint.Style.STROKE);

            selectPaint = new Paint();
            selectPaint.setAntiAlias(true);
            selectPaint.setColor(Color.parseColor("#ff6a13"));
            selectPaint.setStrokeWidth((float) 10.0);
            selectPaint.setStyle(Paint.Style.STROKE);
            this.diameter = diameter;
            radius = diameter / 2;
            rectf = new RectF(0,0, radius, radius);
            Bitmap.Config conf = Bitmap.Config.ARGB_8888;
            Bitmap bmp = Bitmap.createBitmap(diameter, diameter, conf);
            canvas = new Canvas(bmp);
            canvas.drawColor(Color.parseColor("#000000"));
            canvas.save();



            arcCount = count;
            totalUnitDegree = 360 / count;
            if(totalUnitDegree <= separateDegree){
                separateDegree = totalUnitDegree / 2;
            }


            unitDegree = totalUnitDegree - separateDegree;
        }

        public Canvas outputCanvas(){


            selectUnit++;
            if(selectUnit > arcCount){
                selectUnit = 1;
            }

            for (int i = 1 ; i <= arcCount ;i++){

                if(i == selectUnit){
                    canvas.drawArc(rectf, totalUnitDegree*i, unitDegree, false, selectPaint );
                }else {
                    canvas.drawArc(rectf, totalUnitDegree*i, unitDegree, false, paint );
                }
            }

//            Log.d("DrawCircleTool", String.format("selectUnit=%d, arcCount=%d, totalUnitDegree=%d, unitDegree=%d",
//                    selectUnit, arcCount, totalUnitDegree, unitDegree));
            canvas.save();
            return canvas;
        }

    }
}
