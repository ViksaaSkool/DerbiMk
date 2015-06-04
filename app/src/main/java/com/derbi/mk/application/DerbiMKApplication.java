package com.derbi.mk.application;

import android.app.Application;

import com.derbi.mk.cnst.Static;
import com.derbi.mk.utils.LogUtil;
import com.parse.Parse;
import com.parse.ParseCrashReporting;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.SaveCallback;

import timber.log.Timber;

/**
 * Created by varsovski on 27-May-15.
 */
public class DerbiMKApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        checkDebugging();
        initParseDotCom();
    }

    public void checkDebugging() {
        if (BldConfg.DEBUGGING) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }


    /**
     * A tree which logs important information for crash reporting.
     * decided to go with ParseCrashReporting instead, though
     */
    private static class CrashReportingTree extends Timber.HollowTree {
        @Override
        public void i(String message, Object... args) {

            //Crashlytics.log(String.format(message, args));

        }

        @Override
        public void i(Throwable t, String message, Object... args) {
            i(message, args); // Just add to the log.
        }

        @Override
        public void e(String message, Object... args) {
            i("ERROR: " + message, args); // Just add to the log.
        }

        @Override
        public void e(Throwable t, String message, Object... args) {
            e(message, args);

            //Crashlytics.logException(t);
        }
    }

    /**
     * init Parse.com for push notifications and crash reporting
     */
    public void initParseDotCom(){

        ParseCrashReporting.enable(this);
        //enable push notifications
        Parse.initialize(this, BldConfg.PARSE_ID, BldConfg.PARSE_KEY);
        ParseInstallation parseInstallation = ParseInstallation.getCurrentInstallation();
        parseInstallation.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                LogUtil.dLog(Static.P_TAG, "Installation object saved " + ((e != null) ? "failed" : "successfully"));
            }
        });
    }



}
