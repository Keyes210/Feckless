package com.alexlowe.feckless;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Keyes on 5/13/2016.
 */
public class DayFragment extends Fragment {
    private Day day;

    @BindView(R.id.title)TextView tvTitle;
    @BindView(R.id.hrNum)TextView tvHrNum;
    @BindView(R.id.hrWord)TextView tvHrWord;
    @BindView(R.id.minNum)TextView tvMinNum;
    @BindView(R.id.minWord)TextView tvMinWord;
    @BindView(R.id.secNum)TextView tvSecNum;
    @BindView(R.id.secWord)TextView tvSecWord;
    private Unbinder unbinder;

    private int seconds, minutes, hours, totalSeconds;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_day, container, false);
        unbinder = ButterKnife.bind(this, view);

        Bundle bundle = this.getArguments();
        day = bundle.getParcelable(PagerFragment.DAY_OBJECT);
        totalSeconds = day.getSeconds();

        seconds = totalSeconds % 60;
        minutes = (totalSeconds % 3600) / 60;
        hours = totalSeconds / 3600;

        allTheThingsInTheirPlace();

        return view;
    }

    private void allTheThingsInTheirPlace() {
        tvTitle.setText("You've wasted:");

        tvHrNum.setText(String.valueOf(hours));
        String hourString = (hours == 1) ? "hour" : "hours";
        tvHrWord.setText(hourString);

        tvMinNum.setText(String.valueOf(minutes));
        String minString = (minutes == 1) ? "minute" : "minutes";
        tvMinWord.setText(minString);

        tvSecNum.setText(String.valueOf(seconds));
        String secString = (seconds == 1) ? "second" : "seconds";
        tvSecWord.setText(secString);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
