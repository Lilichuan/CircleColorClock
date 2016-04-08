package com.changeworld.tim.colorcircleclock;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.changeworld.tim.colorcircleclock.UI.ClockFragment;
import com.changeworld.tim.colorcircleclock.UI.SetFragment;

public class MainActivity extends FragmentActivity {

    public static final int PAGE_CLOCK = 1;
    public static final int PAGE_SET = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView(R.layout.activity_main);
        changePage(PAGE_CLOCK);
    }

    public void changePage(int page){

        Fragment f = null;

        switch (page){
            case PAGE_CLOCK:
                f = ClockFragment.newInstance();
                break;
            case PAGE_SET:
                f = SetFragment.newInstance();
                break;
        }

        changeFragment(f);
    }

    private void changeFragment(Fragment f){
        if(f == null){
            return;
        }

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, f)
                .commit();
    }


}
