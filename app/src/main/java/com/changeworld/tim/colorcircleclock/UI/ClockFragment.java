package com.changeworld.tim.colorcircleclock.UI;


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
import java.util.Timer;
import java.util.TimerTask;


public class ClockFragment extends Fragment {

    private static SimpleDateFormat format;
    private Timer timer;
    private static TextView textView;

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
        init(root);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Setting setting = new Setting(getContext());
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
