package com.alexlowe.feckless;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by Keyes on 5/12/2016.
 */
public class FecklessPreferences {
    private static final String TIMER_PREFS = "timer_prefs";
    private static final String DAY_PREFS = "day_prefs";
    public static final String KEY_RUNNING_SECONDS = "secondsAtOnPause";
    public static final String KEY_TIME_AT_PAUSE = "timeAtOnPause";
    public static final String KEY_NOT_RUNNING_SECONDS = "secondsNotRunning";

    public static final String KEY_MON = "mondaySeconds";
    public static final String KEY_TUE = "tuesdaySeconds";
    public static final String KEY_WED = "wedsdaySeconds";
    public static final String KEY_THU = "thursdaySeconds";
    public static final String KEY_FRI = "fridaySeconds";
    public static final String KEY_SAT = "saturdaySeconds";
    public static final String KEY_SUN = "sundaySeconds";

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

    public void storeDays(){
        Log.i("rimjob", "Storing... ");
        dayEditor.putInt(KEY_MON, Day.days[0].getSeconds());
        dayEditor.putInt(KEY_TUE, Day.days[1].getSeconds());
        dayEditor.putInt(KEY_WED, Day.days[2].getSeconds());
        dayEditor.putInt(KEY_THU, Day.days[3].getSeconds());
        dayEditor.putInt(KEY_FRI, Day.days[4].getSeconds());
        dayEditor.putInt(KEY_SAT, Day.days[5].getSeconds());
        dayEditor.putInt(KEY_SUN, Day.days[6].getSeconds());
        dayEditor.apply();
    }

    public void retrieveDays(){
            Log.i("rimjob", "Prefs Restored");
            Day.days[0].setSeconds(dayPrefs.getInt(KEY_MON, 0));
            Day.days[1].setSeconds(dayPrefs.getInt(KEY_TUE, 0));
            Day.days[2].setSeconds(dayPrefs.getInt(KEY_WED, 0));
            Day.days[3].setSeconds(dayPrefs.getInt(KEY_THU, 0));
            Day.days[4].setSeconds(dayPrefs.getInt(KEY_FRI, 0));
            Day.days[5].setSeconds(dayPrefs.getInt(KEY_SAT, 0));
            Day.days[6].setSeconds(dayPrefs.getInt(KEY_SUN, 0));
    }


    public void clearPrefs(){
        timerEditor.clear();
        timerEditor.apply();
    }

}
