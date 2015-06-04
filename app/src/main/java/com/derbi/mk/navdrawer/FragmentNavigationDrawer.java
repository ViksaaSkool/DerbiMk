package com.derbi.mk.navdrawer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.derbi.mk.R;
import com.derbi.mk.activity.MainActivity;
import com.derbi.mk.cnst.Static;
import com.derbi.mk.helpers.FragmentHelper;
import com.derbi.mk.utils.UIUtil;
import com.github.johnpersano.supertoasts.SuperToast;
import com.snappydb.SnappydbException;

import java.util.ArrayList;


/**
 * Created by varsovski on 29-May-15.
 */
public class FragmentNavigationDrawer extends DrawerLayout {
    private ActionBarDrawerToggle drawerToggle;
    private ListView lvDrawer;
    private Toolbar toolbar;
    private NavDrawerListAdapter drawerAdapter;
    private ArrayList<NavDrawerItem> navDrawerItems;
    private ArrayList<FragmentNavItem> drawerNavItems;
    private int drawerContainerRes;
    private MainActivity mActivity;

    public FragmentNavigationDrawer(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public FragmentNavigationDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public FragmentNavigationDrawer(Context context) {
        super(context);

    }

    public void setupDrawerConfiguration(MainActivity activity, ListView drawerListView, Toolbar drawerToolbar,
                                         int drawerItemRes, int drawerContainerResId) {
        mActivity = activity;
        // Setup navigation items array
        drawerNavItems = new ArrayList<FragmentNavigationDrawer.FragmentNavItem>();
        navDrawerItems = new ArrayList<NavDrawerItem>();
        drawerContainerRes = drawerContainerResId;
        // Setup drawer list view
        lvDrawer = drawerListView;
        toolbar = drawerToolbar;
        // Setup item listener
        lvDrawer.setOnItemClickListener(new FragmentDrawerItemListener());
        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        drawerToggle = setupDrawerToggle();
        setDrawerListener(drawerToggle);


    }

    // addNavItem("First", R.mipmap.ic_one, "First Fragment", FirstFragment.class)
    public void addNavItem(String navTitle, int icon, String windowTitle, Class<? extends Fragment> fragmentClass) {
        // adding nav drawer items to array
        navDrawerItems.add(new NavDrawerItem(navTitle, icon));
        // Set the adapter for the list view
        drawerAdapter = new NavDrawerListAdapter(mActivity, navDrawerItems);
        lvDrawer.setAdapter(drawerAdapter);
        drawerNavItems.add(new FragmentNavItem(windowTitle, fragmentClass));
    }

    /**
     * Swaps fragments in the main content view
     */
    public void selectDrawerItem(int position, View view) {
        // Create a new fragment and specify the planet to show based on
        // position
        //
        FragmentNavItem navItem = drawerNavItems.get(position);
        Fragment fragment = null;
        try {
            fragment = navItem.getFragmentClass().newInstance();
            Bundle args = navItem.getFragmentArgs();
            if (args != null) {
                fragment.setArguments(args);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        switch (position) {
            case 0:
                FragmentHelper.setHomeFragment(mActivity);
                break;
            case 1:
                FragmentHelper.setCategoriesFragment(mActivity);
                break;
            case 2:
                if (navDrawerItems.get(2) != null) {
                    if (navDrawerItems.get(2).getIcon() == R.drawable.ic_notifications_off) {
                        navDrawerItems.get(2).setIcon(R.drawable.ic_notifications_on);
                        UIUtil.showSuperToast(mActivity, R.string.notificationsON, SuperToast.Duration.MEDIUM);
                        try {
                            if (mActivity.getDB() != null) {
                                if (!mActivity.getDB().isOpen()) {
                                    mActivity.closeDB();
                                    mActivity.openDB();
                                }
                                mActivity.getDB().putBoolean(Static.NOTF, true);
                            }

                        } catch (SnappydbException e) {
                            e.printStackTrace();
                        }
                    } else if (navDrawerItems.get(2).getIcon() == R.drawable.ic_notifications_on) {
                        navDrawerItems.get(2).setIcon(R.drawable.ic_notifications_off);
                        UIUtil.showSuperToast(mActivity, R.string.notificationsOFF, SuperToast.Duration.MEDIUM);
                        try {
                            if (mActivity.getDB() != null) {
                                if (!mActivity.getDB().isOpen()) {
                                    mActivity.closeDB();
                                    mActivity.openDB();
                                }
                                mActivity.getDB().putBoolean(Static.NOTF, false);
                            }
                        } catch (SnappydbException e) {
                            e.printStackTrace();
                        }
                    }
                }
                drawerAdapter.notifyDataSetChanged();
                break;
            case 3:
                FragmentHelper.setContactFragment(mActivity);
                break;

            case 4:
                FragmentHelper.showAboutDialog(mActivity);
                break;

        }

        // Highlight the selected item, update the title, and close the drawer
        lvDrawer.setItemChecked(position, true);
        setTitle(navItem.getTitle());
        closeDrawer(lvDrawer);
    }


    public ActionBarDrawerToggle getDrawerToggle() {
        return drawerToggle;
    }


    private FragmentActivity getActivity() {
        return mActivity;
    }

    private ActionBar getSupportActionBar() {
        return mActivity.getSupportActionBar();
    }

    private void setTitle(CharSequence title) {
        mActivity.getSupportActionBar().setTitle(title);
    }

    private class FragmentDrawerItemListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectDrawerItem(position, view);
        }
    }

    private class FragmentNavItem {
        private Class<? extends Fragment> fragmentClass;
        private String title;
        private Bundle fragmentArgs;

        public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass) {
            this(title, fragmentClass, null);
        }

        public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass, Bundle args) {
            this.fragmentClass = fragmentClass;
            this.fragmentArgs = args;
            this.title = title;
        }

        public Class<? extends Fragment> getFragmentClass() {
            return fragmentClass;
        }

        public String getTitle() {
            return title;
        }

        public Bundle getFragmentArgs() {
            return fragmentArgs;
        }
    }

    private ActionBarDrawerToggle setupDrawerToggle() {
        return new ActionBarDrawerToggle(getActivity(), this, toolbar, R.string.open, R.string.close);
    }

    public boolean isDrawerOpen() {
        return isDrawerOpen(lvDrawer);
    }


    public void setDrawerState(boolean isEnabled) {
        if (isEnabled) {

            setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            drawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_UNLOCKED);
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.syncState();
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mActivity.onBackPressed();
                }
            });

        } else {
            setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            drawerToggle.onDrawerStateChanged(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            drawerToggle.setDrawerIndicatorEnabled(false);


            //toolbar.setNavigationOnClickListener(null);
            drawerToggle = setupDrawerToggle();
            setDrawerListener(drawerToggle);

            drawerToggle.syncState();
        }
    }


}

