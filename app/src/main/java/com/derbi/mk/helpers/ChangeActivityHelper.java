/**
 *
 */
package com.derbi.mk.helpers;


import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

import com.derbi.mk.cnst.Static;


public class ChangeActivityHelper implements OnClickListener {

    private Activity _source;
    private Class<?> _destination;
    private boolean _finishActivity;

    public ChangeActivityHelper(Activity source, Class<?> destination) {
        super();
        _source = source;
        _destination = destination;
        _finishActivity = false;
    }

    public ChangeActivityHelper(Activity source, Class<?> destination, boolean finishActivity) {
        this(source, destination);
        _finishActivity = finishActivity;
    }

    @Override
    public void onClick(View v) {
        changeActivity(_source, _destination, _finishActivity);
    }

    public static void changeActivity(Activity source, Class<?> destination) {
        changeActivity(source, destination, false);
    }

    public static void changeActivity(Activity source, Class<?> destination, Boolean finishContext) {
        if (finishContext)
            source.finish();

        Intent iTransition = new Intent(source, destination);
        source.startActivity(iTransition);
    }


    public static void changeActivityWithExtra(Activity source, Class<?> destination, Boolean finishContext, int c, int p) {
        if (finishContext)
            source.finish();

        Intent iTransition = new Intent(source, destination);
        iTransition.putExtra(Static.CATEGORY_TYPE, c);
        iTransition.putExtra(Static.CATEGORY_POSITION, p);

        source.startActivity(iTransition);
    }

    public static void changeActivityMain(Activity source, Class<?> destination, Boolean finishContext, String url, String flag) {
        if (finishContext)
            source.finish();

        Intent iTransition = new Intent(source, destination);
        iTransition.putExtra(Static.FRAGMENT_FLAG, flag);
        iTransition.putExtra(Static.ARTICLE_URL, url);
        source.startActivity(iTransition);
    }

}
