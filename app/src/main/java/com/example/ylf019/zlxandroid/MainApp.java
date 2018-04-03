package com.example.ylf019.zlxandroid;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by yjx on 2018/3/30.
 */

public class MainApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用 SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
    }

}
