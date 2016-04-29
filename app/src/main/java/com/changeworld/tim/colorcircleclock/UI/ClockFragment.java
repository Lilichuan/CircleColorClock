package com.changeworld.tim.colorcircleclock.UI;



import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.MainActivity;
import com.changeworld.tim.colorcircleclock.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class ClockFragment extends Fragment {

    private Timer timer;
    private static TextView textView;
    private static boolean DISPLAY_2ND_CIRCLE, SHOW_CLOCK;

    private static CircleView splitCircle;
    private static BigCircleView bigCircleView;
    private static String TEXT_COLOR;


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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_clock, container, false);

        root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                MainActivity activity = (MainActivity)getActivity();
                activity.changePage(MainActivity.PAGE_SET);
                return false;
            }
        });

        init(root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(DISPLAY_2ND_CIRCLE || SHOW_CLOCK){
            startDrawTimeText();
        }else {
            textView.setVisibility(View.INVISIBLE);
        }

    }

    private void init(View root){
        Setting setting = new Setting(getContext());
        DISPLAY_2ND_CIRCLE = setting.getShow2Layer();
        SHOW_CLOCK = setting.isShowClock();
        textView = (TextView)root.findViewById(R.id.text);
        bigCircleView = (BigCircleView)root.findViewById(R.id.mainCircle);

        if(SHOW_CLOCK){
            Typeface type = Typeface.createFromAsset(getActivity().getAssets(),"square_sans_serif_7.ttf");
            textView.setTypeface(type);
            TEXT_COLOR = setting.getColor();

        }else {
            textView.setVisibility(View.INVISIBLE);
        }

        initSplitCircle(root);

    }

    private void initSplitCircle(View root){
        splitCircle = (CircleView)root.findViewById(R.id.splitCircle);

        if(!DISPLAY_2ND_CIRCLE){
            splitCircle.setVisibility(View.GONE);
            return;
        }

        splitCircle.init();

    }

    private static final String FORMAT = "HH:mm:ss";

    public static String createNowTimeText(){
        SimpleDateFormat format = new SimpleDateFormat(FORMAT , Locale.US);
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

            bigCircleView.setPause(false);
            bigCircleView.invalidate();

            if(SHOW_CLOCK){
                textView.setBackgroundColor(Color.parseColor("#000000"));
                textView.setTextColor(Color.parseColor(TEXT_COLOR));
                textView.setText(createNowTimeText());
            }

            if(DISPLAY_2ND_CIRCLE){
                splitCircle.setPause(false);
                splitCircle.invalidate();
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


}
