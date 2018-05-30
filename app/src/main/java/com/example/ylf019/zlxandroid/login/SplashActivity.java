package com.example.ylf019.zlxandroid.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.base.BasePagerAdapter;
import com.example.ylf019.zlxandroid.base.BaseViewPagerFragment;
import com.example.ylf019.zlxandroid.utils.SharedPreferencesHelper;
import com.example.ylf019.zlxandroid.view.CircleIndicator;
import com.example.ylf019.zlxandroid.view.CustomViewPager;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {


    public BasePagerAdapter mAdapter;
    private ArrayList<BaseViewPagerFragment> mFragments = new ArrayList<>();
    @Bind(R.id.view_pager)
    CustomViewPager mViewPager;
    @Bind(R.id.ci_img)
    CircleIndicator mCiImg;

    String[]     permissions     = new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, };
    List<String> mPermissionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPermissions();
    }

    private void initPermissions() {
        /**
         * 判断哪些权限未授予
         */
        mPermissionList.clear();
        for (int i = 0; i < permissions.length; i++) {
            if (ContextCompat.checkSelfPermission(this, permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissions[i]);
            }
        }

        /**
        * 判断是否为空
         */
        if (mPermissionList.isEmpty()) {//未授予的权限为空，表示都授予了
            delayEntryPage();
        } else {//请求权限方法
            String[] permissions = mPermissionList.toArray(new String[mPermissionList.size()]);//将List转为数组
            ActivityCompat.requestPermissions(SplashActivity.this, permissions, 1);
        }
    }

    private void delayEntryPage() {
//        boolean isFirstLauncher = SharedPreferencesHelper.getInstance().getBoolean(AppSpContact.SP_KEY_FIRST_LAUNCHER, true);
////        boolean isFirstLauncher = true;
//        if (!isFirstLauncher) {//不是第一次启动
            startActivity(new Intent(this, LoadingActivity.class));
            finish();
//        } else {
//            setContentView(R.layout.activity_splash);
//            ButterKnife.bind(this);
//            if (getActionBar() != null) {
//                getActionBar().hide();
//            }
//            setUpViewComponent();
//            SharedPreferencesHelper.getInstance().putBoolean(AppSpContact.SP_KEY_FIRST_LAUNCHER, false);
//        }
    }

    private void setUpViewComponent() {
        setUpFragments();
        setUpViewPager();
    }

    private void setUpFragments() {
        mFragments.clear();
        for (int i = 1; i <= 4; i++) {
            mFragments.add(LauncherImageFragment.newInstance(i));
        }
    }

    private void setUpViewPager() {
        mAdapter = new BasePagerAdapter(getSupportFragmentManager(), mFragments);
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setScrollable(true);
        mCiImg.setViewCount(4, 0);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mCiImg != null) {
                    mCiImg.onPageSelected(position);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    boolean mShowRequestPermission = true;//用户是否禁止权限

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(SplashActivity.this, permissions[i]);
                        if (showRequestPermission) {//
//                            judgePermission();//重新申请权限
                            initPermissions();
                            return;
                        } else {
                            mShowRequestPermission = false;//已经禁止
                        }
                    }
                }
                delayEntryPage();
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
