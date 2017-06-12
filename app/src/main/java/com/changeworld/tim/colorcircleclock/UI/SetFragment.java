package com.changeworld.tim.colorcircleclock.UI;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.changeworld.tim.colorcircleclock.Data.Setting;
import com.changeworld.tim.colorcircleclock.R;

import java.util.Iterator;
import java.util.List;


public class SetFragment extends Fragment {

    private Setting setting;
    private View secondLayerArea;
    private TextView splitCountText;

    public SetFragment() {

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

        CheckBox showBattery = (CheckBox)root.findViewById(R.id.showBattery);
        showBattery.setChecked(setting.isShowBattery());
        showBattery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setting.setShowBattery(isChecked);
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

    private MyAdapter colorAdapter;

    private void initColorSelect(View root){
        String[] colorList = setting.getColorList();
        colorAdapter = new MyAdapter(getContext(), R.layout.selection_textview);
        colorAdapter.addAll(colorList);
        Spinner spinner = (Spinner)root.findViewById(R.id.select_color);
        spinner.setAdapter(colorAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setting.setColor(colorAdapter.getItem(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        int position = 0;
        String color = setting.getColor();

        for (String s : colorList){
            if(s.contentEquals(color)){
                spinner.setSelection(position);
                return;
            }
            position++;
        }

    }

    private class MyAdapter extends ArrayAdapter<String> implements SpinnerAdapter{
        private LayoutInflater layoutInflater;
        private String[] nameArray;

        MyAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
            layoutInflater = LayoutInflater.from(context);
            nameArray = context.getResources().getStringArray(R.array.colors);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return myGetView(position, convertView);
        }

        @Override
        public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            return myGetView(position, convertView);
        }

        private View myGetView(int position, View convertView){
            if(convertView == null){
                convertView = layoutInflater.inflate(R.layout.selection_textview, null);
            }

            TextView textView = (TextView)convertView;
            textView.setText(nameArray[position]);

            return convertView;
        }
    }


    private BackInterface backInterface;

    public void setBackInterface(BackInterface backInterface) {
        this.backInterface = backInterface;
    }

    public interface BackInterface{

        void onBack();
    }

}
