package com.example.ylf019.zlxandroid.zlxcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.model.ZlxHomePageModel;
import com.example.ylf019.zlxandroid.utils.ToastHelper;
import com.example.ylf019.zlxandroid.zlxfind.OtherSpaceActivity;
import com.example.ylf019.zlxandroid.zlxpersoncenter.ZlxGuanzhuListActivity;
import com.example.ylf019.zlxandroid.zlxpersoncenter.ZlxMyFansListActivity;
import com.example.ylf019.zlxandroid.zlxpersoncenter.adapter.ZlxMyFansAdapter;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class ZlxHomePageActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView        mTvTitle;
    @Bind(R.id.layout_header_image)
    ImageView       mLayoutHeaderImage;
    @Bind(R.id.iv_user_avatar)
    CircleImageView mIvUserAvatar;
    @Bind(R.id.tv_lx_num)
    TextView        mTvLxNum;
    @Bind(R.id.tv_lx_first_date)
    TextView        mTvLxFirstDate;
    @Bind(R.id.tv_home_address)
    TextView        mTvHomeAddress;
    @Bind(R.id.tv_occupation)
    TextView        mTvOccupation;
    @Bind(R.id.tv_education)
    TextView        mTvEducation;
    @Bind(R.id.tv_college)
    TextView        mTvCollege;
    @Bind(R.id.tv_brithday)
    TextView        mTvBrithday;
    @Bind(R.id.item_tab1)
    LinearLayout    mItemTab1;
    @Bind(R.id.item_tab2)
    LinearLayout    mItemTab2;
    @Bind(R.id.item_tab3)
    LinearLayout    mItemTab3;
    @Bind(R.id.iv_image1)
    ImageView       mIvImage1;
    @Bind(R.id.iv_image2)
    ImageView       mIvImage2;
    @Bind(R.id.iv_image3)
    ImageView       mIvImage3;
    @Bind(R.id.iv_image4)
    ImageView       mIvImage4;
    @Bind(R.id.iv_follows1)
    CircleImageView       mIvFollows1;
    @Bind(R.id.iv_follows2)
    CircleImageView       mIvFollows2;
    @Bind(R.id.iv_follows3)
    CircleImageView       mIvFollows3;
    @Bind(R.id.iv_follows4)
    CircleImageView       mIvFollows4;
    @Bind(R.id.iv_fans1)
    CircleImageView       mIvFans1;
    @Bind(R.id.iv_fans2)
    CircleImageView       mIvFans2;
    @Bind(R.id.iv_fans3)
    CircleImageView       mIvFans3;
    @Bind(R.id.iv_fans4)
    CircleImageView       mIvFans4;
    private String mLx_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zlx_home_page);
        ButterKnife.bind(this);
        mTvTitle.setText("个人主页");
        mLx_num = getIntent().getStringExtra(AppSpContact.SP_KEY_LX_NUM);
        initData();
    }

    private void initData() {
        //https://waptest1.ylfcf.com/user/getNewSpaceInfoNew
        netHandler.getNewSpaceInfoNew(mLx_num, mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), new MyCallBack<ZlxHomePageModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.showAlert(ZlxHomePageActivity.this, "网络断开");
            }

            @Override
            public void onSuccess(Call call, ZlxHomePageModel data) {
                if (data != null) {
                    if (data.getError_id() == 0) {
                        Glide.with(ZlxHomePageActivity.this).load(data.getInfo().getHeadurl())
                                .placeholder(R.drawable.img_header).into(mLayoutHeaderImage);
                        Glide.with(ZlxHomePageActivity.this).load(data.getInfo().getHeadurl())
                                .dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvUserAvatar);
                        mTvLxNum.setText("老乡号: " + data.getInfo().getLx_num());
                        mTvLxFirstDate.setText("注册日期: " + data.getInfo().getAdd_time());
                        mTvHomeAddress.setText("家乡: " + data.getInfo().getAddress());
                        mTvOccupation.setText("职业: " + data.getInfo().getVocation());
                        mTvEducation.setText("学历: " + data.getInfo().getEducation());
                        mTvCollege.setText("毕业学校: " + data.getInfo().getGraduate_university());
                        mTvBrithday.setText("生日: " + data.getInfo().getBorn_date());
                        if (data.getInfo().getDynamic_arr() != null && data.getInfo().getDynamic_arr().size() > 0) {
                            initImages(data.getInfo().getDynamic_arr());
                        } else {
//                            mItemTab1.setVisibility(View.GONE);
                            mIvImage1.setVisibility(View.INVISIBLE);
                            mIvImage2.setVisibility(View.INVISIBLE);
                            mIvImage3.setVisibility(View.INVISIBLE);
                            mIvImage4.setVisibility(View.INVISIBLE);
                        }

                        if (data.getInfo().getMy_fans() != null && data.getInfo().getMy_fans().size() > 0) {
                            initFollowImages(data.getInfo().getMy_fans());//todo
                        } else {
//                            mItemTab2.setVisibility(View.GONE);
                            mIvFollows1.setVisibility(View.INVISIBLE);
                            mIvFollows2.setVisibility(View.INVISIBLE);
                            mIvFollows3.setVisibility(View.INVISIBLE);
                            mIvFollows4.setVisibility(View.INVISIBLE);
                        }

                        if (data.getInfo().getFollow_fans() != null && data.getInfo().getFollow_fans().size() > 0) {
                            initFansImages(data.getInfo().getFollow_fans());
                        } else {
//                            mItemTab3.setVisibility(View.GONE);
                            mIvFans1.setVisibility(View.INVISIBLE);
                            mIvFans2.setVisibility(View.INVISIBLE);
                            mIvFans3.setVisibility(View.INVISIBLE);
                            mIvFans4.setVisibility(View.INVISIBLE);
                        }

                    }
                } else {
//                    ToastHelper.showAlert(ZlxHomePageActivity.this, "数据请求失败");
                }
            }
        });
    }

    @OnClick({R.id.rl_toptitlebar_back, R.id.item_tab1, R.id.item_tab2, R.id.item_tab3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_toptitlebar_back:
                finish();
                break;
            case R.id.item_tab1:
                //todo 动态
                Intent intent1 = new Intent(ZlxHomePageActivity.this, OtherSpaceActivity.class);
                intent1.putExtra("lx_num", mLx_num);
                startActivity(intent1);
                break;
            case R.id.item_tab2:
                //todo 他关注的人
                Intent intent2 = new Intent(ZlxHomePageActivity.this, ZlxGuanzhuListActivity.class);
                intent2.putExtra("lx_num", mLx_num);
                startActivity(intent2);
                break;
            case R.id.item_tab3:
                //todo 关注他的人
                Intent intent3 = new Intent(ZlxHomePageActivity.this, ZlxMyFansListActivity.class);
                intent3.putExtra("lx_num", mLx_num);
                startActivity(intent3);
                break;
        }
    }

    private void initImages(List<String> dynamic_arr) {
        switch (dynamic_arr.size()) {
            case 1:
                mIvImage1.setVisibility(View.VISIBLE);
                mIvImage2.setVisibility(View.INVISIBLE);
                mIvImage3.setVisibility(View.INVISIBLE);
                mIvImage4.setVisibility(View.INVISIBLE);
                Glide.with(ZlxHomePageActivity.this).load(dynamic_arr.get(0)).placeholder(R.drawable.ic_default_picture).into(mIvImage1);
                break;
            case 2:
                mIvImage1.setVisibility(View.VISIBLE);
                mIvImage2.setVisibility(View.VISIBLE);
                mIvImage3.setVisibility(View.INVISIBLE);
                mIvImage4.setVisibility(View.INVISIBLE);
                Glide.with(ZlxHomePageActivity.this).load(dynamic_arr.get(0)).placeholder(R.drawable.ic_default_picture).into(mIvImage1);
                Glide.with(ZlxHomePageActivity.this).load(dynamic_arr.get(1)).placeholder(R.drawable.ic_default_picture).into(mIvImage2);
                break;
            case 3:
                mIvImage1.setVisibility(View.VISIBLE);
                mIvImage2.setVisibility(View.VISIBLE);
                mIvImage3.setVisibility(View.VISIBLE);
                mIvImage4.setVisibility(View.INVISIBLE);
                Glide.with(ZlxHomePageActivity.this).load(dynamic_arr.get(0)).placeholder(R.drawable.ic_default_picture).into(mIvImage1);
                Glide.with(ZlxHomePageActivity.this).load(dynamic_arr.get(1)).placeholder(R.drawable.ic_default_picture).into(mIvImage2);
                Glide.with(ZlxHomePageActivity.this).load(dynamic_arr.get(2)).placeholder(R.drawable.ic_default_picture).into(mIvImage3);
                break;
            case 4:
                mIvImage1.setVisibility(View.VISIBLE);
                mIvImage2.setVisibility(View.VISIBLE);
                mIvImage3.setVisibility(View.VISIBLE);
                mIvImage4.setVisibility(View.VISIBLE);
                Glide.with(ZlxHomePageActivity.this).load(dynamic_arr.get(0)).placeholder(R.drawable.ic_default_picture).into(mIvImage1);
                Glide.with(ZlxHomePageActivity.this).load(dynamic_arr.get(1)).placeholder(R.drawable.ic_default_picture).into(mIvImage2);
                Glide.with(ZlxHomePageActivity.this).load(dynamic_arr.get(2)).placeholder(R.drawable.ic_default_picture).into(mIvImage3);
                Glide.with(ZlxHomePageActivity.this).load(dynamic_arr.get(3)).placeholder(R.drawable.ic_default_picture).into(mIvImage4);
                break;

        }
    }

    private void initFollowImages(List<ZlxHomePageModel.InfoBean.My_FansBean> follow_fans) {
        switch (follow_fans.size()) {
            case 1:
                mIvFollows1.setVisibility(View.VISIBLE);
                mIvFollows2.setVisibility(View.INVISIBLE);
                mIvFollows3.setVisibility(View.INVISIBLE);
                mIvFollows4.setVisibility(View.INVISIBLE);
                Glide.with(ZlxHomePageActivity.this).load(follow_fans.get(0).getHeadurl()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFollows1);
                break;
            case 2:
                mIvFollows1.setVisibility(View.VISIBLE);
                mIvFollows2.setVisibility(View.VISIBLE);
                mIvFollows3.setVisibility(View.INVISIBLE);
                mIvFollows4.setVisibility(View.INVISIBLE);
                Glide.with(ZlxHomePageActivity.this).load(follow_fans.get(0).getHeadurl()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFollows1);
                Glide.with(ZlxHomePageActivity.this).load(follow_fans.get(1).getHeadurl()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFollows2);
                break;
            case 3:
                mIvFollows1.setVisibility(View.VISIBLE);
                mIvFollows2.setVisibility(View.VISIBLE);
                mIvFollows3.setVisibility(View.VISIBLE);
                mIvFollows4.setVisibility(View.INVISIBLE);
                Glide.with(ZlxHomePageActivity.this).load(follow_fans.get(0).getHeadurl()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFollows1);
                Glide.with(ZlxHomePageActivity.this).load(follow_fans.get(1).getHeadurl()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFollows2);
                Glide.with(ZlxHomePageActivity.this).load(follow_fans.get(2).getHeadurl()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFollows3);
                break;
            case 4:
                mIvFollows1.setVisibility(View.VISIBLE);
                mIvFollows2.setVisibility(View.VISIBLE);
                mIvFollows3.setVisibility(View.VISIBLE);
                mIvFollows4.setVisibility(View.VISIBLE);
                Glide.with(ZlxHomePageActivity.this).load(follow_fans.get(0).getHeadurl()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFollows1);
                Glide.with(ZlxHomePageActivity.this).load(follow_fans.get(1).getHeadurl()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFollows2);
                Glide.with(ZlxHomePageActivity.this).load(follow_fans.get(2).getHeadurl()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFollows3);
                Glide.with(ZlxHomePageActivity.this).load(follow_fans.get(3).getHeadurl()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFollows4);
                break;

        }
    }

    //todo 图片显示，与头像跳转
    private void initFansImages(List<ZlxHomePageModel.InfoBean.FollowFansBean> my_fans_arr) {
        switch (my_fans_arr.size()) {
            case 1:
                mIvFans1.setVisibility(View.VISIBLE);
                mIvFans2.setVisibility(View.INVISIBLE);
                mIvFans3.setVisibility(View.INVISIBLE);
                mIvFans4.setVisibility(View.INVISIBLE);
                Glide.with(ZlxHomePageActivity.this).load(my_fans_arr.get(0)).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFans1);
                break;
            case 2:
                mIvFans1.setVisibility(View.VISIBLE);
                mIvFans2.setVisibility(View.VISIBLE);
                mIvFans3.setVisibility(View.INVISIBLE);
                mIvFans4.setVisibility(View.INVISIBLE);
                Glide.with(ZlxHomePageActivity.this).load(my_fans_arr.get(0)).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFans1);
                Glide.with(ZlxHomePageActivity.this).load(my_fans_arr.get(1)).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFans2);
                break;
            case 3:
                mIvFans1.setVisibility(View.VISIBLE);
                mIvFans2.setVisibility(View.VISIBLE);
                mIvFans3.setVisibility(View.VISIBLE);
                mIvFans4.setVisibility(View.INVISIBLE);
                Glide.with(ZlxHomePageActivity.this).load(my_fans_arr.get(0)).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFans1);
                Glide.with(ZlxHomePageActivity.this).load(my_fans_arr.get(1)).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFans2);
                Glide.with(ZlxHomePageActivity.this).load(my_fans_arr.get(2)).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFans3);
                break;
            case 4:
                mIvFans1.setVisibility(View.VISIBLE);
                mIvFans2.setVisibility(View.VISIBLE);
                mIvFans3.setVisibility(View.VISIBLE);
                mIvFans4.setVisibility(View.VISIBLE);
                Glide.with(ZlxHomePageActivity.this).load(my_fans_arr.get(0)).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFans1);
                Glide.with(ZlxHomePageActivity.this).load(my_fans_arr.get(1)).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFans2);
                Glide.with(ZlxHomePageActivity.this).load(my_fans_arr.get(2)).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFans3);
                Glide.with(ZlxHomePageActivity.this).load(my_fans_arr.get(3)).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvFans4);
                break;
        }
    }

}

