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
            new Day("Mon", 0),
            new Day("Tue", 0),
            new Day("Wed", 0),
            new Day("Thu", 0),
            new Day("Fri", 0),
            new Day("Sat", 0),
            new Day("Sun", 0),
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

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getSeconds() {
        return seconds;
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
