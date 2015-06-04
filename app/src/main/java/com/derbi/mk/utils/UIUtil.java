package com.derbi.mk.utils;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

import com.afollestad.materialdialogs.MaterialDialog;
import com.derbi.mk.R;
import com.derbi.mk.activity.BaseActivity;
import com.derbi.mk.cnst.Static;
import com.github.johnpersano.supertoasts.SuperToast;

import java.util.Date;

/**
 * Created by varsovski on 29-Apr-15.
 */
public class UIUtil {

    public static int containerHeight(BaseActivity ba) {
        DisplayMetrics dm = new DisplayMetrics();
        ba.getWindowManager().getDefaultDisplay().getMetrics(dm);

        LogUtil.dLog(Static.HEIGHT_TAG, "Screen Height of " + Build.MANUFACTURER + " " + Build.DEVICE + " "
                + Build.MODEL + " is " + Integer.toString(dm.heightPixels));

        double ratio = Static.PIC_RATIO_VALUE;

        return (int) (dm.heightPixels / ratio);
    }


    public static void showSuperToast(Context c, int id, int duration) {

        SuperToast superToast = new SuperToast(c);
        superToast.setDuration(duration);
        superToast.setText(c.getResources().getString(id));

        superToast.setBackground(R.color.derbiPrimaryGray);
        superToast.setTextColor(c.getResources().getColor(R.color.derbiBlue));
        superToast.setAnimations(SuperToast.Animations.POPUP);
        superToast.show();
    }

    public static MaterialDialog.Builder getLoadingDialog(Context c, int natureFlag) {
        MaterialDialog.Builder md = null;
        if (natureFlag == Static.FEED_REFRESH)
        md = new MaterialDialog.Builder(c)
                .title(R.string.pleaseWait)
                .content(R.string.fetchNews)
                .cancelable(false)
                .backgroundColor(c.getResources().getColor(R.color.derbiPrimaryGray))
                .itemColor(c.getResources().getColor(R.color.derbiBlue))
                .contentColor(c.getResources().getColor(R.color.White))
                .titleColor(c.getResources().getColor(R.color.derbiBlue))
                .dividerColor(c.getResources().getColor(R.color.White))
                .progress(true, 0);
        else
            md = new MaterialDialog.Builder(c)
                    .title(R.string.pleaseWait)
                    .content(R.string.fetchNews)
                    .cancelable(false)
                    .backgroundColor(c.getResources().getColor(R.color.derbiPrimaryGray))
                    .itemColor(c.getResources().getColor(R.color.derbiBlue))
                    .contentColor(c.getResources().getColor(R.color.White))
                    .titleColor(c.getResources().getColor(R.color.derbiBlue))
                    .dividerColor(c.getResources().getColor(R.color.White))
                    .cancelable(true)
                    .progress(true, 0);

        return md;
    }


    public static String shortDsc(String description) {
        return description.substring(0, Static.CHAR_NO) + "...";
    }

    public static String formatDate(long date) {

        Date d = new Date (date);
        android.text.format.DateFormat dateFormat = new android.text.format.DateFormat();
        return dateFormat.format("dd.MM.yyyy, kk:mm", d).toString();

    }



}
