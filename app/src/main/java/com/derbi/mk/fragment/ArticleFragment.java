package com.derbi.mk.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.derbi.mk.R;
import com.derbi.mk.activity.MainActivity;
import com.derbi.mk.cnst.Static;
import com.derbi.mk.helpers.FragmentHelper;
import com.derbi.mk.utils.UIUtil;
import com.github.johnpersano.supertoasts.SuperToast;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by varsovski on 27-May-15.
 */
public class ArticleFragment extends BaseFragment {

    @InjectView(R.id.wvArticle)
    WebView mWvArticle;

    private String mDerbiRUL;
    private String mFromWhere;

    public static ArticleFragment newInstance() {
        ArticleFragment fragment = new ArticleFragment();
        return fragment;
    }

    public static ArticleFragment newInstance(String derbiUrl, String fromWhere) {
        ArticleFragment fragment = new ArticleFragment();
        Bundle args = new Bundle();
        args.putString(Static.DERBI_URL, derbiUrl);
        args.putString(Static.FROM_WHERE, fromWhere);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (getBaseActivity() != null && getArguments() != null) {
            mDerbiRUL = (String) getArguments().get(Static.DERBI_URL);
            mFromWhere = (String) getArguments().get(Static.FROM_WHERE);

            if (getBaseActivity().getSupportActionBar() != null && mFromWhere != null) {
                getBaseActivity().getSupportActionBar().setTitle(R.string.article);

                if (FragmentHelper.HOME.equals(mFromWhere)) {
                    getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                    getBaseActivity().getSupportActionBar().setHomeButtonEnabled(false);
                    getBaseActivity().getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                } else if (activity != null && ((MainActivity) activity).getNavDrawer() != null
                        && ((MainActivity) activity).getNavDrawer().getDrawerToggle() != null) {
                    ((MainActivity) activity).getNavDrawer().getDrawerToggle().setDrawerIndicatorEnabled(false);
                    ((MainActivity) activity).getNavDrawer().getDrawerToggle().setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
                }

            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_article, container, false);
        ButterKnife.inject(this, v);

        initWebView();

        return v;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    public void initWebView() {

        if (mDerbiRUL != null && !mDerbiRUL.isEmpty()) {
            if (getLoadingDialog() != null && !getLoadingDialog().isShowing())
                getLoadingDialog().show();


            mWvArticle.loadUrl(mDerbiRUL);

            WebSettings webSettings = mWvArticle.getSettings();
            webSettings.setBuiltInZoomControls(true);
            webSettings.setDisplayZoomControls(false);
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);


            mWvArticle.setWebViewClient(new WebViewClient() {

                public void onPageFinished(WebView view, String url) {
                    if (getLoadingDialog() != null && getLoadingDialog().isShowing())
                        getLoadingDialog().dismiss();
                }
            });
        } else {
            UIUtil.showSuperToast(getBaseActivity(), R.string.smtWentWrong, SuperToast.Duration.SHORT);
        }

    }

    //getters
    public String getDerbiRUL() {
        return mDerbiRUL;
    }

    public String getFromWhere() {
        return mFromWhere;
    }
}
