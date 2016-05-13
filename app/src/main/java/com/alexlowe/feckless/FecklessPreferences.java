package com.alexlowe.feckless;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Keyes on 5/12/2016.
 */
public class FecklessPreferences {
    private static final String TIMER_PREFS = "timer_prefs";
    private static final String DAY_PREFS = "day_prefs";

    private SharedPreferences timerPrefs;
    private SharedPreferences dayPrefs;
    private SharedPreferences.Editor timerEditor;
    private SharedPreferences.Editor dayEditor;

    public boolean isRunning;

    public FecklessPreferences(Context context){
        this.timerPrefs = context.getSharedPreferences(TIMER_PREFS, Context.MODE_PRIVATE);
        this.timerEditor = timerPrefs.edit();

        this.dayPrefs = context.getSharedPreferences(DAY_PREFS, Context.MODE_PRIVATE);
        this.dayEditor = dayPrefs.edit();
    }

    public void runningStorePrefs(int seconds){
        timerEditor.putInt("secondsAtOnPause", seconds);
        timerEditor.putLong("timeAtOnPause", System.currentTimeMillis());
        timerEditor.remove("secondsNotRunning");
        timerEditor.apply();
    }

    public void notRunningStorePrefs(int seconds){
        timerEditor.putInt("secondsNotRunning", seconds);
        timerEditor.remove("secondsAtOnPause");
        timerEditor.remove("timeAtOnPause");
        timerEditor.apply();
    }

    public int getSecondsFromPrefs(){
        int seconds = 0;

        if(timerPrefs.contains("secondsAtOnPause")){
            long resumeTime = System.currentTimeMillis();
            long stopTime = timerPrefs.getLong("timeAtOnPause", 0L);
            int elapsed = (int) (((resumeTime - stopTime)/1000));

            seconds = timerPrefs.getInt("secondsAtOnPause", 0) + elapsed;
            isRunning = true;
        }else if(timerPrefs.contains("secondsNotRunning")){
            isRunning = false;
            seconds = timerPrefs.getInt("secondsNotRunning", 0);
        }else{
            seconds = 0;
            isRunning = false;
        }

        return seconds;
    }

    public void clearPrefs(){
        timerEditor.clear();
        timerEditor.apply();
    }

}
