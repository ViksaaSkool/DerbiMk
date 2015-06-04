package com.derbi.mk.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.derbi.mk.R;
import com.derbi.mk.activity.GenCategoriesActivity;
import com.derbi.mk.activity.SplashScreenActivity;
import com.derbi.mk.adapters.NewsAdapter;
import com.derbi.mk.callback.RSSCallback;
import com.derbi.mk.cnst.Static;
import com.derbi.mk.cnst.Urlz;
import com.derbi.mk.helpers.ChangeActivityHelper;
import com.derbi.mk.rss.RSSHelper;
import com.derbi.mk.utils.LogUtil;
import com.derbi.mk.utils.SocialUtil;
import com.derbi.mk.utils.UIUtil;
import com.derbi.mk.utils.ValidationUtil;
import com.derbi.mk.widgets.QuickReturnRecyclerView;
import com.github.johnpersano.supertoasts.SuperToast;
import com.pkmmte.pkrss.Article;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by varsovski on 27-May-15.
 */
public class GenCategoriesFragment extends BaseFragment implements RSSCallback, View.OnClickListener {


    @InjectView(R.id.qrRecyclerView)
    QuickReturnRecyclerView mQrRecyclerView;
    @InjectView(R.id.bbHomeFacebook)
    BootstrapButton mBbHomeFacebook;
    @InjectView(R.id.bbHomeTwitter)
    BootstrapButton mBbHomeTwitter;


    private ArrayList<Article> mArticles;
    private ArrayList<Article> _articles;
    private LinearLayoutManager mLinearLayoutManager;
    private NewsAdapter mNewsRecyclerAdapter;


    private String mCategoryRssURL;
    private boolean loading = false;


    public static GenCategoriesFragment newInstance(String categoriesUrl, int categoryType, int position) {
        GenCategoriesFragment fragment = new GenCategoriesFragment();
        Bundle args = new Bundle();
        args.putString(Static.CATEGORY_URL, categoriesUrl);
        args.putInt(Static.CATEGORY_TYPE, categoryType);
        args.putInt(Static.CATEGORY_POSITION, position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_gen_article, container, false);
        ButterKnife.inject(this, v);

        init();

        return v;
    }


    @Override
    public void onResume() {
        super.onResume();


        LogUtil.dLog(Static.GF_TAG, "GenCategoriesFragment | (mQrRecyclerView != null): " + (mQrRecyclerView != null));
        LogUtil.dLog(Static.GF_TAG, "GenCategoriesFragment | (mArticles == null): " + (mArticles == null));
        LogUtil.dLog(Static.GF_TAG, "GenCategoriesFragment | loading: " + (loading == false));
        LogUtil.dLog(Static.GF_TAG, "GenCategoriesFragment | (getBaseActivity() != null): " + (getBaseActivity() != null));


        if (mQrRecyclerView != null && loading == false && mArticles == null) {
            RSSHelper.loadRSS(getBaseActivity(), mCategoryRssURL);
            loading = true;
        } else if (mQrRecyclerView == null && getBaseActivity() != null) {
            GenCategoriesActivity ga = (GenCategoriesActivity) getBaseActivity();
            ChangeActivityHelper.changeActivityWithExtra(ga, GenCategoriesActivity.class, true, ga.getCategory(), ga.getPosition());
        } else if (getBaseActivity() == null)
            ChangeActivityHelper.changeActivity(getActivity(), SplashScreenActivity.class, true);
    }


    @Override
    public void onRssLoaded(ArrayList<Article> articles) {
        loading = false;
        if (mQrRecyclerView != null) {
            LogUtil.dLog(Static.RSS_TAG, "onRssLoaded(ArrayList<Article> articles)");
            if (articles != null) {
                _articles = articles;

                if (ValidationUtil.vRSSList(mArticles, _articles)) {
                    mArticles = articles;
                    mNewsRecyclerAdapter = new NewsAdapter(getBaseActivity(), mArticles);
                    mQrRecyclerView.setAdapter(mNewsRecyclerAdapter);
                } else {
                    UIUtil.showSuperToast(getBaseActivity(), R.string.noNews, SuperToast.Duration.VERY_SHORT);
                }
            } else {
                UIUtil.showSuperToast(getBaseActivity(), R.string.slowInternet, SuperToast.Duration.SHORT);
                LogUtil.dLog(Static.GF_TAG, "slowInternet | articles != null = " + (articles != null));
                LogUtil.dLog(Static.GF_TAG, "slowInternet | !articles.isEmpty() = " + (!articles.isEmpty()));
                LogUtil.dLog(Static.GF_TAG, "slowInternet | mQrRecyclerView != null = " + (mQrRecyclerView != null));
                getLoadingDialog().dismiss();
            }
        }
        //dismiss the dialog; not in use
        if (getLoadingDialog() != null && getLoadingDialog().isShowing())
            getLoadingDialog().dismiss();

    }

    @Override
    public void onRssLoadFailed() {
        loading = false;
        LogUtil.dLog(Static.RSS_TAG, "onRssLoadFailed()");

        //dismiss the dialog
        if (getLoadingDialog() != null && getLoadingDialog().isShowing())
            getLoadingDialog().dismiss();

        UIUtil.showSuperToast(getBaseActivity(), R.string.smtWentWrong, SuperToast.Duration.MEDIUM);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);

    }


    public void init() {
        if (getArguments() != null && getArguments().get(Static.CATEGORY_URL) != null) {
            mCategoryRssURL = Urlz.BASE_URL + getArguments().get(Static.CATEGORY_URL);

            LogUtil.dLog(Static.GF_TAG, "init() | mCategoryRssURL = " + mCategoryRssURL);

            mLinearLayoutManager = new LinearLayoutManager(getBaseActivity());
            mQrRecyclerView.setLayoutManager(mLinearLayoutManager);

            mBbHomeFacebook.setOnClickListener(this);
            mBbHomeTwitter.setOnClickListener(this);

            //not in use
            if (getLoadingDialog() == null)
                setLoadingDialog(UIUtil.getLoadingDialog(getBaseActivity(), Static.FEED_REFRESH).build());
            if (getLoadingDialog().isShowing())
                getLoadingDialog().dismiss();

        } else
            ChangeActivityHelper.changeActivity(getBaseActivity(), SplashScreenActivity.class, true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bbHomeFacebook:
                SocialUtil.openFacebookIntent(getBaseActivity());
                break;
            case R.id.bbHomeTwitter:
                SocialUtil.openTwitterAccount(getBaseActivity());
                break;
            default:
                break;
        }

    }


    //getters and setters
    public String getCategoryRss() {
        return mCategoryRssURL;
    }

    public boolean isLoading() {
        return loading;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }
}
