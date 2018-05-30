package com.example.ylf019.zlxandroid.zlxfind.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CommonTabPagerAdapter extends FragmentPagerAdapter {

    private final int PAGE_COUNT;
    private TabPagerListener listener;

    public interface TabPagerListener{
        Fragment getFragment(int position);
    }

    public void setListener(TabPagerListener listener) {
        this.listener = listener;
    }

    public CommonTabPagerAdapter(FragmentManager fm, int count, Context context) {
        super(fm);
        if (count<=0){
            throw new ExceptionInInitializerError("count value error");
        }
        this.PAGE_COUNT = count;
    }

    @Override
    public Fragment getItem(int position) {
       return listener.getFragment(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }


}
