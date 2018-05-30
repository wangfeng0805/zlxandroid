package com.example.ylf019.zlxandroid.view;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;


import com.example.ylf019.zlxandroid.appconfig.AppContact;
import com.example.ylf019.zlxandroid.utils.StringHelper;

import java.util.regex.Pattern;

/**
 * Author:    ZhuWenWu
 * Version    V1.0
 * Date:      15/12/7 下午9:54
 * Description: 扩展TextView用于显示表情及超链接
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 15/12/7      ZhuWenWu            1.0                    1.0
 * Why & What is modified:
 */
public class WeiBoContentTextView extends android.support.v7.widget.AppCompatTextView {
    private View.OnClickListener onViewClickListener;

    public WeiBoContentTextView(Context context) {
        super(context);
    }

    public WeiBoContentTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeiBoContentTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setWeiBoContent(CharSequence content) {
        if (content != null && StringHelper.notEmpty(content.toString())) {
            SpannableString spannableString = SpannableString.valueOf(content);
            // 用户昵称
//            Pattern pattern = Pattern.compile("@[\\w\\p{InCJKUnifiedIdeographs}-]{1,26}");//Pattern.compile("@[^,，：:\\s@]+");//Pattern.compile("@[^\\\\s:：]+[:：\\\\s]|#([^\\\\#|.]+)#|http://t\\\\.cn/\\\\w");//Pattern.compile("@[\\w\\p{InCJKUnifiedIdeographs}-]{1,26}");
//            Linkify.addLinks(spannableString, pattern, AppContact.APP_USER_INFO_SCHEME);

            if(isUrlEnable) {
                // 网页链接
                Pattern pattern2 = Pattern.compile("http://[a-zA-Z0-9+&@#/%?=~_\\-|!:,\\.;]*[a-zA-Z0-9+&@#/%=~_|]");
                Linkify.addLinks(spannableString,pattern2, AppContact.APP_WEB_LINK_SCHEME);
            }

            URLSpan[] urlSpans = spannableString.getSpans(0, spannableString.length(), URLSpan.class);
            MyCustomURLSpan weiBoSpan;
            for (URLSpan urlSpan : urlSpans) {
                weiBoSpan = new MyCustomURLSpan(urlSpan.getURL(), getContext());
                int start = spannableString.getSpanStart(urlSpan);
                int end = spannableString.getSpanEnd(urlSpan);
                spannableString.removeSpan(urlSpan);
                spannableString.setSpan(weiBoSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            setMovementMethod(LinkMovementMethod.getInstance());
            setText(spannableString);
        }
    }

    private boolean isUrlEnable = false;

    public void setUrlEnable(boolean isUrlEnabled) {
        this.isUrlEnable = isUrlEnabled;
    }

}
