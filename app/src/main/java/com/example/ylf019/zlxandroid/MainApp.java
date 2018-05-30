package com.example.ylf019.zlxandroid;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.example.ylf019.zlxandroid.utils.SharedPreferencesHelper;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by yjx on 2018/3/30.
 */

public class MainApp extends Application {

    private static Context sContext;//获取Application 上下文Contenxt对象 单例
    public static IWXAPI api;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = getApplicationContext(); // 获取Application的上下文
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);

        setUpSharedPreferencesHelper(getApplicationContext());//初始化SharedPreferences

        //初始化微信sdk
        initWeixin();
    }

    private void initWeixin() {
        api = WXAPIFactory.createWXAPI(this, "wxd408d2c4479e46a4", true);
        api.registerApp("wxd408d2c4479e46a4");
    }

    /**
     * 初始化SharedPreferences
     *
     * @param context 上下文
     */
    private void setUpSharedPreferencesHelper(Context context) {
        SharedPreferencesHelper.getInstance().Builder(context);
    }

    /**
     * 获取AppApplication对象
     */
    public static MainApp getInstance() {
        return (MainApp) sContext;
    }


}
