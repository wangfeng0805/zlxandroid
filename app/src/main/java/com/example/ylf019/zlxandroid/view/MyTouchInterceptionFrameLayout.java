package com.example.ylf019.zlxandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.ksoichiro.android.observablescrollview.TouchInterceptionFrameLayout;

/**
 * Author:    yangjinxin
 * Version    V1.0
 * Date:      17-8-28 13:53
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 17-8-28      yangjinxin            1.0                    1.0
 */

public class MyTouchInterceptionFrameLayout extends TouchInterceptionFrameLayout {

    public MyTouchInterceptionFrameLayout(Context context) {
        this(context,null);
    }

    public MyTouchInterceptionFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyTouchInterceptionFrameLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs);
    }

    public MyTouchInterceptionFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        this(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //逻辑:
        //1.判断down事件时候,recyclerView的位置.如果recyclerView存在位置的偏移就应该
        //把事件传递给recyclerView.


        //        boolean isScroll = mFixedRecyclerView != null && mFixedRecyclerView.canScrollVertically(RecyclerView.VERTICAL);
        boolean isScroll = isCanScrollVertically();

//        Log.d("TAG", "onInterceptTouchEvent: isScoll"+isScroll);
        //        switch (ev.getAction()) {
        //            case MotionEvent.ACTION_DOWN:
        //                //判断如果RecyclerView存在位移,那么久应该把事件给recycle
        //
        //        }

        if (!isScroll) {
            return super.onInterceptTouchEvent(ev);
        } else {

            return false;

        }

        //        return super.onInterceptTouchEvent(ev);
    }


    private FixedRecyclerView mFixedRecyclerView;

    public void setFixedRecyclerView(FixedRecyclerView fixedRecyclerView) {
        mFixedRecyclerView = fixedRecyclerView;
    }

    public boolean isCanScrollVertically() {
        //判断recyclerView是否第一个条目top 是否处于
        return mFixedRecyclerView != null && mFixedRecyclerView.isCanScrollVertically();
    }

}
