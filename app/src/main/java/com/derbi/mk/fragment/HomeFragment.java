package com.derbi.mk.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.derbi.mk.R;
import com.derbi.mk.adapters.NewsAdapter;
import com.derbi.mk.callback.RSSCallback;
import com.derbi.mk.cnst.Static;
import com.derbi.mk.cnst.Urlz;
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
public class HomeFragment extends BaseFragment implements RSSCallback, View.OnClickListener {

    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }


    @InjectView(R.id.qrRecyclerView)
    QuickReturnRecyclerView mQrRecyclerView;
    @InjectView(R.id.bbHomeFacebook)
    BootstrapButton mBbHomeFacebook;
    @InjectView(R.id.bbHomeTwitter)
    BootstrapButton mBbHomeTwitter;
    @InjectView(R.id.rlBottom)
    LinearLayout mRlBottom;


    private ArrayList<Article> mArticles;
    private ArrayList<Article> _articles;
    private LinearLayoutManager mLinearLayoutManager;
    private NewsAdapter mNewsRecyclerAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_gen_article, container, false);

        ButterKnife.inject(this, v);
        initUI();

        return v;

    }


    @Override
    public void onResume() {
        super.onResume();

        if (getLoadingDialog() != null && !getLoadingDialog().isShowing()) {
            getLoadingDialog().show();

            RSSHelper.loadRSS(getBaseActivity(), Urlz.BASE_URL + Urlz.TOP12_NEWS);
        }

    }


    @Override
    public void onRssLoaded(ArrayList<Article> articles) {

        LogUtil.dLog(Static.RSS_TAG, "onRssLoaded(ArrayList<Article> articles)");
        if (articles != null && !articles.isEmpty()) {
            _articles = new ArrayList<>(articles);

            if (ValidationUtil.vRSSList(mArticles, _articles)) {
                mArticles = new ArrayList<>(articles);
                mNewsRecyclerAdapter = new NewsAdapter(getBaseActivity(), mArticles);
                mQrRecyclerView.setAdapter(mNewsRecyclerAdapter);
            } else {
                UIUtil.showSuperToast(getBaseActivity(), R.string.noNews, SuperToast.Duration.VERY_SHORT);
            }
        } else {
            UIUtil.showSuperToast(getBaseActivity(), R.string.slowInternet, SuperToast.Duration.SHORT);
        }

        //dismiss the dialog
        if (getLoadingDialog() != null && getLoadingDialog().isShowing())
            getLoadingDialog().dismiss();

    }

    @Override
    public void onRssLoadFailed() {

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


    public void initUI() {

        //set the Recyclerview and set onclick listeners
        mLinearLayoutManager = new LinearLayoutManager(getBaseActivity());
        mQrRecyclerView.setLayoutManager(mLinearLayoutManager);

        mBbHomeFacebook.setOnClickListener(this);
        mBbHomeTwitter.setOnClickListener(this);

        mQrRecyclerView.setReturningView(mRlBottom);
    }


}

