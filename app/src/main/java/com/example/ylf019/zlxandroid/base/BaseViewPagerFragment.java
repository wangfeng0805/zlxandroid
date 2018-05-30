package com.example.ylf019.zlxandroid.base;


import com.example.ylf019.zlxandroid.view.MyTouchInterceptionFrameLayout;

/**
 * Author:    ZhuWenWu
 * Version    V1.0
 * Date:      15/10/2 下午5:52
 * Description:
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 15/10/2      ZhuWenWu            1.0                    1.0
 * Why & What is modified:
 */
public abstract class BaseViewPagerFragment extends BaseFragment {
    public boolean isInitFinish = false;//是否已初始化

    /**
     * 如果Fragment没有初始化，那么初始化Fragment
     */
    public void initPagerFragment() {
        if (!isInitFinish) {
            isInitFinish = true;
            onInitPagerData();
        }
    }

    /**
     * 设置Framgment是否初始化
     * @param bRefresh
     */
    public void setInitFinish(boolean bRefresh) {
        isInitFinish = bRefresh;
    }

    /**
     * 初始化Fragment抽象类
     */
    protected abstract void onInitPagerData();//初始化数据

    public MyTouchInterceptionFrameLayout mMyTouchInterceptionFrameLayout;

    public void setTouchInterceptionFrameLayout(MyTouchInterceptionFrameLayout myTouchInterceptionFrameLayout) {
        //传递上层TouchInterceptionFrameLayout给fragment
        this.mMyTouchInterceptionFrameLayout = myTouchInterceptionFrameLayout;
    }

}
