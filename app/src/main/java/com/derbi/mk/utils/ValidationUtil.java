package com.derbi.mk.utils;

import com.pkmmte.pkrss.Article;

import java.util.ArrayList;

/**
 * Created by varsovski on 01-Jun-15.
 */
public class ValidationUtil {


    public static boolean vRSSList(ArrayList<Article> oldList, ArrayList<Article> newList) {
        boolean r = false;

        if ((oldList == null) ||
                (oldList != null && oldList.isEmpty()) ||
                (oldList != null && !oldList.isEmpty() && oldList.get(0) != null
                        && oldList.get(0).getTitle() != null && newList != null
                        && !newList.isEmpty() && newList.get(0) != null
                        && newList.get(0).getTitle() != null
                        && !oldList.get(0).getTitle().equals(newList.get(0).getTitle())))
            r = true;

            return r;
    }
}
