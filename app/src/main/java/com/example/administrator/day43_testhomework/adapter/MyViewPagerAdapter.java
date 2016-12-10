package com.example.administrator.day43_testhomework.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016-12-7.
 */
public class MyViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;
    private static final String[] TITLE=new String[]{"ITEM1","ITEM2","ITEM3"};

    public MyViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TITLE[position];
    }
}
