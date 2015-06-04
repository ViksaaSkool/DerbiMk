package com.derbi.mk.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afollestad.materialdialogs.MaterialDialog;
import com.derbi.mk.R;
import com.derbi.mk.activity.BaseActivity;
import com.derbi.mk.activity.GenCategoriesActivity;
import com.derbi.mk.activity.MainActivity;
import com.derbi.mk.activity.SplashScreenActivity;
import com.derbi.mk.callback.NetworkChange;
import com.derbi.mk.cnst.Static;
import com.derbi.mk.helpers.ChangeActivityHelper;
import com.derbi.mk.utils.UIUtil;
import com.github.johnpersano.supertoasts.SuperToast;

/**
 * Created by varsovski on 27-May-15.
 */
public class BaseFragment extends Fragment implements NetworkChange {


    private BaseActivity mActivity;
    private MaterialDialog mLoading;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        initFragSettings(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);


        if (this instanceof HomeFragment) {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(true);
        } else if (this instanceof CategoriesFragment) {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(false);
        } else if (this instanceof ContactFragment) {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(false);
        } else if (this instanceof ArticleFragment) {
            menu.getItem(0).setVisible(false);
            menu.getItem(1).setVisible(true);
            menu.getItem(2).setVisible(false);
        } else if (this instanceof GenCategoriesFragment) {
            menu.getItem(0).setVisible(true);
            menu.getItem(1).setVisible(false);
            menu.getItem(2).setVisible(true);

        }
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void netChanged(boolean b) {
        if (!b) {
            UIUtil.showSuperToast(getBaseActivity(), R.string.noInternet, SuperToast.Duration.LONG);
        }
    }


    public void initFragSettings(Activity activity) {
        if (activity != null) {
            mActivity = ((BaseActivity) activity);
            mActivity.setCurrentFragment(this);
            if (mActivity instanceof MainActivity) {
                MainActivity mA = ((MainActivity) mActivity);
                if (mA != null && mA.getNavDrawer() != null) {
                    if (this instanceof ArticleFragment) {
                        mA.getNavDrawer().setDrawerState(true);
                        mLoading = UIUtil.getLoadingDialog(mActivity, Static.WEB_REFRESH).build();

                    } else {
                        mA.getNavDrawer().setDrawerState(false);
                        mLoading = UIUtil.getLoadingDialog(mActivity, Static.FEED_REFRESH).build();
                    }


                } else
                    ChangeActivityHelper.changeActivity(activity, SplashScreenActivity.class, true);
            } else {
                GenCategoriesActivity mA = ((GenCategoriesActivity) mActivity);
                if (mA == null)
                    ChangeActivityHelper.changeActivity(activity, SplashScreenActivity.class, true);


            }

        }

    }


    //getters and setters
    public BaseActivity getBaseActivity() {
        return mActivity;
    }

    public MaterialDialog getLoadingDialog() {
        return mLoading;
    }

    public void setLoadingDialog(MaterialDialog mLoading) {
        this.mLoading = mLoading;
    }
}
