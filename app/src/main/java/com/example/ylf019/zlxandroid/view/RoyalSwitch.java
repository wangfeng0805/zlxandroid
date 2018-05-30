package com.example.ylf019.zlxandroid.view;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.ViewParent;
import android.widget.CheckBox;

import com.example.ylf019.zlxandroid.R;


/**
 * Description ${TODO}
 * Created by yangjinxin on 2017/11/21.
 */
public class RoyalSwitch extends android.support.v7.widget.AppCompatCheckBox {
    /**
     * 是否打开
     */
    private boolean mChecked        = false;
    /**
     * 是否正在滑动
     */
    private boolean mIsMoving       = false;
    /**
     * 是否正在显示动画效果
     */
    private boolean mAnimating      = false;
    /**
     * 确保开关只激活一次
     */
    private boolean mBroadcasting   = false;
    /**
     * 底部图片高
     */
    private int     mThumbHeight    = 0;
    /**
     * 画布高度
     */
    private int     mMeasuredHeight = 0;
    /**
     * 底部图片宽
     */
    private int     mSwtichWidth    = 0;
    /**
     * 小滑块的宽度
     */
    private int     mThumbWidth     = 0;
    /**
     * 点击范围
     */
    private int     mTouchSlop      = 0;

    //    private static final int        DEFAULT_HEIGHT     = 46;
    private static final int        DEFAULT_HEIGHT     = 50;
    /**
     * 点击事件过时的时间
     */
    private              long       mClickTimeout      = 0;
    /**
     * 首次按下的x轴坐标
     */
    private              float      mFristDownX        = 0;
    /**
     * 首次按下的Y轴坐标
     */
    private              float      mFirstDownY        = 0;
    /**
     * 小滑块的初始位置
     */
    private              float      mInitPos           = 0;
    /**
     * 小滑块当前x轴位置
     */
    private float      mCurXPos           = 0;
    /**
     * 小滑块为开时的位置
     */
    private float      mOnPos             = 0;
    /**
     * 小滑块为关时候的位置
     */
    private float      mOffPos            = 0;
    /**
     * 动画的矢量速度
     */
    private float      mAnimatorVelocity  = 0;
    /**
     * 动画位置
     */
    private float      mAnimationPosition = 0;
    /**
     * 矢量速度
     */
    private float      mVelocity          = 10;
    /**
     * 小滑块图片
     */
    private Bitmap     mThumbBitmap       = null;
    /**
     * 小滑块资源id
     */
    private int        mThumbResource     = 0;
    /**
     * 父控件
     */
    private ViewParent mParent            = null;
    /**
     * 画笔
     */
    private Paint      mPaint             = null;
    /**
     * 点击事件
     */
    private OnClickListener mOnClickListener;
    /**
     * 状态改变监听器
     */
    private              OnCheckedChangeListener mOnCheckedChangeListener = null;
    /**
     * 底部矩形
     */
    private              RectF                   mSwitchFRect             = null;
    /**
     * 默认绿色
     */
    private static       int                     sDefaultColorGreen       = 0;
    /**
     * 自定义绿色
     */
    private              int                     mCheckedColor            = 0;
    /**
     * 默认灰色
     */
    private static       int                     sDefaultColorGray        = 0;
    /**
     * 自定义灰色
     */
    private              int                     mUncheckedColor          = 0;
    /**
     * X轴增量
     */
    private static final int                     X_OFFSET                 = 6;
    /**
     * 一半X轴增量
     */
    private static final int                     HALF_X_OFFSET            = X_OFFSET / 4;
    /**
     * Y轴增量
     */
    private static final int                     Y_OFFSET                 = 4;
    /**
     * 一半Y轴增量
     */
    private static final int                     HALT_Y_OFFSET            = Y_OFFSET / 2;

    /**
     * 透明度的最大值
     */
    private static final int          MAX_ALPHA          = 255;
    /**
     * 圆角矩形的弧度
     */
    private static       float        RECF_RADIUS        = 50;
    /**
     * 开关状态改变的延迟时间
     */
    private static final long         CHECKED_DELAY_TIME = 10;
    /**
     * 调用点击时间的Runnable对象
     */
    private              PerformClick mPerformClick      = null;
    /**
     * 记录滑动是否打开
     */
    private              boolean      mTurningOn         = false;

