package com.alexlowe.feckless;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Keyes on 5/12/2016.
 */
public class FecklessPreferences {
    private static final String TIMER_PREFS = "timer_prefs";
    private static final String DAY_PREFS = "day_prefs";
    public static final String KEY_RUNNING_SECONDS = "secondsAtOnPause";
    public static final String KEY_TIME_AT_PAUSE = "timeAtOnPause";
    public static final String KEY_NOT_RUNNING_SECONDS = "secondsNotRunning";

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
        timerEditor.putInt(KEY_RUNNING_SECONDS, seconds);
        timerEditor.putLong(KEY_TIME_AT_PAUSE, System.currentTimeMillis());
        timerEditor.remove(KEY_NOT_RUNNING_SECONDS);
        timerEditor.apply();
    }

    public void notRunningStorePrefs(int seconds){
        timerEditor.putInt(KEY_NOT_RUNNING_SECONDS, seconds);
        timerEditor.remove(KEY_RUNNING_SECONDS);
        timerEditor.remove(KEY_TIME_AT_PAUSE);
        timerEditor.apply();
    }

    public int getSecondsFromPrefs(){
        int seconds = 0;

        if(timerPrefs.contains(KEY_RUNNING_SECONDS)){
            long resumeTime = System.currentTimeMillis();
            long stopTime = timerPrefs.getLong(KEY_TIME_AT_PAUSE, 0L);
            int elapsed = (int) (((resumeTime - stopTime)/1000));

            seconds = timerPrefs.getInt(KEY_RUNNING_SECONDS, 0) + elapsed;
            isRunning = true;
        }else if(timerPrefs.contains(KEY_NOT_RUNNING_SECONDS)){
            isRunning = false;
            seconds = timerPrefs.getInt(KEY_NOT_RUNNING_SECONDS, 0);
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
