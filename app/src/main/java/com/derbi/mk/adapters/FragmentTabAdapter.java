package com.derbi.mk.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.derbi.mk.activity.BaseActivity;
import com.derbi.mk.cnst.Static;
import com.derbi.mk.cnst.Urlz;
import com.derbi.mk.fragment.GenCategoriesFragment;
import com.derbi.mk.utils.LogUtil;

import java.util.ArrayList;

/**
 * Created by varsovski on 01-Jun-15.
 */
public class FragmentTabAdapter extends FragmentStatePagerAdapter {

    private int mCategory;
    private ArrayList<String> mTitles;
    private BaseActivity mActivity;

    public FragmentTabAdapter(FragmentManager fm) {
        super(fm);
    }

    public FragmentTabAdapter(FragmentManager fm, BaseActivity a, ArrayList<String> titles, int category) {
        super(fm);
        this.mActivity = a;
        this.mTitles = titles;
        this.mCategory = category;
    }


    @Override
    public Fragment getItem(int index) {
        String url = "";
        switch (mCategory) {
            case 0:
                url = Urlz.footballUrlz[index];

                break;
            case 1:
                url = Urlz.basketballUrlz[index];
                break;
            case 2:
                url = Urlz.handballUrlz[index];
                break;
            case 3:
                url = Urlz.motoSportUrlz[index];
                break;
            case 4:
                url = Urlz.sportPlusUrlz[index];
                break;
            case 5:
                url = Urlz.tennisUrlz[index];
                break;
            case 6:
                url = Urlz.magazinUrlz[index];
                break;

            default:
                break;
        }

        LogUtil.dLog(Static.GF_TAG, "FragmentTabAdapter | getItem(int index) | url = " + url);
        GenCategoriesFragment fragment = GenCategoriesFragment.newInstance(url, 0, 0);


       return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }


}
