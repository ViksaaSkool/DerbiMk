package com.derbi.mk.rss;

import android.content.Context;

import com.derbi.mk.activity.BaseActivity;
import com.derbi.mk.callback.RSSCallback;
import com.derbi.mk.cnst.Static;
import com.derbi.mk.utils.LogUtil;
import com.nanotasks.BackgroundWork;
import com.nanotasks.Completion;
import com.nanotasks.Tasks;
import com.pkmmte.pkrss.Article;
import com.pkmmte.pkrss.PkRSS;
import com.pkmmte.pkrss.parser.Rss2Parser;

import java.util.ArrayList;

/**
 * Created by varsovski on 27-May-15.
 */
public class RSSHelper {

    public static void loadRSS(final BaseActivity ba, final String url) {
        final RSSCallback rssCallback = (RSSCallback) ba.getCurrentFragment();
        Tasks.executeInBackground(ba, new BackgroundWork<ArrayList<Article>>() {
            @Override
            public ArrayList<Article> doInBackground() throws Exception {
                ArrayList<Article> a = removeDuplicates((ArrayList) PkRSS.with(ba).load(url).parser(new Rss2Parser()).get());
                if (a == null || a.size() == 0)
                    return new ArrayList<>();
                else if (a.size() > 10)
                    return new ArrayList<>(a.subList(0, 9));
                else
                    return new ArrayList<>(a.subList(0, a.size() - 1));
            }
        }, new Completion<ArrayList<Article>>() {
            @Override
            public void onSuccess(Context context, ArrayList<Article> result) {
                LogUtil.dLog(Static.RSS_TAG, "RSS loaded");
                rssCallback.onRssLoaded(result);
            }

            @Override
            public void onError(Context context, Exception e) {
                LogUtil.dLog(Static.RSS_TAG, "onError | RSS load failed; Exception = " + e.getMessage());
                rssCallback.onRssLoadFailed();
            }
        });
    }


    public static ArrayList<Article> removeDuplicates(ArrayList<Article> items) {
        ArrayList<Article> l1 = new ArrayList(items);
        ArrayList<Article> l2 = new ArrayList();

        for (Article article1 : l1) {
            boolean exists = false;
            for (int i = 0; i < l2.size(); i++) {
                if (l2.get(i).getTitle().equals(article1.getTitle()))
                    exists = true;
            }
            if (!exists)
                l2.add(article1);
        }

        return l2;
    }
}
