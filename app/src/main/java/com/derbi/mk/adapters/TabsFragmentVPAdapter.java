package com.derbi.mk.adapters;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.derbi.mk.cnst.Urlz;


public class TabsFragmentVPAdapter extends FragmentStatePagerAdapter {

    private int mPagesCount;
    private int mCategory;


    // private

    public TabsFragmentVPAdapter(FragmentManager fm) {
        super(fm);
    }

    public TabsFragmentVPAdapter(FragmentManager fm, int pCount, int category, Context cnt) {
        super(fm);
        this.mPagesCount = pCount;
        this.mCategory = category;


    }


    @Override
    public Fragment getItem(int index) {
        String prefKey = "";
        switch (mCategory) {
            case 0:
                prefKey = Urlz.footballUrlz[index];

                break;
            case 1:
                prefKey = Urlz.basketballUrlz[index];
                break;
            case 2:
                prefKey = Urlz.handballUrlz[index];
                break;
            case 3:
                prefKey = Urlz.motoSportUrlz[index];
                break;
            case 4:
                prefKey = Urlz.sportPlusUrlz[index];
                break;
            case 5:
                prefKey = Urlz.tennisUrlz[index];
                break;
            case 6:
                prefKey = Urlz.magazinUrlz[index];
                break;

            default:
                break;


        }


        //   return new GenSelCategoryFragment(prefKey);
        return null;

    }

    @Override
    public int getCount() {
        return mPagesCount;

    }


}
