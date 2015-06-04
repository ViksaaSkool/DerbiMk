package com.derbi.mk.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.derbi.mk.R;
import com.derbi.mk.cnst.Static;
import com.derbi.mk.cnst.Urlz;

/**
 * Created by varsovski on 28-May-15.
 */
public class SocialUtil {

    public static void openFacebookIntent(Context c) {
        Intent intent = new Intent();
        try {
            c.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/" + Static.FACEBOOK_ID));
        } catch (Exception e) {
            intent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.facebook.com/" + Static.FACEBOOK_URI));
        }

        c.startActivity(intent);
    }

    public static void openTwitterAccount(Context c) {
        Intent intent = new Intent();
        try {
            // get the Twitter app if possible
            c.getPackageManager().getPackageInfo("com.twitter.android", 0);
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("twitter://user?user_id=" + Static.TWITTER_ID));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            // no Twitter app, revert to browser
            intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/" + Static.TWITTER_URI));
        }

        c.startActivity(intent);
    }

    public static void sendMailTo(Context c, String address) {

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Static.MAILTO + address));
        intent.putExtra(Intent.EXTRA_SUBJECT, c.getResources().getString(R.string.mailTitle));
        intent.putExtra(Intent.EXTRA_TEXT, c.getResources().getString(R.string.mailTextAddition));
        c.startActivity(Intent.createChooser(intent, c.getResources().getString(R.string.mailName)));

    }

    public static void startArticleVia(Context c, String extra){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, extra + " " + Static.VIA_DERBI);
        c.startActivity(Intent.createChooser(intent, c.getResources().getString(R.string.shareNews)));
    }

    public static void openLinkInBrowser(Context c, String url){
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        c.startActivity(browserIntent);
    }

    public static void openAppInPlay(Context c){
        final String appPackageName = c.getPackageName();
        try {
            c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Urlz.PLAY_M + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            c.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Urlz.PLAY_URL + appPackageName)));
        }
    }
}
