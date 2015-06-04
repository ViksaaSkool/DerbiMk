package com.derbi.mk.navdrawer;

/**
 * Created by varsovski on 29-May-15.
 */
public class NavDrawerItem {
    private String mTitle;
    private int mIcon;

    public NavDrawerItem(){}

    public NavDrawerItem(String title, int icon){
        this.mTitle = title;
        this.mIcon = icon;
    }

    public String getTitle(){
        return this.mTitle;
    }

    public int getIcon(){
        return this.mIcon;
    }

    public void setTitle(String title){
        this.mTitle = title;
    }

    public void setIcon(int icon){
        this.mIcon = icon;
    }
}
