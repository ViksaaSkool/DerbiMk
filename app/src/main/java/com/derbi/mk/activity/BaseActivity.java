package com.derbi.mk.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.derbi.mk.callback.NetworkChange;
import com.derbi.mk.cnst.Static;
import com.derbi.mk.fragment.BaseFragment;
import com.derbi.mk.utils.ConnectivityUtil;
import com.derbi.mk.utils.LogUtil;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

/**
 * Created by varsovski on 27-May-15.
 */
public class BaseActivity extends ActionBarActivity {

    private DB mDB;
    public NetReceiver netReceiver;
    private BaseFragment mCurrentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        openDB();

    }

    @Override
    protected void onResume() {
        super.onResume();

        netReceiver = new NetReceiver();
        registerReceiver(netReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(netReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeDB();

    }

    //check if there is Internet Connection and change the UI
    private class NetReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (context != null && intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {

                boolean isConnected = false;
                if (context != null) {
                    isConnected = ConnectivityUtil.isConnected(context);
                    if (getCurrentFragment() != null) {
                        NetworkChange nChange = (NetworkChange) getCurrentFragment();
                        nChange.netChanged(isConnected);
                    }
                }
            }
        }
    }


    //getters and setters
    public BaseFragment getCurrentFragment() {
        return mCurrentFragment;
    }

    public void setCurrentFragment(BaseFragment currentFragment) {
        this.mCurrentFragment = currentFragment;
    }


    //DB related
    public void openDB() {
        try {
            if (mDB == null)
                mDB = DBFactory.open(this);
            else if (!mDB.isOpen())
                mDB = DBFactory.open(this);
        } catch (SnappydbException e) {
            LogUtil.eLog(Static.DB_TAG, "openDB() | error: " + e.getMessage());
        }
    }

    public void closeDB() {
        try {
            if (mDB != null && mDB.isOpen())
                mDB.close();
        } catch (SnappydbException e) {
            LogUtil.eLog(Static.DB_TAG, "closeDB() | error: " + e.getMessage());
        }
    }


    // getters and setters
    public DB getDB() {
        return mDB;
    }

    public void setDB(DB mDB) {
        this.mDB = mDB;
    }



    //getExtras
    public <T> T getExtras(String name) {
        T result = null;
        Bundle extras = this.getIntent().getExtras();

        if (extras != null) {
            try {
                result = (T) extras.get(name);
            } finally {
            }
        }

        return result;
    }

}
