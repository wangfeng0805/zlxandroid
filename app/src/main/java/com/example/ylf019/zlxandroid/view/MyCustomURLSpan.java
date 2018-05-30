package com.example.ylf019.zlxandroid.view;

import android.content.Context;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;

import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppContact;


/**
 * Description ${TODO}
 * Created by yangjinxin on 2017/11/13.
 */

public class MyCustomURLSpan extends ClickableSpan {

    private String  mURL;
    private Context mContext;

    public MyCustomURLSpan(String url, Context context) {
        mURL = url;
        mContext = context;
    }

    public String getURL() {
        return mURL;
    }

    @Override
    public void onClick(View widget) {
        if (getURL().startsWith(AppContact.APP_WEB_LINK_SCHEME)) {//网址链接，用户昵称及表情不做处理
//            WebActivity.open(mContext,getURL());
        }
    }

    @Override
    public void updateDrawState(TextPaint tp) {
        tp.setColor(mContext.getResources().getColor(R.color.weibo_username_color));
    }

}
