package com.derbi.mk.fragment;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.derbi.mk.R;
import com.derbi.mk.activity.GenCategoriesActivity;
import com.derbi.mk.adapters.CategoriesAdapter;
import com.derbi.mk.cnst.Urlz;
import com.derbi.mk.helpers.ChangeActivityHelper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by varsovski on 27-May-15.
 */
public class CategoriesFragment extends BaseFragment implements ExpandableListView.OnChildClickListener, ExpandableListView.OnGroupClickListener {


    @InjectView(R.id.elCategories)
    ExpandableListView mElCategories;

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
        return fragment;
    }


    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> categoriesCollection;
    public TypedArray categoriesIcons;


    int[] categoriesIcns = {R.drawable.football, R.drawable.basketball,
            R.drawable.handball, R.drawable.motosport,
            R.drawable.sport_plus, R.drawable.tennis, R.drawable.magazine};

    ExpandableListView expListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_categories, container, false);
        ButterKnife.inject(this, v);

        init();


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
        categoriesIcons.recycle();
    }


    public void init() {
        createGroupList();

        createCollection();
        categoriesIcons = getResources().obtainTypedArray(R.array.catListImgs);
        final CategoriesAdapter expListAdapter = new CategoriesAdapter(getBaseActivity(), groupList, categoriesCollection, categoriesIcns);
        mElCategories.setAdapter(expListAdapter);
        mElCategories.setOnChildClickListener(this);
        mElCategories.setOnGroupClickListener(this);
        mElCategories.setGroupIndicator(null);
    }


    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        String selectedUrl = "";
        int category = 0;
        int position = 0;


        switch (groupPosition) {

            case 0:
                selectedUrl = Urlz.footballUrlz[childPosition + 1];
                category = 0;
                position = childPosition + 1;
                break;

            case 1:
                selectedUrl = Urlz.basketballUrlz[childPosition + 1];
                category = 1;
                position = childPosition + 1;
                break;

            case 2:
                selectedUrl = Urlz.handballUrlz[childPosition + 1];
                category = 2;
                position = childPosition + 1;
                break;

            case 3:
                selectedUrl = Urlz.motoSportUrlz[childPosition + 1];
                category = 3;
                position = childPosition + 1;
                break;

            case 4:
                selectedUrl = Urlz.sportPlusUrlz[childPosition + 1];
                category = 4;
                position = childPosition + 1;
                break;

            case 5:
                selectedUrl = Urlz.tennisUrlz[childPosition + 1];
                category = 5;
                position = childPosition + 1;
                break;
            case 6:
                selectedUrl = Urlz.magazinUrlz[childPosition + 1];
                category = 6;
                position = childPosition + 1;
                break;

        }


        //FragmentHelper.setGenCategories(getBaseActivity(), selectedUrl, category, position);

        ChangeActivityHelper.changeActivityWithExtra(getBaseActivity(), GenCategoriesActivity.class, false, category, position);

        return true;
    }


    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add(getResources().getString(R.string.fudbal));
        groupList.add(getResources().getString(R.string.kosharka));
        groupList.add(getResources().getString(R.string.rakomet));
        groupList.add(getResources().getString(R.string.motosport));
        groupList.add(getResources().getString(R.string.sportPlus));
        groupList.add(getResources().getString(R.string.tenis));
        groupList.add(getResources().getString(R.string.magazin));
    }

    private void createCollection() {
        // preparing categories collection(child)
        String[] fudbal = getResources().getStringArray(R.array.fudbalArr);
        String[] kosharka = getResources().getStringArray(R.array.kosharkaArr);
        String[] rakomet = getResources().getStringArray(R.array.rakometArr);
        String[] motosport = getResources()
                .getStringArray(R.array.motosportArr);
        String[] sportPlus = getResources()
                .getStringArray(R.array.sportPlusArr);
        String[] tenis = getResources().getStringArray(R.array.tenisArr);
        String[] magazin = getResources().getStringArray(R.array.magazinArr);

        categoriesCollection = new LinkedHashMap<String, List<String>>();

        for (String cat : groupList) {
            if (cat.equals(getResources().getString(R.string.fudbal))) {
                loadChild(fudbal);
            } else if (cat.equals(getResources().getString(R.string.kosharka)))
                loadChild(kosharka);
            else if (cat.equals(getResources().getString(R.string.rakomet)))
                loadChild(rakomet);
            else if (cat.equals(getResources().getString(R.string.motosport)))
                loadChild(motosport);
            else if (cat.equals(getResources().getString(R.string.tenis)))
                loadChild(tenis);
            else if (cat.equals(getResources().getString(R.string.sportPlus)))
                loadChild(sportPlus);
            else
                loadChild(magazin);

            categoriesCollection.put(cat, childList);
        }
    }

    private void loadChild(String[] subCategories) {
        childList = new ArrayList<String>();
        for (String cat : subCategories)
            childList.add(cat);
    }

    @Override
    public boolean onGroupClick(ExpandableListView parent, View v,
                                int groupPosition, long id) {
        // TODO Auto-generated method stub
        return false;
    }
}
