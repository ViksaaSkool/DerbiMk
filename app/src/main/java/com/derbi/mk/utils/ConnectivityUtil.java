package com.derbi.mk.utils;

/**
 * Created by varsovski on 03-Feb-15.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class ConnectivityUtil {

    /**
     * Get the network info
     * @param context
     * @return
     */
    public static NetworkInfo getNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo();
    }

    /**
     * Check if there is any connectivity
     * @param context
     * @return
     */
    public static boolean isConnected(Context context){
        NetworkInfo info = ConnectivityUtil.getNetworkInfo(context);
        return (info != null && info.isConnected());
    }

    /**
     * Check if there is any connectivity to a Wifi network
     * @param context
     * @return
     */
    public static boolean isConnectedWifi(Context context){
        NetworkInfo info = ConnectivityUtil.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_WIFI);
    }

    /**
     * Check if there is any connectivity to a mobile network
     * @param context
     * @return
     */
    public static boolean isConnectedMobile(Context context){
        NetworkInfo info = ConnectivityUtil.getNetworkInfo(context);
        return (info != null && info.isConnected() && info.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * Check if there is fast connectivity
     * @param context
     * @return
     */
    public static boolean isConnectedFast(Context context){
        NetworkInfo info = ConnectivityUtil.getNetworkInfo(context);
        return (info != null && info.isConnected() && ConnectivityUtil.isConnectionFast(context, info.getType(),info.getSubtype()));
    }

    /**
     * Check if the connection is fast
     * @param type
     * @param subType
     * @return
     */
    public static boolean isConnectionFast(Context c, int type, int subType){
        if(type== ConnectivityManager.TYPE_WIFI){
            Toast.makeText(c, "TYPE_WIFI", Toast.LENGTH_LONG).show();
            return true;
        }else if(type== ConnectivityManager.TYPE_MOBILE){
            switch(subType){
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    Toast.makeText(c, "NETWORK_TYPE_1xRTT", Toast.LENGTH_LONG).show();
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    Toast.makeText(c, "NETWORK_TYPE_CDMA", Toast.LENGTH_LONG).show();
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    Toast.makeText(c, "NETWORK_TYPE_EDGE", Toast.LENGTH_LONG).show();
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    Toast.makeText(c, "NETWORK_TYPE_EVDO_0", Toast.LENGTH_LONG).show();
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    Toast.makeText(c, "NETWORK_TYPE_EVDO_A", Toast.LENGTH_LONG).show();
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    Toast.makeText(c, "NETWORK_TYPE_GPRS", Toast.LENGTH_LONG).show();
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    Toast.makeText(c, "NETWORK_TYPE_HSDPA", Toast.LENGTH_LONG).show();
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    Toast.makeText(c, "NETWORK_TYPE_HSPA", Toast.LENGTH_LONG).show();
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    Toast.makeText(c, "NETWORK_TYPE_HSUPA", Toast.LENGTH_LONG).show();
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    Toast.makeText(c, "NETWORK_TYPE_UMTS", Toast.LENGTH_LONG).show();
                    return true; // ~ 400-7000 kbps
            /*
             * Above API level 7, make sure to set android:targetSdkVersion
             * to appropriate level to use these
             */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    Toast.makeText(c, "NETWORK_TYPE_EHRPD", Toast.LENGTH_LONG).show();
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    Toast.makeText(c, "NETWORK_TYPE_EVDO_B", Toast.LENGTH_LONG).show();
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    Toast.makeText(c, "NETWORK_TYPE_HSPAP", Toast.LENGTH_LONG).show();
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    Toast.makeText(c, "NETWORK_TYPE_IDEN", Toast.LENGTH_LONG).show();
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    Toast.makeText(c, "NETWORK_TYPE_LTE", Toast.LENGTH_LONG).show();
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                    Toast.makeText(c, "NETWORK_TYPE_UNKNOWN", Toast.LENGTH_LONG).show();
                default:
                    return false;
            }
        }else{
            return false;
        }
    }



    public static String getNetworkClass(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if(info==null || !info.isConnected())
            return "NOT CONNECTED";
        if(info.getType() == ConnectivityManager.TYPE_WIFI)
            return "WIFI";
        if(info.getType() == ConnectivityManager.TYPE_MOBILE){
            int networkType = info.getSubtype();
            switch (networkType) {
                case TelephonyManager.NETWORK_TYPE_GPRS:
                case TelephonyManager.NETWORK_TYPE_EDGE:
                case TelephonyManager.NETWORK_TYPE_CDMA:
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                case TelephonyManager.NETWORK_TYPE_IDEN:
                    return "2G";
                case TelephonyManager.NETWORK_TYPE_UMTS:
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                case TelephonyManager.NETWORK_TYPE_HSPA:
                case TelephonyManager.NETWORK_TYPE_EVDO_B:
                case TelephonyManager.NETWORK_TYPE_EHRPD:
                case TelephonyManager.NETWORK_TYPE_HSPAP:
                    return "3G";
                case TelephonyManager.NETWORK_TYPE_LTE:
                    return "4G";
                default:
                    return "UNKNOWN?";
            }
        }
        return "UNKNOWN?";
    }


}

