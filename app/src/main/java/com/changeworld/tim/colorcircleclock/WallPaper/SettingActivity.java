package com.changeworld.tim.colorcircleclock.WallPaper;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.changeworld.tim.colorcircleclock.R;
import com.changeworld.tim.colorcircleclock.UI.SetFragment;

public class SettingActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settingFragmentContainer, SetFragment.newInstance())
                .commit();
    }
}
