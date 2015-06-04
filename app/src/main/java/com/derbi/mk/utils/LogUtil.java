package com.derbi.mk.utils;

import android.util.Log;

import com.derbi.mk.application.BldConfg;

/**
 * Created by varsovski on 16-Mar-15.
 */
public class LogUtil {

    public static void dLog(String TAG, String message) {
        if (BldConfg.DEBUGGING)
            Log.d(TAG, message);
    }

    public static void eLog(String TAG, String message) {
        if (BldConfg.DEBUGGING)
            Log.e(TAG, message);
    }

    public static void wLog(String TAG, String message) {
        if (BldConfg.DEBUGGING)
            Log.w(TAG, message);
    }

    public static void iLog(String TAG, String message) {
        if (BldConfg.DEBUGGING)
            Log.i(TAG, message);
    }

}
