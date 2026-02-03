package com.example.ass1.controller;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ass1.model.Settings;

public class SharedPreferenceHelper {

    private static final String PREFS_NAME = "ass1_prefs";

    // Settings keys
    private static final String KEY_B1 = "button1";
    private static final String KEY_B2 = "button2";
    private static final String KEY_B3 = "button3";
    private static final String KEY_MAX = "maxEvents";

    // Data keys
    private static final String KEY_C1 = "count1";
    private static final String KEY_C2 = "count2";
    private static final String KEY_C3 = "count3";
    private static final String KEY_TOTAL = "total";
    private static final String KEY_HISTORY = "history_csv"; // "1,2,3,..."

    private final SharedPreferences prefs;

    public SharedPreferenceHelper(Context context) {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    // ---------- Settings ----------
    public void saveSettings(Settings s) {
        prefs.edit()
                .putString(KEY_B1, s.getButton1Name())
                .putString(KEY_B2, s.getButton2Name())
                .putString(KEY_B3, s.getButton3Name())
                .putInt(KEY_MAX, s.getMaxEvents())
                .apply();
    }

    public Settings loadSettings() {
        Settings s = new Settings();
        s.setButton1Name(prefs.getString(KEY_B1, ""));
        s.setButton2Name(prefs.getString(KEY_B2, ""));
        s.setButton3Name(prefs.getString(KEY_B3, ""));
        s.setMaxEvents(prefs.getInt(KEY_MAX, 0));
        return s;
    }

    public boolean hasSettings() {
        return loadSettings().isComplete();
    }

    // ---------- Counts ----------
    public int getCount1() { return prefs.getInt(KEY_C1, 0); }
    public int getCount2() { return prefs.getInt(KEY_C2, 0); }
    public int getCount3() { return prefs.getInt(KEY_C3, 0); }
    public int getTotal()  { return prefs.getInt(KEY_TOTAL, 0); }

    public void saveCounts(int c1, int c2, int c3, int total) {
        prefs.edit()
                .putInt(KEY_C1, c1)
                .putInt(KEY_C2, c2)
                .putInt(KEY_C3, c3)
                .putInt(KEY_TOTAL, total)
                .apply();
    }

    // ---------- History ----------
    public String getHistoryCsv() {
        return prefs.getString(KEY_HISTORY, "");
    }

    public void saveHistoryCsv(String csv) {
        prefs.edit().putString(KEY_HISTORY, csv).apply();
    }

    public void clearAllCountsAndHistory() {
        prefs.edit()
                .remove(KEY_C1).remove(KEY_C2).remove(KEY_C3).remove(KEY_TOTAL)
                .remove(KEY_HISTORY)
                .apply();
    }
}
