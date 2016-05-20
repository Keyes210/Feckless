package com.alexlowe.feckless;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Keyes on 5/16/2016.
 */
public class TotalFragment extends Fragment {
    @BindView(R.id.title)TextView tvTitle;
    @BindView(R.id.hrNum)TextView tvHrNum;
    @BindView(R.id.hrWord)TextView tvHrWord;
    @BindView(R.id.minNum)TextView tvMinNum;
    @BindView(R.id.minWord)TextView tvMinWord;
    @BindView(R.id.secNum)TextView tvSecNum;
    @BindView(R.id.secWord)TextView tvSecWord;
    @BindView(R.id.fab)FloatingActionButton fab;
    private Unbinder unbinder;

    private int seconds, minutes, hours, totalSeconds;

    private FecklessPreferences fp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_total, container, false);
        unbinder = ButterKnife.bind(this, view);

        totalSeconds = Day.getTotal();

        seconds = totalSeconds % 60;
        minutes = (totalSeconds % 3600) / 60;
        hours = totalSeconds / 3600;

        allTheThingsInTheirPlace();
        fp = new FecklessPreferences(getContext());

        return view;
    }

    private void allTheThingsInTheirPlace() {
        tvTitle.setText("This week you've wasted:");

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

    @OnClick(R.id.fab)
    public void clearDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Clear week");
        builder.setMessage("Are you sure you want to clear all data for this week?");
        builder.setPositiveButton("Clear", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Day.clearAll();
                fp.storeDays();

                Fragment fragment = getActivity().getSupportFragmentManager()
                        .findFragmentById(R.id.fragmentContainer);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.detach(fragment).attach(fragment).commit();
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

}
