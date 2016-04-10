package com.changeworld.tim.colorcircleclock.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.MainActivity;
import com.changeworld.tim.colorcircleclock.R;


public class SetFragment extends Fragment {

    private Setting setting;
    private View secondLayerArea;
    private SeekBar circleSplitSeekBar;
    private TextView splitCountText;

    public SetFragment() {
        // Required empty public constructor
    }


    public static SetFragment newInstance() {
        SetFragment fragment = new SetFragment();
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
        View root = inflater.inflate(R.layout.fragment_set, container, false);

        setting = new Setting(getContext());

        CheckBox showClock = (CheckBox)root.findViewById(R.id.showClock);
        showClock.setChecked(setting.isShowClock());
        showClock.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.setShowClock(isChecked);
            }
        });

        CheckBox display2ndLayer = (CheckBox)root.findViewById(R.id.show2ndLayer);
        display2ndLayer.setChecked(setting.getShow2Layer());
        display2ndLayer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.setShow2Layer(isChecked);
                secondLayerArea.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            }
        });

        root.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity activity = (MainActivity) getActivity();
                activity.changePage(MainActivity.PAGE_CLOCK);
            }
        });

        initSecondCircleSettingView(root);

        return root;
    }

    private void initSecondCircleSettingView(View root){
        secondLayerArea = root.findViewById(R.id.secondLayerArea);
        splitCountText = (TextView)root.findViewById(R.id.splitCountText);
        circleSplitSeekBar = (SeekBar)root.findViewById(R.id.splitCountSeekBar);
        circleSplitSeekBar.setMax(Setting.CIRCLE_SPLIT_MAX);
        circleSplitSeekBar.setProgress(setting.get2ndLayerSplit());
        circleSplitSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress < Setting.CIRCLE_SPLIT_MINI && fromUser){
                    progress = Setting.CIRCLE_SPLIT_MINI;
                }
                setSplitText(progress);
                setting.set2ndLayerSplit(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        setSplitText(setting.get2ndLayerSplit());

    }

    private void setSplitText(int count){
        splitCountText.setText(getString(R.string.secondLayerSplitCount, count));
    }

}
