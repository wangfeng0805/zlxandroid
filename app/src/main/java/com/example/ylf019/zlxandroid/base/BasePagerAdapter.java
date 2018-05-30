package com.example.ylf019.zlxandroid.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Author:    ZhuWenWu
 * Version    V1.0
 * Date:      15/10/2 下午5:53
 * Description: ViewPager适配器基类
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 15/10/2      ZhuWenWu            1.0                    1.0
 * Why & What is modified:
 */
public class BasePagerAdapter extends FragmentPagerAdapter {
    private final ArrayList<BaseViewPagerFragment> mFragments;
    private       String[]                         mTitles;


    public BasePagerAdapter(FragmentManager fm, ArrayList<BaseViewPagerFragment> fragments) {
        super(fm);
        mFragments = fragments;
    }

    public void setTitles(String[] titles) {
        this.mTitles = titles;
    }

    @Override
    public Fragment getItem(int i) {
        return mFragments.get(i);
    }

    @Override
    public int getCount() {
        return mFragments.size() == 0 ? 0 : mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles != null && position < mTitles.length ? mTitles[position] : "";
    }

}
