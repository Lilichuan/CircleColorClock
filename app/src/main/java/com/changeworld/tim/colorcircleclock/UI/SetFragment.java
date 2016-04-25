package com.changeworld.tim.colorcircleclock.UI;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.R;


public class SetFragment extends Fragment {

    private Setting setting;
    private View secondLayerArea;
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
//        if (getArguments() != null) {
//
//        }
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
                backInterface.onBack();
            }
        });

        CheckBox displayError = (CheckBox)root.findViewById(R.id.showErrorMsg);
        displayError.setChecked(setting.getShowError());
        displayError.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setting.setShowError(b);
            }
        });

        initSecondCircleSettingView(root);
        initColorSelect(root);

        return root;
    }

    private void initSecondCircleSettingView(View root){
        secondLayerArea = root.findViewById(R.id.secondLayerArea);
        secondLayerArea.setVisibility(setting.getShow2Layer() ? View.VISIBLE : View.GONE);
        splitCountText = (TextView)root.findViewById(R.id.splitCountText);
        SeekBar circleSplitSeekBar = (SeekBar)root.findViewById(R.id.splitCountSeekBar);
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

    private void initColorSelect(View root){
        String[] colors = getResources().getStringArray(R.array.colors);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.selection_textview, colors);
        Spinner spinner = (Spinner)root.findViewById(R.id.select_color);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String s;
                switch (position){
                    case 0:
                        s = Setting.COLOR_GREEN;
                        break;
                    case 1:
                        s = Setting.COLOR_PURPLE;
                        break;
                    case 2:
                        s = Setting.COLOR_ORANGE;
                        break;
                    case 3:
                        s = Setting.COLOR_BLUE;
                        break;
                    case 4:
                        s = Setting.COLOR_RAD;
                        break;
                    case 5:
                        s = Setting.COLOR_BARNEY;
                        break;
                    default:
                        s = Setting.COLOR_ORANGE;
                }

                setting.setColor(s);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int position;

        switch (setting.getColor()){
            case Setting.COLOR_GREEN:
                position = 0;
                break;
            case Setting.COLOR_PURPLE:
                position = 1;
                break;
            case Setting.COLOR_ORANGE:
                position = 2;
                break;
            case Setting.COLOR_BLUE:
                position = 3;
                break;
            case Setting.COLOR_RAD:
                position = 4;
                break;
            case Setting.COLOR_BARNEY:
                position = 5;
                break;
            default:
                position = 2;
        }


        spinner.setSelection(position);
    }


    private BackInterface backInterface;

    public void setBackInterface(BackInterface backInterface) {
        this.backInterface = backInterface;
    }

    public interface BackInterface{

        void onBack();
    }

}
