package com.alexlowe.feckless;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Keyes on 5/14/2016.
 */
public class StopwatchFragment extends Fragment {
    private static final String PREFS_NAME = "time_prefs";
    private static final String TAG = "rimjob";
    private int seconds;
    private boolean running;

    @BindView(R.id.run_button)Button btnRun;
    @BindView(R.id.sub_hr_button)Button btnSubHr;
    @BindView(R.id.sub_min_button)Button btnSubMin;
    @BindView(R.id.add_min_button)Button btnAddMin;
    @BindView(R.id.add_hr_button)Button btnAddHr;
    @BindView(R.id.reset_button)Button btnReset;
    private Unbinder unbinder;

    private FecklessPreferences fecklessPreferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        unbinder = ButterKnife.bind(this,view);

        runTimer(view);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }

    private void runTimer(View view){
        final TextView timeview = (TextView)view.findViewById(R.id.time_view);
        final Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;

                String time = String.format("%02d:%02d:%02d", hours, minutes, secs);
                timeview.setText(time);

                if (running) {
                    seconds++;
                }

                if (seconds > 359998) {
                    running = false;
                }

                handler.postDelayed(this, 1000);
            }

        });

    }


    @OnClick(R.id.run_button)
    public void onClickRun(){
        if(running){
            running = false;
            btnRun.setText(R.string.start);
        }else{
            running = true;
            btnRun.setText(R.string.pause);
        }
    }

    @OnClick(R.id.reset_button)
    public void onClickReset(){
        running = false;
        btnRun.setText(R.string.start);
        seconds = 0;
    }

    @OnClick(R.id.add_hr_button)
    public void addHour() {
        if(seconds < 356400){
            seconds += 3600;
        }
    }

    @OnClick(R.id.sub_hr_button)
    public void subHour() {
        if(seconds > 3600){
            seconds -= 3600;
        }else{
            seconds = 0;
        }
    }

    @OnClick(R.id.add_min_button)
    public void addMin() {
        if(seconds < 359940){
            seconds += 60;
        }
    }

    @OnClick(R.id.sub_min_button)
    public void subMin() {
        if(seconds > 60){
            seconds -= 60;
        }else{
            seconds = 0;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        saveTimerState();
    }

    @Override
    public void onResume() {
        super.onResume();
        fecklessPreferences = new FecklessPreferences(getContext());
        retrieveTimerState();
    }

    private void saveTimerState() {
        if(running){
            fecklessPreferences.runningStorePrefs(seconds);
        }else{
            fecklessPreferences.notRunningStorePrefs(seconds);
        }
    }

    private void retrieveTimerState() {
        seconds = fecklessPreferences.getSecondsFromPrefs();
        running = fecklessPreferences.isRunning;

        if(running)btnRun.setText(R.string.pause);

        fecklessPreferences.clearPrefs();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
