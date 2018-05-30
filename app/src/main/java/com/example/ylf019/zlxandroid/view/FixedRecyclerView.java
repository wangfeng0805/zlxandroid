package com.example.ylf019.zlxandroid.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.malinskiy.superrecyclerview.SuperRecyclerView;

/**
 * Author:    ZhuWenWu
 * Version    V1.0
 * Date:      14-12-14 23:48
 * Description: fixed RecyclerView and SwipeRefreshLayout BUG
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 14-12-14      ZhuWenWu            1.0                    1.0
 * Why & What is modified:
 */
public class FixedRecyclerView extends ObservableRecyclerView {
    protected SuperRecyclerView.LAYOUT_MANAGER_TYPE layoutManagerType;
    private boolean isLoadingEnable = false;
    private boolean isLoading       = false;
    private OnLoadNextListener mLoadNextListener;
    private int                mStartX;
    private int                mStartY;

    public FixedRecyclerView(Context context) {
        this(context, null);
    }

    public FixedRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FixedRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public boolean isCanScrollVertically() {
        return getChildAt(0) != null && getChildAt(0).getTop() < 0;
    }

    @Override
    public boolean canScrollVertically(int direction) {
        if (direction < 1) {
            boolean original = super.canScrollVertically(direction);
            return !original && getChildAt(0) != null && getChildAt(0).getTop() < 0 || original;
        }
        return super.canScrollVertically(direction);
    }

    public void setLoadingEnable(boolean isLoadingEnable) {
        this.isLoadingEnable = isLoadingEnable;
    }

    public void setLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        try {
            super.onScrollChanged(l, t, oldl, oldt);

            LayoutManager layoutManager = getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();

            int firstVisibleItemPosition = -1;
            if (layoutManagerType == null) {
                if (layoutManager instanceof LinearLayoutManager) {
                    layoutManagerType = SuperRecyclerView.LAYOUT_MANAGER_TYPE.LINEAR;
                } else if (layoutManager instanceof GridLayoutManager) {
                    layoutManagerType = SuperRecyclerView.LAYOUT_MANAGER_TYPE.GRID;
                } else {
                    throw new RuntimeException("Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager");
                }
            }

            switch (layoutManagerType) {
                case LINEAR:
                    firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    break;
                case GRID:
                    firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
                    break;
            }

            if (firstVisibleItemPosition + visibleItemCount >= totalItemCount &&
                    totalItemCount != 0 && isLoadingEnable && !isLoading && mLoadNextListener != null) {
                isLoading = true;
                mLoadNextListener.onLoadNext();
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
    }

    public void setLoadNextListener(OnLoadNextListener listener) {
        mLoadNextListener = listener;
    }

    public interface OnLoadNextListener {
        public void onLoadNext();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //子父控件的事件处理。横向事件本控件拦截
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mStartX = (int) ev.getX();
                mStartY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                int diffX = moveX - mStartX;
                int diffY = moveY - mStartY;
                if (Math.abs(diffY) > Math.abs(diffX)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            //            case MotionEvent.ACTION_UP :
            //
            //                break;
        }
        return super.dispatchTouchEvent(ev);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        //        LinearLayoutManager layoutManager = (LinearLayoutManager) this.getLayoutManager();
        //        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
        //
        //        switch (ev.getAction()) {
        //            case MotionEvent.ACTION_DOWN:
        //                mStartY = (int) ev.getY();
        //                break;
        //            case MotionEvent.ACTION_MOVE:
        //                int moveY = (int) ev.getY();
        //                int diffY = moveY - mStartY;
        //                mStartY = moveY;
        //                if (firstVisibleItemPosition != 0) {
        //                    scrollBy(0, diffY);
        //                }
        //                break;
        //        }
        return super.onTouchEvent(ev);
    }

}