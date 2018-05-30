package com.example.ylf019.zlxandroid.home.fragment;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseFragment;
import com.example.ylf019.zlxandroid.base.BasePagerAdapter;
import com.example.ylf019.zlxandroid.base.BaseViewPagerFragment;
import com.example.ylf019.zlxandroid.zlxfind.ZlxBeautyViewFragment;
import com.example.ylf019.zlxandroid.zlxfind.ZlxNationFragment;
import com.example.ylf019.zlxandroid.zlxfind.ZlxfindFragment;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZlxdiscFragment extends BaseFragment {

    @Bind(R.id.view_pager)
    ViewPager      mViewPager;
    @Bind(R.id.magic_indicator)
    MagicIndicator magicIndicator;

    private ArrayList<BaseViewPagerFragment> fragments = new ArrayList<>();
    private ZlxfindFragment       mZlxfindFragment;
    private ZlxBeautyViewFragment mZlxBeautyViewFragment;
    private ZlxNationFragment     mZlxNationFragment;
    private List<String>          mDataList;
    private int currentPosition = 0;

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_base_tablelayout;
    }

    @Override
    protected void initData() {
//        if (mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_CHANGEBG_CODE).equals("1"))
//            magicIndicator.setBackgroundResource(R.drawable.changebg_newyear_nav2);
//        else
//            magicIndicator.setBackgroundResource(R.color.rng_black);
            magicIndicator.setBackgroundResource(R.color.tab_bg);
        initFragments();
        initViewPager();
    }

    private void initViewPager() {
        BasePagerAdapter pagerAdapter = new BasePagerAdapter(getChildFragmentManager(), fragments);
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(fragments.size()); //ViewPager的缓存页面数量
        mViewPager.setAdapter(pagerAdapter);
        mDataList = Arrays.asList(getResources().getStringArray(R.array.ZLX_bb_titles));
        CommonNavigator commonNavigator = new CommonNavigator(getActivity());
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.normal_text));
//                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.color_tab));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.blue_tab));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 10));
//                linePagerIndicator.setColors(getResources().getColor(R.color.color_tab));
                linePagerIndicator.setColors(getResources().getColor(R.color.blue_tab));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(getActivity(), 15);
            }
        });

        final FragmentContainerHelper fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
        fragmentContainerHelper.attachMagicIndicator(magicIndicator);
        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                fragmentContainerHelper.handlePageSelected(position);
                currentPosition = position;
            }
        });
    }

    private void initFragments() {
        fragments.clear();
        if (mZlxfindFragment == null)
            mZlxfindFragment = new ZlxfindFragment();
        fragments.add(mZlxfindFragment);
        if (mZlxBeautyViewFragment == null)
            mZlxBeautyViewFragment = new ZlxBeautyViewFragment();
        fragments.add(mZlxBeautyViewFragment);
        if (mZlxNationFragment == null)
            mZlxNationFragment = new ZlxNationFragment();
        fragments.add(mZlxNationFragment);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    public void refreshData() {
        BaseViewPagerFragment baseViewPagerFragment = fragments.get(currentPosition);
        if(baseViewPagerFragment != null) {
            baseViewPagerFragment.initPagerFragment();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
