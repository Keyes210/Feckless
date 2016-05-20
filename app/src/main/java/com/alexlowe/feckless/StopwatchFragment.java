package com.alexlowe.feckless;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.fafaldo.fabtoolbar.widget.FABToolbarLayout;

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

    private FABToolbarLayout layout;
    private View one, two, three, four;
    private FloatingActionButton fab;

    @BindView(R.id.run_button)Button btnRun;
    @BindView(R.id.sub_hr_button)Button btnSubHr;
    @BindView(R.id.sub_min_button)Button btnSubMin;
    @BindView(R.id.add_min_button)Button btnAddMin;
    @BindView(R.id.add_hr_button)Button btnAddHr;
    @BindView(R.id.reset_button)Button btnReset;

    @BindView(R.id.mo)TextView tvMo;
    @BindView(R.id.tu)TextView tvTu;
    @BindView(R.id.we)TextView tvWe;
    @BindView(R.id.th)TextView tvTh;
    @BindView(R.id.fr)TextView tvFr;
    @BindView(R.id.sa)TextView tvSa;
    @BindView(R.id.su)TextView tvSu;
    @BindView(R.id.x)ImageView tvX;

    private Unbinder unbinder;

    private FecklessPreferences fecklessPreferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stopwatch, container, false);
        unbinder = ButterKnife.bind(this, view);
        layout = (FABToolbarLayout) view.findViewById(R.id.fabtoolbar);
        fab = (FloatingActionButton)view.findViewById(R.id.fabtoolbar_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout.show();
            }
        });

        runTimer(view);

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

    @OnClick({R.id.mo, R.id.tu, R.id.we, R.id.th, R.id.fr, R.id.sa, R.id.su, R.id.x})
    public void clickDay(View view){
        switch (view.getId()){
            case R.id.mo:
                handleClick("Monday");
                break;
            case R.id.tu:
                handleClick("Tuesday");
                break;
            case R.id.we:
                handleClick("Wednesday");
                break;
            case R.id.th:
                handleClick("Thursday");
                break;
            case R.id.fr:
                handleClick("Friday");
                break;
            case R.id.sa:
                handleClick("Saturday");
                break;
            case R.id.su:
                handleClick("Sunday");
                break;
            case R.id.x:
                handleClick("close");
                break;
        }
    }

    private void handleClick(final String toolBarItem) {
        if(toolBarItem.equals("close")){
            layout.hide();
        }else{
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("Save time");
            builder.setMessage("Are you sure you want to save this time for " + toolBarItem + "?");
            builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Day.saveSeconds(toolBarItem, seconds);
                }
            });
            builder.setNegativeButton("Cancel", null);
            builder.show();
        }


    }

    @Override
    public void onPause() {
        super.onPause();
        saveTimerState();
        fecklessPreferences.storeDays();
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
