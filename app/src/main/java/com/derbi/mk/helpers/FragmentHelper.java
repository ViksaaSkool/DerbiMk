package com.derbi.mk.helpers;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.derbi.mk.R;
import com.derbi.mk.activity.BaseActivity;
import com.derbi.mk.activity.MainActivity;
import com.derbi.mk.fragment.ArticleFragment;
import com.derbi.mk.fragment.CategoriesFragment;
import com.derbi.mk.fragment.ContactFragment;
import com.derbi.mk.fragment.GenCategoriesFragment;
import com.derbi.mk.fragment.HomeFragment;
import com.derbi.mk.fragmentdialog.AboutDialog;

/**
 * Created by varsovski on 18-Feb-15.
 */
public class FragmentHelper {

    public static final String ARTICLE = "articleFragment";
    public static final String CATEGORIES = "categoriesFragment";
    public static final String HOME = "homeFragment";
    public static final String GEN_CATEGORIES = "generalCategoriesFragment";
    public static final String CONTACT = "contactFragment";
    public static final String ABOUT_DIALOG = "aboutFragmentDialog";


    public static void setHomeFragment(BaseActivity bfa) {
        FragmentTransaction ft = bfa.getSupportFragmentManager().beginTransaction();
        HomeFragment fragment = HomeFragment.newInstance();
        ft.replace(R.id.mainFragment, fragment, HOME);
        ft.commit();
    }

    public static void setContactFragment(BaseActivity bfa) {
        FragmentTransaction ft = bfa.getSupportFragmentManager().beginTransaction();
        ContactFragment fragment = ContactFragment.newInstance();
        ft.replace(R.id.mainFragment, fragment, CONTACT);
        ft.commit();
    }


    public static void setArticleFragment(BaseActivity bfa, String derbiUrl, String fromWhere) {
        FragmentTransaction ft = bfa.getSupportFragmentManager().beginTransaction();
        ArticleFragment fragment = ArticleFragment.newInstance(derbiUrl, fromWhere);
        ft.replace(R.id.mainFragment, fragment, ARTICLE);
        ft.commit();
    }

    public static void setCategoriesFragment(BaseActivity bfa) {
        FragmentTransaction ft = bfa.getSupportFragmentManager().beginTransaction();
        CategoriesFragment fragment = CategoriesFragment.newInstance();
        ft.replace(R.id.mainFragment, fragment, CATEGORIES);
        ft.commit();
    }


    public static void setGenCategories(BaseActivity ba, String url, Integer category, Integer postion){
        FragmentTransaction ft = ba.getSupportFragmentManager().beginTransaction();
        GenCategoriesFragment fragment = GenCategoriesFragment.newInstance(url, category, postion);
        ft.replace(R.id.mainFragment, fragment, GEN_CATEGORIES);
        ft.commit();
    }


    public static void showAboutDialog(MainActivity c) {
        FragmentManager fm = c.getSupportFragmentManager();
        AboutDialog settingsDialog = new AboutDialog();
        settingsDialog.show(fm, ABOUT_DIALOG);
    }

    public static boolean onBckPrsd(MainActivity a) {
        boolean b = false;
        if (a.getNavDrawer().isDrawerOpen())
            a.getNavDrawer().closeDrawers();

        else if (a.getCurrentFragment() instanceof CategoriesFragment || a.getCurrentFragment() instanceof ContactFragment) {
            FragmentHelper.setHomeFragment(a);
            a.getSupportActionBar().setTitle(R.string.app_name);
        } else if (a.getCurrentFragment() instanceof ArticleFragment) {
            if (FragmentHelper.HOME.equals(((ArticleFragment) a.getCurrentFragment()).getFromWhere())){
                FragmentHelper.setHomeFragment(a);
                a.getSupportActionBar().setTitle(R.string.app_name);
            }
            else {
                a.finish();
            }

        } else if (a.getCurrentFragment() instanceof GenCategoriesFragment){
            FragmentHelper.setCategoriesFragment(a);
            a.getSupportActionBar().setTitle(R.string.categories);
        }

        else {
            b = true;
        }

        return b;
    }
}
