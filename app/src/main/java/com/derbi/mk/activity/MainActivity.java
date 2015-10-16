package com.derbi.mk.activity;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ListView;

import com.astuetz.PagerSlidingTabStrip;
import com.derbi.mk.R;
import com.derbi.mk.cnst.Static;
import com.derbi.mk.cnst.Urlz;
import com.derbi.mk.fragment.ArticleFragment;
import com.derbi.mk.fragment.BaseFragment;
import com.derbi.mk.fragment.CategoriesFragment;
import com.derbi.mk.fragment.ContactFragment;
import com.derbi.mk.fragment.HomeFragment;
import com.derbi.mk.helpers.FragmentHelper;
import com.derbi.mk.navdrawer.FragmentNavigationDrawer;
import com.derbi.mk.rss.RSSHelper;
import com.derbi.mk.utils.LogUtil;
import com.derbi.mk.utils.SocialUtil;
import com.snappydb.SnappydbException;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class MainActivity extends BaseActivity {

    //views
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    /*@InjectView(R.id.vpPager)
    ViewPager mVpPager;*/
    @InjectView(R.id.lvDrawer)
    ListView mLvDrawer;
    @InjectView(R.id.navDrawer)
    FragmentNavigationDrawer mNavDrawer;
    @InjectView(R.id.tabs)
    PagerSlidingTabStrip mTabs;

    //drawer
    public String[] navMenuTitles;
    public TypedArray navMenuIcons;


    //viewpager and tabs related variables
    /*private String[] mTabUrlz;
    private int mCategory, mPosition;
    private ArrayList<String> mTabTitles;*/

    private String fragmentFlag;
    private String articleURl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        setToolbarSettings();
        setDrawer(savedInstanceState);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {

            case R.id.action_home:
                FragmentHelper.setHomeFragment(this);
                if (getSupportActionBar() != null)
                    getSupportActionBar().setTitle(R.string.app_name);
                return true;

            case R.id.action_share:
                if (getCurrentFragment() instanceof ArticleFragment && ((ArticleFragment) getCurrentFragment()).getDerbiRUL() != null)
                    SocialUtil.startArticleVia(this, ((ArticleFragment) getCurrentFragment()).getDerbiRUL());
                return true;

            case R.id.action_refresh:
                if (getCurrentFragment() instanceof HomeFragment) {

                    if (getCurrentFragment().getLoadingDialog() != null && !getCurrentFragment().getLoadingDialog().isShowing()) {
                        getCurrentFragment().getLoadingDialog().show();
                        RSSHelper.loadRSS(this, Urlz.BASE_URL + Urlz.TOP12_NEWS);
                    }

                }
                return true;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mNavDrawer.getDrawerToggle().syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mNavDrawer.getDrawerToggle().onConfigurationChanged(newConfig);
    }


    public void setToolbarSettings() {

        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(R.string.app_name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        fragmentFlag = getExtras(Static.FRAGMENT_FLAG);
        articleURl = getExtras(Static.ARTICLE_URL);
    }


    public void setDrawer(Bundle savedInstanceState) {

        // load slide menu items
        navMenuTitles = getResources().getStringArray(R.array.navDrawerMenu);
        // nav drawer icons from resources
        navMenuIcons = getResources().obtainTypedArray(R.array.navDrawerIcn);
        int notif = 0;

        try {
            if (getDB() == null)
                openDB();

            if (getDB() != null && getDB().isOpen() && getDB().exists(Static.NOTF)) {

                if (getDB().getBoolean(Static.NOTF))
                    notif = R.drawable.ic_notifications_on;
                else
                    notif = R.drawable.ic_notifications_off;
            } else
                notif = R.drawable.ic_notifications_off;


        } catch (SnappydbException e) {
            e.printStackTrace();
            notif = R.drawable.ic_notifications_off;
        }

        // Setup drawer view
        mNavDrawer.setupDrawerConfiguration(this, mLvDrawer, mToolbar, R.layout.drawer_list_item, R.id.mainFragment);

        if (fragmentFlag != null && articleURl != null) {
            FragmentHelper.setArticleFragment(this, articleURl, FragmentHelper.GEN_CATEGORIES);

        } else {
            // Add nav items
            mNavDrawer.addNavItem(navMenuTitles[0], navMenuIcons.getResourceId(0, -1), getResources().getString(R.string.app_name), HomeFragment.class);
            mNavDrawer.addNavItem(navMenuTitles[1], navMenuIcons.getResourceId(1, -1), navMenuTitles[1], CategoriesFragment.class);
            mNavDrawer.addNavItem(navMenuTitles[2], notif, getResources().getString(R.string.app_name), BaseFragment.class);
            mNavDrawer.addNavItem(navMenuTitles[3], navMenuIcons.getResourceId(3, -1), navMenuTitles[3], ContactFragment.class);
            mNavDrawer.addNavItem(navMenuTitles[4], navMenuIcons.getResourceId(4, -1), getResources().getString(R.string.app_name), BaseFragment.class);
            // Select default
            if (savedInstanceState == null) {
                mNavDrawer.selectDrawerItem(0, null);

            }
        }


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (FragmentHelper.onBckPrsd(this))
            super.onBackPressed();
    }

    public FragmentNavigationDrawer getNavDrawer() {
        return mNavDrawer;
    }


    @Override
    protected void onPause() {
        super.onPause();
        storeLastVisitTime();
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void storeLastVisitTime() {
        if (getDB() == null)
            openDB();
        if (getDB() != null) {
            try {
                if (!getDB().isOpen()) {
                    closeDB();
                    openDB();
                }
                LogUtil.dLog(Static.N_TAG, "storeLastVisitTime() | System.currentTimeMillis() = " + System.currentTimeMillis());
                getDB().putLong(Static.DATE, System.currentTimeMillis());
                LogUtil.dLog(Static.N_TAG, "storeLastVisitTime() | getDB().putLong() ");

            } catch (SnappydbException e) {
                e.printStackTrace();
                LogUtil.dLog(Static.N_TAG, "storeLastVisitTime() | db error = " + e.getMessage());
            }
        }
    }

    /*@SuppressWarnings("unchecked")
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

    }*/


}
