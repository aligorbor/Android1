package ru.geekbrains.android1.homework;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatDelegate;

public class CalcSettings implements Parcelable {
    public static final String CALC_PREFERENCES = "calcsettings";
    public static final String CALC_PREFERENCES_MODE_NIGHT = "modenight";
    public static final String CALC_PREFERENCES_BACKGROUND = "resourcebackground";
    public static final String CALC_PREFERENCES_DEC_FORMAT = "strdecformat";

    private int modeNight;
    private int resourceBackground;
    private String strDecFormat;

    public CalcSettings() {
        this.modeNight = AppCompatDelegate.MODE_NIGHT_NO;
        this.resourceBackground = 0;
        this.strDecFormat = "#.######";
    }

    public void getPreferences(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(CALC_PREFERENCES, Context.MODE_PRIVATE);
        if (sharedPref.contains(CALC_PREFERENCES_MODE_NIGHT))
            modeNight = sharedPref.getInt(CALC_PREFERENCES_MODE_NIGHT, modeNight);
        if (sharedPref.contains(CALC_PREFERENCES_BACKGROUND))
            resourceBackground = sharedPref.getInt(CALC_PREFERENCES_BACKGROUND, resourceBackground);
        if (sharedPref.contains(CALC_PREFERENCES_DEC_FORMAT))
            strDecFormat = sharedPref.getString(CALC_PREFERENCES_DEC_FORMAT, strDecFormat);
    }

    public void setPreferences(Activity activity) {
        SharedPreferences sharedPref = activity.getSharedPreferences(CALC_PREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(CALC_PREFERENCES_MODE_NIGHT, modeNight);
        editor.putInt(CALC_PREFERENCES_BACKGROUND, resourceBackground);
        editor.putString(CALC_PREFERENCES_DEC_FORMAT, strDecFormat);
        editor.apply();
    }

    protected CalcSettings(Parcel in) {
        modeNight = in.readInt();
        resourceBackground = in.readInt();
        strDecFormat = in.readString();
    }

    public static final Creator<CalcSettings> CREATOR = new Creator<CalcSettings>() {
        @Override
        public CalcSettings createFromParcel(Parcel in) {
            return new CalcSettings(in);
        }

        @Override
        public CalcSettings[] newArray(int size) {
            return new CalcSettings[size];
        }
    };

    public int getModeNight() {
        return modeNight;
    }

    public void setModeNight(int modeNight) {
        this.modeNight = modeNight;
    }

    public int getResourceBackground() {
        return resourceBackground;
    }

    public void setResourceBackground(int resourceBackground) {
        this.resourceBackground = resourceBackground;
    }

    public String getStrDecFormat() {
        return strDecFormat;
    }

    public void setStrDecFormat(String strDecFormat) {
        this.strDecFormat = strDecFormat;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(modeNight);
        dest.writeInt(resourceBackground);
        dest.writeString(strDecFormat);
    }
}
