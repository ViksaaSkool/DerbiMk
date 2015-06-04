package com.derbi.mk.notifications;

import android.content.Context;
import android.content.Intent;

import com.derbi.mk.cnst.Static;
import com.derbi.mk.utils.LogUtil;
import com.parse.ParsePushBroadcastReceiver;
import com.snappydb.DB;
import com.snappydb.DBFactory;
import com.snappydb.SnappydbException;

/**
 * Created by varsovski on 04-Jun-15.
 */
public class NotifReceiver extends ParsePushBroadcastReceiver {

    /**
     * do not process the received message if the
     * user turned off the notifications
     * else do as expected
     * **/
    @Override
    protected void onPushReceive(Context context, Intent intent) {
        LogUtil.dLog(Static.P_TAG, "onPushReceive");
        try {
            DB db = DBFactory.open(context);
            if (db.exists(Static.NOTF) && db.getBoolean(Static.NOTF)) {
                LogUtil.dLog(Static.P_TAG, "onPushReceive | db.getBoolean(Static.NOTF) = " + db.getBoolean(Static.NOTF));
                super.onPushReceive(context, intent);
            } else {
                if (db.exists(Static.NOTF))
                    LogUtil.dLog(Static.P_TAG, "onPushReceive | db.getBoolean(Static.NOTF) = " + db.getBoolean(Static.NOTF));
                else
                    LogUtil.dLog(Static.P_TAG, "onPushReceive | db.exists(Static.NOTF) = " + db.exists(Static.NOTF));
            }
            db.close();
        } catch (SnappydbException e) {
            e.printStackTrace();
            LogUtil.dLog(Static.P_TAG, "onPushReceive | error = " + e.getMessage());
        }
    }
}
