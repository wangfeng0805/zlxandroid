package com.example.ylf019.zlxandroid.utils;

import android.view.View;

/**
 * Description: View 帮助类
 */
public class ViewHelper {
    /**
     * 设置View 是否显示
     *
     * @param view   需要设置的View对象
     * @param isGone 是否隐藏
     * @param <V>    V
     * @return V 当前View
     */
    public static <V extends View> V setGone(V view, boolean isGone) {
        if (view != null) {
            if (isGone) {
                if (View.GONE != view.getVisibility())
                    view.setVisibility(View.GONE);
            } else {
                if (View.VISIBLE != view.getVisibility())
                    view.setVisibility(View.VISIBLE);
            }
        }
        return view;
    }

    /**
     * 多个view隐藏或显示
     *
     * @param gone  true 隐藏；false 显示
     * @param views 多个view对象
     */
    public static void setViewsGone(boolean gone, View... views) {
        for (View view : views) {
            setGone(view, gone);
        }
    }

    /**
     * 多个Views可见或不可见
     *
     * @param inVisible
     * @param views
     */
    public static void setViewsInVisible(boolean inVisible, View... views) {
        for (View view : views) {
            setInVisible(view, inVisible);
        }
    }

    /**
     * 单个view设置可见或不可见
     *
     * @param view
     * @param inVisible
     */
    public static void setInVisible(View view, boolean inVisible) {
        if (inVisible) {
            if (View.VISIBLE == view.getVisibility()) {
                view.setVisibility(View.INVISIBLE);
            }
        } else {
            if (View.INVISIBLE == view.getVisibility()) {
                view.setVisibility(View.VISIBLE);
            }
        }
    }
}
