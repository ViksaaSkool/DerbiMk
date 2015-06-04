package com.derbi.mk.callback;

import com.pkmmte.pkrss.Article;

import java.util.ArrayList;

/**
 * Created by varsovski on 27-May-15.
 */
public interface RSSCallback {

    void onRssLoaded(ArrayList<Article> rssNews);

    void onRssLoadFailed();
}
