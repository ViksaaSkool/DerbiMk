package com.derbi.mk.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.derbi.mk.R;
import com.derbi.mk.activity.BaseActivity;
import com.derbi.mk.activity.GenCategoriesActivity;
import com.derbi.mk.cnst.Urlz;
import com.derbi.mk.helpers.ChangeActivityHelper;

import java.util.List;
import java.util.Map;

public class CategoriesAdapter extends BaseExpandableListAdapter {

    private BaseActivity mActivity;
    private Map<String, List<String>> mSubCategories;
    private List<String> mCategories;
    public int[] mCategoriesIcons;

    public CategoriesAdapter(BaseActivity activity, List<String> categories,
                             Map<String, List<String>> subCategories, int[] categoriesIcons) {
        this.mActivity = activity;
        this.mSubCategories = subCategories;
        this.mCategories = categories;
        this.mCategoriesIcons = categoriesIcons;
    }

    public Object getChild(int groupPosition, int childPosition) {
        return mSubCategories.get(mCategories.get(groupPosition)).get(
                childPosition);
    }

    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String laptop = (String) getChild(groupPosition, childPosition);
        LayoutInflater inflater = mActivity.getLayoutInflater();

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.categories_item, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.subCategory);

        item.setText(laptop);
        return convertView;
    }

    public int getChildrenCount(int groupPosition) {
        return mSubCategories.get(mCategories.get(groupPosition)).size();
    }

    public Object getGroup(int groupPosition) {
        return mCategories.get(groupPosition);
    }

    public int getGroupCount() {
        return mCategories.size();
    }

    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String categoryName = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.categories_group, null);
        }

        TextView item = (TextView) convertView.findViewById(R.id.mainCategory);
        ImageView icn = (ImageView) convertView.findViewById(R.id.imgCategory);

        icn.setBackgroundResource(mCategoriesIcons[groupPosition]);
        item.setTypeface(null, Typeface.BOLD);
        item.setText(categoryName);

        ImageView toCategory = (ImageView) convertView
                .findViewById(R.id.chooseCategory);
        toCategory.setTag(groupPosition);
        toCategory.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                String selectedUrl = "";
                int category = 0;
                int position = 0;


                switch ((Integer) v.getTag()) {

                    case 0:
                        selectedUrl = Urlz.footballUrlz[0];
                        category = 0;
                        break;

                    case 1:
                        selectedUrl = Urlz.basketballUrlz[0];
                        category = 1;
                        break;

                    case 2:
                        selectedUrl = Urlz.handballUrlz[0];
                        category = 2;
                        break;

                    case 3:
                        selectedUrl = Urlz.motoSportUrlz[0];
                        category = 3;
                        break;

                    case 4:
                        selectedUrl = Urlz.sportPlusUrlz[0];
                        category = 4;
                        break;

                    case 5:
                        selectedUrl = Urlz.tennisUrlz[0];
                        category = 5;
                        break;
                    case 6:
                        selectedUrl = Urlz.magazinUrlz[0];
                        category = 6;
                        break;

                }


                ChangeActivityHelper.changeActivityWithExtra(mActivity, GenCategoriesActivity.class, false, category, 0);
            }
        });

        return convertView;
    }

    public boolean hasStableIds() {
        return true;
    }

    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
