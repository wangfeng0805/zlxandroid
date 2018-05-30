package com.example.ylf019.zlxandroid.home;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.home.fragment.ZlxdiscFragment;
import com.example.ylf019.zlxandroid.home.fragment.ZlxadvertFragment;
import com.example.ylf019.zlxandroid.home.fragment.ZlxmapFragment;
import com.example.ylf019.zlxandroid.home.fragment.ZlxpersonFragment;
import com.example.ylf019.zlxandroid.home.fragment.ZlxrecommendFragment;
import com.example.ylf019.zlxandroid.utils.ToastHelper;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity {

    //    @Bind(R.id.iv_user_avatar)
//    ImageView    mIvUserAvatar;//顶部用户头像
    @Bind(R.id.tabs)
    LinearLayout tabs;
    @Bind(R.id.iv_bottom_map)
    ImageView    mIv_bottom_map;
    @Bind(R.id.iv_bottom_find)
    ImageView    mIv_bottom_find;
    @Bind(R.id.iv_bottom_shop)
    ImageView    mIv_bottom_shop;
    @Bind(R.id.iv_bottom_help)
    ImageView    mIv_bottom_help;
    @Bind(R.id.iv_bottom_person)
    ImageView    mIv_bottom_person;
    @Bind(R.id.tool_bar)
    Toolbar      mToolbar;
    @Bind(R.id.tv_title)
    TextView     mTvTitle;

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ZlxmapFragment       mZlxmapFragment;
    private ZlxdiscFragment      mZlxdiscFragment;
    private ZlxrecommendFragment mZlxshopFragment;
    private ZlxadvertFragment    mZlxhelpFragment;
    private ZlxpersonFragment    mZlxpersonFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
//        setImmerseLayout(mToolbar);
        setUpViewComponent();//初始化所有
//        Utils.setWindowStatusBarColor(this, R.color.transparent);
    }

//    protected void setImmerseLayout(View view) {
//        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            Window window = getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            int statusBarHeight = getStatusBarHeight(this.getBaseContext());
//            view.setPadding(0, statusBarHeight, 0, 0);
//        }
//    }

    private void setUpViewComponent() {
        initFragments();
//        showUserAvatar(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_USER_AVATAR));
        changeFragment(0);

    }

    @OnClick(R.id.iv_bottom_map)
    void onBbClick() {
        changeFragment(0);
        mToolbar.setVisibility(View.GONE);
        setViewClick(mIv_bottom_map, R.drawable.tab_map_selected);
    }

    @OnClick(R.id.iv_bottom_find)
    void onFindClick() {
        changeFragment(1);
        mToolbar.setVisibility(View.VISIBLE);
        mTvTitle.setText("发现");
        setViewClick(mIv_bottom_find, R.drawable.tab_find_selected);
    }

    @OnClick(R.id.iv_bottom_shop)
    void onCenterClick() {
        changeFragment(2);
        mToolbar.setVisibility(View.VISIBLE);
        mTvTitle.setText("找对象");
        setViewClick(mIv_bottom_shop, R.drawable.tab_shop_selected);
    }

    @OnClick(R.id.iv_bottom_help)
    void onMsgClick() {
        changeFragment(3);
        mToolbar.setVisibility(View.VISIBLE);
        mTvTitle.setText("广告");
        setViewClick(mIv_bottom_help, R.drawable.tab_help_selected);
    }

    @OnClick(R.id.iv_bottom_person)
    void onProfileClick() {
        changeFragment(4);
        mToolbar.setVisibility(View.GONE);
        setViewClick(mIv_bottom_person, R.drawable.tab_person_selected);
    }

    public void setViewClick(ImageView rb, int resId) {
        setView(mIv_bottom_map, R.drawable.tab_map_unselected);
        setView(mIv_bottom_find, R.drawable.tab_find_unselected);
        setView(mIv_bottom_shop, R.drawable.tab_shop_unselected);
        setView(mIv_bottom_help, R.drawable.tab_help_unselected);
        setView(mIv_bottom_person, R.drawable.tab_person_unselected);
        setView(rb, resId);
    }

    private void setView(ImageView rb, int resId) {
        rb.setImageResource(resId);
    }

    private int currentPosition = 0;

    /**
     * 切换Fragment
     */
    public void changeFragment(int position) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        hideFragment(transaction);
        if (position >= fragments.size() || fragments.get(position) == null) {
            Fragment fragment = getFragment(position);
            fragments.set(position, fragment);
            transaction.add(R.id.vp_main, fragment).commitAllowingStateLoss();
        } else {
            Fragment fragment = fragments.get(position);
            transaction.show(fragment).commitAllowingStateLoss();
//            if (currentPosition == position) {
//                switch (position) {
//                    case 0:
//                        ((RNGbbFragment) fragment).refreshData();
//                        break;
//                }
//            }
            currentPosition = position;
        }
    }

    /**
     * 隐藏Fragment
     */
    private void hideFragment(FragmentTransaction transaction) {
        for (Fragment fragment : fragments) {
            if (fragment != null) {
                transaction.hide(fragment);
            }
        }
    }

    /**
     * 根据点击获取显示的Fragment
     */
    private Fragment getFragment(int position) {
        Fragment fragment = null;
        if (position == 0) {
            mZlxmapFragment = new ZlxmapFragment();
            fragment = mZlxmapFragment;
        } else if (position == 1) {
            mZlxdiscFragment = new ZlxdiscFragment();
            fragment = mZlxdiscFragment;
        } else if (position == 2) {
            mZlxshopFragment = new ZlxrecommendFragment();
            fragment = mZlxshopFragment;
        } else if (position == 3) {
            mZlxhelpFragment = new ZlxadvertFragment();
            fragment = mZlxhelpFragment;
        } else if (position == 4) {
            mZlxpersonFragment = new ZlxpersonFragment();
            fragment = mZlxpersonFragment;
        }
        return fragment;
    }

    /**
     * 初始化集合
     */
    private void initFragments() {
        fragments.add(mZlxmapFragment);
        fragments.add(mZlxdiscFragment);
        fragments.add(mZlxshopFragment);
        fragments.add(mZlxhelpFragment);
        fragments.add(mZlxpersonFragment);
    }
//    /**
//     * 显示用户头像
//     */
//    private void showUserAvatar(String userAvatar) {
//        if (StringHelper.notEmpty(userAvatar))
//            Glide.with(this).load(userAvatar).into(mIvUserAvatar);
//        else
//            mIvUserAvatar.setImageResource(R.drawable.sidebar_default_head_icon);
//    }

    private long mExitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_MENU://消费实体MENU按键
                return true;
            case KeyEvent.KEYCODE_BACK:
                if ((System.currentTimeMillis() - mExitTime) > 2000) {
                    ToastHelper.showBottomAlert(this, "再按一次退出程序");
                    mExitTime = System.currentTimeMillis();
                } else {
                    finish();
                }
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
