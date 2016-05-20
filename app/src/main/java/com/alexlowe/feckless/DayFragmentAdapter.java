package com.alexlowe.feckless;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Keyes on 5/13/2016.
 */
public class DayFragmentAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public DayFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String day){
        mFragmentList.add(fragment);
        mFragmentTitleList.add(day);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    public String getTitle(int position){
        String full = "";

        String stub = (String) getPageTitle(position);
        switch (stub){
            case "mon":
                full = "Monday";
                break;
            case "tue":
                full = "Tuesday";
                break;
            case "wed":
                full = "Wednesday";
                break;
            case "thu":
                full = "Thursday";
                break;
            case "fri":
                full = "Friday";
                break;
            case "sat":
                full = "Saturday";
                break;
            case "sun":
                full = "Sunday";
                break;
        }

        return full;

    }
}
