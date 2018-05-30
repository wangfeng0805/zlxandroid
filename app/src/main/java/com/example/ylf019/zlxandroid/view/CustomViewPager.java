package com.example.ylf019.zlxandroid.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Author:    ZhuWenWu
 * Version    V1.0
 * Date:      15/10/2 下午4:52
 * Description: 自定义滑动ViewPager
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 15/10/2      ZhuWenWu            1.0                    1.0
 * Why & What is modified:
 */
public class CustomViewPager extends ViewPager {

  private boolean isScrollable;

  public CustomViewPager(Context context) {
    super(context);
  }

  public CustomViewPager(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override
  public boolean onTouchEvent(MotionEvent ev) {
    return isScrollable && super.onTouchEvent(ev);
  }

  @Override
  public boolean onInterceptTouchEvent(MotionEvent ev) {
    return isScrollable && super.onInterceptTouchEvent(ev);
  }

  public boolean isScrollable() {
    return isScrollable;
  }

  public void setScrollable(boolean isScrollable) {
    this.isScrollable = isScrollable;
  }
}
