package com.example.lkh.a173050061_svm;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by DJL on 4/6/2018.
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

    private String title[] = {"TRAIN", "TEST"};
    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
    }



    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                train tab1 = new train();
                return tab1;
            case 1:
                test tab2 = new test();
                return tab2;
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }
}
