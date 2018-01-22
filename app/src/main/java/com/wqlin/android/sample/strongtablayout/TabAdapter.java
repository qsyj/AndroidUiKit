package com.wqlin.android.sample.strongtablayout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangql
 * @email wangql@leleyuntech.com
 * @date 2018/1/19 9:51
 */
public class TabAdapter extends FragmentPagerAdapter {
    List<String> tabTitle = new ArrayList<>();

    public TabAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setItems(List<String> data){
        tabTitle.clear();
        tabTitle.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        TabFragment fragment = TabFragment.newInstance(position + "");
        return fragment;
    }

    @Override
    public int getCount() {
        return tabTitle.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (tabTitle.size() > position){
            return tabTitle.get(position);
        }
        return "";
    }
}
