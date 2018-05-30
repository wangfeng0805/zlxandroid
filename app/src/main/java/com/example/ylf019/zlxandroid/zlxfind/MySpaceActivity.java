package com.example.ylf019.zlxandroid.zlxfind;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.model.PersonCenterModel;
import com.example.ylf019.zlxandroid.zlxfind.adapter.CommonTabPagerAdapter;
import com.example.ylf019.zlxandroid.zlxfind.fragment.ZLXMessageFragment;
import com.example.ylf019.zlxandroid.zlxfind.fragment.ZLXPersonBBFragment;

import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class MySpaceActivity extends BaseActivity implements CommonTabPagerAdapter.TabPagerListener {


    @Bind(R.id.iv_avatar)
    CircleImageView                                   mIvAvatar;
    @Bind(R.id.ll_background)
    LinearLayout                                      mLlBackground;
    @Bind(R.id.toolbar)
    Toolbar                                           mToolbar;
    @Bind(R.id.collapsingToolbar)
    CollapsingToolbarLayout                           mCollapsingToolbar;
    @Bind(R.id.magic_indicator)
    net.lucode.hackware.magicindicator.MagicIndicator mMagicIndicator;
    @Bind(R.id.appbar)
    AppBarLayout                                      mAppbar;
    @Bind(R.id.viewpager)
    ViewPager                                         mViewpager;

    private CommonTabPagerAdapter adapter;
    private ZLXMessageFragment    mZlxMessageFragment;
    private ZLXPersonBBFragment   mZlxPersonBBFragment;
    private String                mLx_num;
    private List<String>          mDataList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_space);
        ButterKnife.bind(this);//绑定布局
        mLx_num = getIntent().getStringExtra("lx_num");
        if (mLx_num == null || TextUtils.isEmpty(mLx_num)) {
            this.finish();
        }
        setUpViewComponent();
    }

    private void setUpViewComponent() {
        setTitle("返回");//MagicIndicator
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initData();
    }

    private void initData() {
        if (adapter == null) {
            adapter = new CommonTabPagerAdapter(getSupportFragmentManager()
                    , 2, this);
            adapter.setListener(this);
            mViewpager.setAdapter(adapter);
            setPageChange();
        } else {
            if (mZlxMessageFragment != null && mZlxPersonBBFragment != null) {
                mZlxMessageFragment.singleData(mLx_num);
                mZlxPersonBBFragment.singleData(mLx_num);
            }
        }
        netHandler.getPersonCenterInfo(mLx_num, new MyCallBack<PersonCenterModel>() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onSuccess(Call call, PersonCenterModel data) {
                //获取到个人信息
                mCollapsingToolbar.setTitle(data.getInfo().getNickname());
                mCollapsingToolbar.setExpandedTitleColor(Color.parseColor("#00ffffff"));//设置还没收缩时状态下字体颜色
                mCollapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.black));//设置收缩后Toolbar上字体的
                Glide.with(MySpaceActivity.this).load(data.getInfo().getHeadurl()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvAvatar);
                Glide.with(MySpaceActivity.this).load(data.getInfo().getHeadurl())
                        .into(new ViewTarget<View, GlideDrawable>(mLlBackground) {
                            //括号里为需要加载的控件
                            @Override
                            public void onResourceReady(GlideDrawable resource,
                                                        GlideAnimation<? super GlideDrawable> glideAnimation) {
                                mLlBackground.setBackground(resource.getCurrent());
                        }
                  });
            }
        });
    }

    void setPageChange() {
        mDataList = Arrays.asList(getResources().getStringArray(R.array.person_center_titles2));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.secondary_text));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.primary_text));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewpager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 10));
                linePagerIndicator.setColors(getResources().getColor(R.color.color_tab));
                return linePagerIndicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(MySpaceActivity.this, 15);
            }
        });
        ViewPagerHelper.bind(mMagicIndicator, mViewpager);
    }

    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public Fragment getFragment(int position) {
        if (position == 1) {
            if (mZlxMessageFragment == null) {
                mZlxMessageFragment = ZLXMessageFragment.newInstance(mLx_num);
            }
            return mZlxMessageFragment;
        } else if (position == 0) {
//            return DemoFragment.newInstance(position);
            if (mZlxPersonBBFragment == null) {
                mZlxPersonBBFragment = ZLXPersonBBFragment.newInstance(mLx_num);
            }
            return mZlxPersonBBFragment;
        }
        return null;
    }

}