    public boolean isChecked() {
        return mChecked;
    }

    public OnCheckedChangeListener getOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener Listener) {
        this.mOnCheckedChangeListener = Listener;
    }

    /**
     * 初始化时调用
     */
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            Log.e("sysout", "setChecked:" + checked);
            mPaint.setColor(checked ? mCheckedColor : mUncheckedColor);
            if (mBroadcasting) {
                return;
            }
            // 设置调用onCheckedChanged时的状态
            super.setChecked(checked);
            // 初始化时会给mBroadcasting附真值
            mBroadcasting = true;
            if (mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onCheckedChanged(RoyalSwitch.this, checked);
            }

            mBroadcasting = false;

            mCurXPos = checked ? mOnPos : mOffPos;
            // 重绘
            invalidate();

        }
    }

    public OnClickListener getOnClickListener() {
        return mOnClickListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    public RoyalSwitch(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    /**
     * 初始化
     * @param context
     */
    private void init(Context context, AttributeSet attrs, int defStyle) {
        Resources resources = context.getResources();
        // 颜色
        sDefaultColorGreen = resources.getColor(R.color.open_green);
        sDefaultColorGray = resources.getColor(R.color.closed_gray);

        TypedArray ta = context.obtainStyledAttributes(attrs,
                R.styleable.RoyalSwitch, defStyle, 0);
        mCheckedColor = ta.getColor(R.styleable.RoyalSwitch_checked_color,
                sDefaultColorGreen);
        mUncheckedColor = ta.getColor(R.styleable.RoyalSwitch_unchecked_color,
                sDefaultColorGray);
        mThumbResource = ta.getResourceId(R.styleable.RoyalSwitch_thumb,
                R.mipmap.ic_switch_thumb);
        // 小滑块图片
        mThumbBitmap = BitmapFactory.decodeResource(resources, mThumbResource);
        mMeasuredHeight = mThumbBitmap.getHeight();
        // 得到小滑块图片的宽高
        mThumbHeight = mThumbBitmap.getHeight();
        mThumbWidth = mThumbBitmap.getWidth();
        mSwtichWidth = mThumbWidth * 2 + X_OFFSET;

        //底部圆角矩形半径
        RECF_RADIUS = mThumbBitmap.getHeight() / 2 + X_OFFSET;
        // 记录打开位置
        mOnPos = mSwtichWidth / 2;
        // 点击范围
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        // 点击事件
        mClickTimeout = ViewConfiguration.getPressedStateDuration()
                + ViewConfiguration.getTapTimeout();
        // 开关底部矩形
        mSwitchFRect = new RectF(0, 0, mSwtichWidth, mThumbHeight + Y_OFFSET);

        // 设置画笔
        mPaint = new Paint();
        mPaint.setColor(mChecked ? mCheckedColor : mUncheckedColor);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setDither(true);
        // 使用抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setFilterBitmap(true);
        ta.recycle();
    }

    public RoyalSwitch(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.checkboxStyle);
    }

    public RoyalSwitch(Context context) {
        this(context, null);
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        // 在画布上绘画图形
        if (mIsMoving) {
            mPaint.setColor(mUncheckedColor);
            canvas.drawRoundRect(mSwitchFRect, RECF_RADIUS, RECF_RADIUS, mPaint);
            mPaint.setColor(mCheckedColor);
            double absPos = Math.min(Math.abs(mCurXPos), mThumbWidth);
            int alpha = (int) (absPos / mThumbWidth * MAX_ALPHA);
            mPaint.setAlpha(alpha);
            canvas.drawRoundRect(mSwitchFRect, RECF_RADIUS, RECF_RADIUS, mPaint);
            mPaint.setAlpha(MAX_ALPHA);
            canvas.drawBitmap(mThumbBitmap, mCurXPos + HALF_X_OFFSET,
                    HALT_Y_OFFSET, mPaint);
        } else {
            canvas.drawRoundRect(mSwitchFRect, RECF_RADIUS, RECF_RADIUS, mPaint);
            canvas.drawBitmap(mThumbBitmap, mCurXPos + HALF_X_OFFSET,
                    HALT_Y_OFFSET, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 测量控件的最大宽高,裁定画布的最大宽高
        setMeasuredDimension(mSwtichWidth + X_OFFSET,
                Math.max(mMeasuredHeight + Y_OFFSET, mThumbHeight + Y_OFFSET));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        // x,y距离
        int disX = (int) (x - mFristDownX);
        int disY = (int) (y - mFirstDownY);
        // x,y绝对值
        int absDisX = Math.abs(disX);
        int absDisY = Math.abs(disY);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsMoving = false;
                mFristDownX = x;
                mFirstDownY = y;
                mParent = getParent();
                if (mParent != null) {
                    mParent.requestDisallowInterceptTouchEvent(true);
                }
                mInitPos = mChecked ? mOnPos : mOffPos;
                break;
            case MotionEvent.ACTION_MOVE:
                mIsMoving = true;
                mCurXPos = mInitPos + event.getX() - mFristDownX;
                // 超出按钮位置时小滑块的位置
                if (mCurXPos >= mOnPos) {
                    // 开
                    mCurXPos = mOnPos;
                    mPaint.setColor(mCheckedColor);
                } else if (mCurXPos <= 0) {
                    // 关
                    mCurXPos = mOffPos;
                    mPaint.setColor(mUncheckedColor);
                }
                mTurningOn = mCurXPos > ((mOnPos - mOffPos) / 3);
                break;
            case MotionEvent.ACTION_UP:
                mIsMoving = false;
                long time = event.getEventTime() - event.getDownTime();
                // 响应点击事件
                if (absDisX < mTouchSlop && absDisY < mTouchSlop
                        && time < mClickTimeout) {
                    if (mPerformClick == null) {
                        mPerformClick = new PerformClick();
                    }
                    if (!post(mPerformClick)) {
                        performClick();
                    }
                } else {
                    startAnimator(!mTurningOn);
                }
                break;
            default:
                break;
        }
        // 重绘
        invalidate();
        return isEnabled();
    }

    private class PerformClick implements Runnable {

        @Override
        public void run() {
            performClick();
        }
    }

    @Override
    public boolean performClick() {
        startAnimator(mChecked);
        if (mOnClickListener != null) {
            mOnClickListener.onClick(RoyalSwitch.this);
        }
        return true;
    }

    /**
     * 延迟设定开关值，保证动画流畅
     * @param checked
     */
    private void setCheckedDelay(final boolean checked) {
        postDelayed(new Runnable() {

            @Override
            public void run() {
                setChecked(checked);
            }
        }, CHECKED_DELAY_TIME);
    }

    private void stopAnimator() {
        mAnimating = false;
    }

    /**
     * 动画+设置开关值.
     * @param checked
     *         开关值
     */
    private void startAnimator(final boolean checked) {
        // 用更平滑的动画过渡
        mAnimating = true;
        mAnimationPosition = mCurXPos;
        mAnimatorVelocity = checked ? -mVelocity : mVelocity;
        new SwitchAnimator().run();
    }

    /**
     * 点击开关动画
     */
    private class SwitchAnimator implements Runnable {
        @Override
        public void run() {


            if (!mAnimating) {
                return;
            }
            mAnimationPosition += mAnimatorVelocity;
            if (mAnimatorVelocity > 0) {
                // 打开
                mPaint.setColor(mCheckedColor);
            } else {
                // 关闭
                mPaint.setColor(mUncheckedColor);
            }
            if (mAnimationPosition >= mOnPos) {
                stopAnimator();
                mAnimationPosition = mOnPos;
                setCheckedDelay(true);
            } else if (mAnimationPosition <= 0) {
                stopAnimator();
                setCheckedDelay(false);
                mAnimationPosition = mOffPos;
            }
            mCurXPos = mAnimationPosition;
            invalidate();
            // 循环播放动画
            SwitchAnimatorController.sendMsg(this);
        }
    }
}