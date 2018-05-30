package com.example.ylf019.zlxandroid.zlxfind;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.model.BaseModel;
import com.example.ylf019.zlxandroid.http.model.PersonCenterModel;
import com.example.ylf019.zlxandroid.utils.ToastHelper;
import com.example.ylf019.zlxandroid.zlxfind.adapter.CommonTabPagerAdapter;
import com.example.ylf019.zlxandroid.zlxfind.fragment.ZLXMessageFragment;
import com.example.ylf019.zlxandroid.zlxfind.fragment.ZLXPersonBBFragment;
import com.example.ylf019.zlxandroid.zlxweibo.ZlxWriteMessageActivity;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class OtherSpaceActivity extends BaseActivity implements CommonTabPagerAdapter.TabPagerListener {


    @Bind(R.id.iv_avatar)
    CircleImageView         mIvAvatar;
    @Bind(R.id.ll_background)
    LinearLayout            mLlBackground;
    @Bind(R.id.toolbar)
    Toolbar                 mToolbar;
    @Bind(R.id.collapsingToolbar)
    CollapsingToolbarLayout mCollapsingToolbar;
    @Bind(R.id.magic_indicator)
    MagicIndicator          mMagicIndicator;
    @Bind(R.id.appbar)
    AppBarLayout            mAppbar;
    @Bind(R.id.viewpager)
    ViewPager               mViewpager;
    @Bind(R.id.ll_message)
    LinearLayout            mLlMessage;
    @Bind(R.id.ll_follow)
    LinearLayout            mLlFollow;
    @Bind(R.id.iv_like_tag)
    ImageView               mIvLikeTag;
    @Bind(R.id.tv_like_tag)
    TextView                mTvLikeTag;

    private CommonTabPagerAdapter adapter;
    private ZLXMessageFragment    mZlxMessageFragment;
    private ZLXPersonBBFragment   mZlxPersonBBFragment;
    private String                mLx_num;
    private List<String>          mDataList;
    private boolean               isFollowed;
    private String mId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            //透明状态栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_space);
        ButterKnife.bind(this);//绑定布局
        mLx_num = getIntent().getStringExtra("lx_num");
        if (mLx_num == null || TextUtils.isEmpty(mLx_num)) {
            this.finish();
            return;
        }
        setUpViewComponent();
    }

    private void setUpViewComponent() {
        setTitle("返回");//MagicIndicator
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initData();
    }

    private void initData() {
        if (adapter == null) {
            adapter = new CommonTabPagerAdapter(getSupportFragmentManager()
                    , 2, this);
            adapter.setListener(this);
            mViewpager.setAdapter(adapter);
            setPageChange();
        } else {
            if (mZlxMessageFragment != null && mZlxPersonBBFragment != null) {
                mZlxMessageFragment.singleData(mLx_num);
                mZlxPersonBBFragment.singleData(mLx_num);
            }
        }
        netHandler.getPersonCenterInfo(mLx_num, mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), new MyCallBack<PersonCenterModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.showAlert(OtherSpaceActivity.this, "网络断开");
            }

            @Override
            public void onSuccess(Call call, PersonCenterModel data) {
                if(data != null && data.getInfo() != null) {
                    //获取到个人信息
                    mCollapsingToolbar.setTitle(data.getInfo().getNickname());
                    mCollapsingToolbar.setExpandedTitleColor(Color.parseColor("#00ffffff"));//设置还没收缩时状态下字体颜色
                    mCollapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.black));//设置收缩后Toolbar上字体的
                    Glide.with(OtherSpaceActivity.this).load(data.getInfo().getHeadurl()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvAvatar);
                    Glide.with(OtherSpaceActivity.this).load(data.getInfo().getHeadurl())
                            .into(new ViewTarget<View, GlideDrawable>(mLlBackground) {
                                //括号里为需要加载的控件
                                @Override
                                public void onResourceReady(GlideDrawable resource,
                                                            GlideAnimation<? super GlideDrawable> glideAnimation) {
                                    mLlBackground.setBackground(resource.getCurrent());
                                }
                            });
                    isFollowed = (data.getInfo().getIs_follow().equals("1"));
                    mId = data.getInfo().getId();
                    if (isFollowed) {
                        mIvLikeTag.setImageResource(R.drawable.icon_followed);
                        mTvLikeTag.setTextColor(Color.parseColor("#ff0000"));
                    }else {
                        mIvLikeTag.setImageResource(R.drawable.icon_follow);
                        mTvLikeTag.setTextColor(Color.parseColor("#999999"));
                    }
                }else {
                    ToastHelper.showAlert(OtherSpaceActivity.this, "网络异常");
                }
            }
        });
    }

    void setPageChange() {
        mDataList = Arrays.asList(getResources().getStringArray(R.array.person_center_titles));
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {
                return mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ColorTransitionPagerTitleView(context);
                simplePagerTitleView.setNormalColor(getResources().getColor(R.color.secondary_text));
                simplePagerTitleView.setSelectedColor(getResources().getColor(R.color.primary_text));
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mViewpager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 10));
                linePagerIndicator.setColors(getResources().getColor(R.color.color_tab));
                return linePagerIndicator;
            }
        });
        mMagicIndicator.setNavigator(commonNavigator);
        LinearLayout titleContainer = commonNavigator.getTitleContainer(); // must after setNavigator
        titleContainer.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        titleContainer.setDividerDrawable(new ColorDrawable() {
            @Override
            public int getIntrinsicWidth() {
                return UIUtil.dip2px(OtherSpaceActivity.this, 15);
            }
        });
        ViewPagerHelper.bind(mMagicIndicator, mViewpager);
    }

    public void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public Fragment getFragment(int position) {
        if (position == 0) {
            if (mZlxMessageFragment == null) {
                mZlxMessageFragment = ZLXMessageFragment.newInstance(mLx_num);
            }
            return mZlxMessageFragment;
        } else if (position == 1) {
//            return DemoFragment.newInstance(position);
            if (mZlxPersonBBFragment == null) {
                mZlxPersonBBFragment = ZLXPersonBBFragment.newInstance(mLx_num);
            }
            return mZlxPersonBBFragment;
        }
        return null;
    }

    @OnClick({R.id.ll_message, R.id.ll_follow})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_message:
                //todo 留言
                Intent intent = new Intent(OtherSpaceActivity.this, ZlxWriteMessageActivity.class);
                intent.putExtra("lx_num", mLx_num);
                startActivityForResult(intent, 26);
                break;
            case R.id.ll_follow:
                //todo 关注
                if(mId != null &&!mId.isEmpty()) {
                    if(isFollowed) {
//                        netHandler.cancelMyfans(mId, new MyCallBack<BaseModel>() {
//                            @Override
//                            public void onFailure(Call call, IOException e) {
//                                ToastHelper.showAlert(OtherSpaceActivity.this, "取消关注失败");
//                            }
//
//                            @Override
//                            public void onSuccess(Call call, BaseModel data) {
//                                if(data.getError_id() == 0) {
//                                    mIvLikeTag.setImageResource(R.drawable.icon_follow);
//                                    mTvLikeTag.setTextColor(Color.parseColor("#999999"));
//                                    isFollowed = false;
//                                }else {
//                                    ToastHelper.showAlert(OtherSpaceActivity.this, "取消关注失败");
//                                }
//                            }
//                        });
                    }else { //       mId
                        netHandler.followVillager(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), mLx_num, new MyCallBack<BaseModel>() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                ToastHelper.showAlert(OtherSpaceActivity.this, "关注失败");
                            }

                            @Override
                            public void onSuccess(Call call, BaseModel data) {
                                if(data.getError_id() == 0) {
                                    mIvLikeTag.setImageResource(R.drawable.icon_followed);
                                    mTvLikeTag.setTextColor(Color.parseColor("#ff0000"));
                                    isFollowed = true;
                                }else {
                                    ToastHelper.showAlert(OtherSpaceActivity.this, "已关注");
                                }
                            }
                        });
                    }
                }
                break;

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 666) {
           if(mZlxMessageFragment != null) {
               mZlxMessageFragment.singleData(mLx_num);
           }
        }
    }

}
