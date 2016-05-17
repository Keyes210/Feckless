package com.alexlowe.feckless;

import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PagerFragment extends Fragment {
    public static final String DAY_OBJECT = "day_object";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        // Setting ViewPager for each Tabs
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setUpViewPager(viewPager);

        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout)view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        Bundle bundle = this.getArguments();

        viewPager.setCurrentItem(bundle.getInt("tab", 0));

        return view;
    }

    private void setUpViewPager(ViewPager viewPager) {
        DayFragmentAdapter adapter = new DayFragmentAdapter(getActivity().getSupportFragmentManager());
        for (int i = 0; i < Day.days.length; i++) {
            DayFragment fragment = new DayFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(DAY_OBJECT, Day.days[i]);
            fragment.setArguments(bundle);
            adapter.addFragment(fragment, Day.days[i].getDay());
        }

        viewPager.setAdapter(adapter);
    }



}
