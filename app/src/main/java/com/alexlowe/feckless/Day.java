package com.alexlowe.feckless;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Keyes on 5/13/2016.
 */
public class Day implements Parcelable{
    private String day;
    private int seconds;

    public static final Day[] days = {
            new Day("mon", 0),
            new Day("tue", 0),
            new Day("wed", 0),
            new Day("thu", 0),
            new Day("fri", 0),
            new Day("sat", 0),
            new Day("sun", 0),
    };

    public Day(String day, int seconds) {
        this.day = day;
        this.seconds = seconds;
    }

    protected Day(Parcel in) {
        day = in.readString();
        seconds = in.readInt();
    }

    public static final Creator<Day> CREATOR = new Creator<Day>() {
        @Override
        public Day createFromParcel(Parcel in) {
            return new Day(in);
        }

        @Override
        public Day[] newArray(int size) {
            return new Day[size];
        }
    };

    public static void saveSeconds(String dayName, int seconds){
        switch (dayName){
            case "Monday":
                Day.days[0].setSeconds(seconds);
                break;
            case "Tuesday":
                Day.days[1].setSeconds(seconds);
                break;
            case "Wednesday":
                Day.days[2].setSeconds(seconds);
                break;
            case "Thursday":
                Day.days[3].setSeconds(seconds);
                break;
            case "Friday":
                Day.days[4].setSeconds(seconds);
                break;
            case "Saturday":
                Day.days[5].setSeconds(seconds);
                break;
            case "Sunday":
                Day.days[6].setSeconds(seconds);
                break;
        }
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getSeconds() {
        return seconds;
    }

    public static int getTotal(){
        int total = 0;
        for(Day day : days){
            total += day.seconds;
        }
        return total;
    }

    public static void clearAll(){
        for(Day day : days){
            day.setSeconds(0);
        }
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(day);
        dest.writeInt(seconds);
    }
}
