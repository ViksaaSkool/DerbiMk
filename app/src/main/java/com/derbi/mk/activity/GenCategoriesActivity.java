package com.derbi.mk.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.derbi.mk.R;
import com.derbi.mk.adapters.FragmentTabAdapter;
import com.derbi.mk.adapters.TabsFragmentVPAdapter;
import com.derbi.mk.cnst.Static;
import com.derbi.mk.cnst.Urlz;
import com.derbi.mk.fragment.GenCategoriesFragment;
import com.derbi.mk.helpers.ChangeActivityHelper;
import com.derbi.mk.rss.RSSHelper;
import com.derbi.mk.utils.LogUtil;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by varsovski on 01-Jun-15.
 */
public class GenCategoriesActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.tabs)
    PagerSlidingTabStrip mTabs;
    @InjectView(R.id.vpPager)
    ViewPager mVpPager;


    private TabsFragmentVPAdapter mTabsAdapter;
    private String[] mTabUrlz;
    private boolean firstTime = true;


    private int mCategory, mPosition;
    private ArrayList<String> mTabTitles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic_category);
        ButterKnife.inject(this);

        setToolbarSettings();
        mCategory = getExtras(Static.CATEGORY_TYPE);
        mPosition = getExtras(Static.CATEGORY_POSITION);

        LogUtil.dLog(Static.GF_TAG, "GenCategoriesActivity | mCategory = " + mCategory + " mPosition = " + mPosition);
        initPagerAndTabs();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


    public void initPagerAndTabs() {
        loadTitlesForPager(mCategory);

        mVpPager.setAdapter(new FragmentTabAdapter(getSupportFragmentManager(), this, mTabTitles, mCategory));
        mTabs.setViewPager(mVpPager);


        mVpPager.setCurrentItem(mPosition);
        mVpPager.setOffscreenPageLimit(0);


        mTabs.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                //  FragmentHelper.setGenCategories(ba, mTabUrlz[i], null, null);
            }

            @Override
            public void onPageSelected(int i) {
                //  FragmentHelper.setGenCategories(ba, mTabUrlz[i], null, null);
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });

        firstTime = false;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.action_home:
                ChangeActivityHelper.changeActivity(this, MainActivity.class, true);
                return true;

            case R.id.action_refresh:
                if (getCurrentFragment() != null && getCurrentFragment() instanceof GenCategoriesFragment) {
                    GenCategoriesFragment fr = (GenCategoriesFragment) getCurrentFragment();
                    if (!fr.isLoading()) {
                        RSSHelper.loadRSS(this, ((GenCategoriesFragment) getCurrentFragment()).getCategoryRss());
                        fr.setLoading(true);
                    }
                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    public void setToolbarSettings() {

        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(R.string.categories);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void loadTitlesForPager(int c) {
        mCategory = c;
        mTabTitles = new ArrayList<String>();

        switch (mCategory) {
            case 0:
                mTabTitles = new ArrayList(Arrays.asList(getResources()
                        .getStringArray(R.array.fudbalArr)));
                mTabTitles.add(0, getResources().getString(R.string.fudbal));
                mTabUrlz = Urlz.footballUrlz;
                break;

            case 1:
                mTabTitles = new ArrayList(Arrays.asList(getResources()
                        .getStringArray(R.array.kosharkaArr)));
                mTabTitles.add(0, getResources().getString(R.string.kosharka));
                mTabUrlz = Urlz.basketballUrlz;
                break;
            case 2:
                mTabTitles = new ArrayList(Arrays.asList(getResources()
                        .getStringArray(R.array.rakometArr)));
                mTabTitles.add(0, getResources().getString(R.string.rakomet));
                mTabUrlz = Urlz.handballUrlz;
                break;
            case 3:
                mTabTitles = new ArrayList(Arrays.asList(getResources()
                        .getStringArray(R.array.motosportArr)));
                mTabTitles.add(0, getResources().getString(R.string.motosport));
                mTabUrlz = Urlz.motoSportUrlz;
                break;
            case 4:
                mTabTitles = new ArrayList(Arrays.asList(getResources()
                        .getStringArray(R.array.sportPlusArr)));
                mTabTitles.add(0, getResources().getString(R.string.sportPlus));
                mTabUrlz = Urlz.sportPlusUrlz;
                break;
            case 5:
                mTabTitles = new ArrayList(Arrays.asList(getResources()
                        .getStringArray(R.array.tenisArr)));
                mTabTitles.add(0, getResources().getString(R.string.tenis));
                mTabUrlz = Urlz.tennisUrlz;
                break;
            case 6:
                mTabTitles = new ArrayList(Arrays.asList(getResources()
                        .getStringArray(R.array.magazinArr)));
                mTabTitles.add(0, getResources().getString(R.string.magazin));
                mTabUrlz = Urlz.magazinUrlz;
                break;

            default:
                break;
        }

    }

    public int getCategory() {
        return mCategory;
    }

    public int getPosition() {
        return mPosition;
    }

}
