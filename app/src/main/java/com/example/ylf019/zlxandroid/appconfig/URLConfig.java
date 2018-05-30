package com.example.ylf019.zlxandroid.appconfig;


import com.example.ylf019.zlxandroid.BuildConfig;

/**
 * @author guozhangyu  create by 2017/8/24 14:57
 * @Description
 */
public class URLConfig {

    public static String getBaseUrl() {
        return BuildConfig.DEBUG ? DEFAULT_TEST_SERVER : DEFAULT_SERVER;
    }

    //Url拼接地址
    public static final String HTTP = "http://";
    public static final String HTTPS = "https://";
    public static final String DEFAULT_SERVER = HTTPS + "waptest1.ylfcf.com";
//    public static final String DEFAULT_SERVER = HTTP + "minipro.cms.com";
    public static final String DEFAULT_TEST_SERVER = HTTPS + "waptest1.ylfcf.com";
//    public static final String DEFAULT_TEST_SERVER = HTTP + "minipro.cms.com";
//    public static final String RNG_TAOBAO_MALL_URL = "https://royalclub.taobao.com/";
}
