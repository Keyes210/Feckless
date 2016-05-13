package com.alexlowe.feckless;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "time_prefs";
    private int seconds;
    private boolean running;
    private Button btnRun;

    private FecklessPreferences fecklessPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRun = (Button)findViewById(R.id.run_button);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        runTimer();
    }

    private void runTimer(){
        final TextView timeview = (TextView)findViewById(R.id.time_view);
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

    public void onClickRun(View view){
        if(running){
            running = false;
            btnRun.setText(R.string.start);
        }else{
            running = true;
            btnRun.setText(R.string.pause);
        }
    }

    public void onClickReset(View view){
        running = false;
        btnRun.setText(R.string.start);
        seconds = 0;
    }

    public void addHour(View view) {
        if(seconds < 356400){
            seconds += 3600;
        }
    }

    public void subHour(View view) {
        if(seconds > 3600){
            seconds -= 3600;
        }else{
            seconds = 0;
        }
    }

    public void addMin(View view) {
        if(seconds < 359940){
            seconds += 60;
        }
    }

    public void subMin(View view) {
        if(seconds > 60){
            seconds -= 60;
        }else{
            seconds = 0;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveTimerState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fecklessPreferences = new FecklessPreferences(getApplicationContext());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
