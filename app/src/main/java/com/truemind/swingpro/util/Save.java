package com.truemind.swingpro.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import org.json.JSONArray;

import java.util.Set;

/**
 * Created by Hyunseok on 2017-08-15.
 */

public final class Save {

    private static SharedPreferences SP;
    public static final String DATASET_RECORD = "dr";
    public static final String FISRT_DATE = "fd";
    public static final String BEST_SCORE = "bs";
    public static final String AVG_SCORE = "as";
    public static final String COUNT_TIMES = "ct";



    private static SharedPreferences instance(Context context) {
        if (SP == null) {
            synchronized (Save.class) {
                if (SP == null) {
                    SP = context.getSharedPreferences("save", Context.MODE_PRIVATE);
                }
            }
        }
        return SP;
    }


    public static void dataSetRecord(Context context, @Nullable String data) {
        instance(context).edit().putString(DATASET_RECORD, data).apply();
    }

    public static String dataSetRecord(Context context) {
        return instance(context).getString(DATASET_RECORD, "0");
    }

    public static void firstDate(Context context, @Nullable String date) {
        instance(context).edit().putString(FISRT_DATE, date).apply();
    }

    public static String firstDate(Context context) {
        return instance(context).getString(FISRT_DATE, "0");
    }

    public static void avgScore(Context context, float score) {
        instance(context).edit().putFloat(AVG_SCORE, score).apply();
    }

    public static float avgScore(Context context) {
        return instance(context).getFloat(AVG_SCORE, 999999999);
    }

    public static void bestScore(Context context, float score) {
        instance(context).edit().putFloat(BEST_SCORE, score).apply();
    }

    public static float bestScore(Context context) {
        return instance(context).getFloat(BEST_SCORE, 999999999);
    }

    public static void countTime(Context context, int count) {
        instance(context).edit().putInt(COUNT_TIMES, count).apply();
    }

    public static int countTime(Context context) {
        return instance(context).getInt(COUNT_TIMES, 0);
    }
}
