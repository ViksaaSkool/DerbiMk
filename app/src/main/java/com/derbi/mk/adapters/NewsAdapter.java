package com.derbi.mk.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.derbi.mk.R;
import com.derbi.mk.activity.BaseActivity;
import com.derbi.mk.activity.MainActivity;
import com.derbi.mk.fragment.HomeFragment;
import com.derbi.mk.helpers.ChangeActivityHelper;
import com.derbi.mk.helpers.FragmentHelper;
import com.derbi.mk.utils.SocialUtil;
import com.derbi.mk.utils.UIUtil;
import com.pkmmte.pkrss.Article;

import java.util.ArrayList;

/**
 * Created by varsovski on 27-May-15.
 */
public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.SimpleItemViewHolder> {


    private ArrayList<Article> mArticles;
    private BaseActivity mActivity;
    OnItemClickListener mItemClickListener;

    public class SimpleItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getAdapterPosition());
            }
        }

        TextView date;
        ImageView newsPic;
        TextView shortDesc;
        ImageView icnFacebook;
        ImageView icnLink;
        ImageView icnTwitter;
        ImageView icnReadMore;
        TextView title;
        TextView dateText;
        RelativeLayout container;

        public SimpleItemViewHolder(View view) {
            super(view);
            date = (TextView) view.findViewById(R.id.pubDate);
            dateText = (TextView) view.findViewById(R.id._pubDate);
            shortDesc = (TextView) view.findViewById(R.id.descNews);
            newsPic = (ImageView) view.findViewById(R.id.imgNewsPic);
            icnReadMore = (ImageView) view.findViewById(R.id.icnMore);
            icnFacebook = (ImageView) view.findViewById(R.id.icnFacebook);
            icnTwitter = (ImageView) view.findViewById(R.id.icnTwitter);
            icnLink = (ImageView) view.findViewById(R.id.icnUrl);
            title = (TextView) view.findViewById(R.id.tvNewsTitle);
            container = (RelativeLayout) view.findViewById(R.id.rlMainImg);
        }
    }

    public NewsAdapter(BaseActivity a, ArrayList<Article> articles) {
        this.mArticles = articles;
        this.mActivity = a;
    }


    @Override
    public SimpleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_card, parent, false);
        return new SimpleItemViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(SimpleItemViewHolder holder, final int position) {

        holder.title.setText(mArticles.get(position).getTitle());
        if (mArticles.get(position) != null)
            holder.shortDesc.setText(UIUtil.shortDsc(mArticles.get(position).getDescription()));
        else
            holder.shortDesc.setText("");
        if (mArticles.get(position) != null){
            String date = UIUtil.formatDate(mArticles.get(position).getDate());
            if (!date.contains("1970")){
                holder.date.setText(date);
            }
            else {
                holder.date.setText("");
                holder.dateText.setText("");
            }
        }
        else {
            holder.date.setText("");
        }


        if (mArticles.get(position).getImage() != null && !mArticles.get(position).getImage().toString().isEmpty())
            Glide.with(mActivity).load(mArticles.get(position).getImage().toString()).into(holder.newsPic);


        holder.icnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialUtil.startArticleVia(mActivity, mArticles.get(position).getTitle() + " " + mArticles.get(position).getSource());
            }
        });
        holder.icnTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialUtil.startArticleVia(mActivity, mArticles.get(position).getTitle() + " " + mArticles.get(position).getSource());
            }
        });
        holder.icnLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SocialUtil.startArticleVia(mActivity, mArticles.get(position).getTitle() + " " + mArticles.get(position).getSource());
            }
        });

        holder.icnReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mArticles.get(position) != null && mArticles.get(position).getSource() != null && !mArticles.get(position).getSource().toString().isEmpty())
                    if (mActivity.getCurrentFragment() instanceof HomeFragment)
                        FragmentHelper.setArticleFragment(mActivity, mArticles.get(position).getSource().toString(), FragmentHelper.HOME);
                    else
                        ChangeActivityHelper.changeActivityMain(mActivity, MainActivity.class, false, mArticles.get(position).getSource().toString(), FragmentHelper.GEN_CATEGORIES);
            }
        });

        holder.newsPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mActivity.getCurrentFragment() instanceof HomeFragment)
                    FragmentHelper.setArticleFragment(mActivity, mArticles.get(position).getSource().toString(), FragmentHelper.HOME);
                else
                    ChangeActivityHelper.changeActivityMain(mActivity, MainActivity.class, false, mArticles.get(position).getSource().toString(), FragmentHelper.GEN_CATEGORIES);

            }
        });

        //set height in proportion to screen size
        int proportionalHeight = UIUtil.containerHeight(mActivity);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, proportionalHeight);
        params.addRule(RelativeLayout.BELOW, R.id.llTitleAndDate);
        holder.container.setLayoutParams(params);


    }

    @Override
    public int getItemCount() {
        return mArticles.size();
    }


    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}

