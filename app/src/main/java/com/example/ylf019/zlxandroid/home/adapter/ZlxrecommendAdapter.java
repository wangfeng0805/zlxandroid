package com.example.ylf019.zlxandroid.home.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.http.info.WeiboInfo;
import com.example.ylf019.zlxandroid.http.model.ZlxObjectListModel;
import com.example.ylf019.zlxandroid.utils.Distance;
import com.example.ylf019.zlxandroid.zlxcenter.ZlxHomePageActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by yjx on 2018/5/8.
 */

public class ZlxrecommendAdapter extends BaseQuickAdapter<ZlxObjectListModel.DataBean, BaseViewHolder> {

    private double mLong1;
    private double mLat1;


    public ZlxrecommendAdapter(int layoutResId, @Nullable List<ZlxObjectListModel.DataBean> data, double long1, double lat1) {
        super(layoutResId, data);
        mLong1 = long1;
        mLat1 = lat1;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ZlxObjectListModel.DataBean item) {
        RoundedImageView imageView = helper.getView(R.id.iv_picture);
        RoundedImageView ri_round_bg = helper.getView(R.id.ri_round_bg);
        if(item.getLatitude() !=null && item.getLongitude() != null && !TextUtils.isEmpty(item.getLatitude()) && !TextUtils.isEmpty(item.getLongitude())) {
            double distance = Distance.DistanceOfTwoPoints(mLat1, mLong1, Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude()));
            int dis = (int) distance;
            helper.setText(R.id.tv_age, " " + item.getAge() + "岁   |   " + dis + "公里");
        }else {
            helper.setText(R.id.tv_age, " " + item.getAge() + "岁");
        }
        Glide.with(mContext).load(item.getHeadurl()).placeholder(R.drawable.ic_default_picture).dontAnimate().into(imageView);
        helper.setText(R.id.tv_name, item.getNickname())
                .setText(R.id.tv_user_info, item.getCity() + item.getArea() + " " + item.getEducation())
                .setText(R.id.tv_like_tag, item.getObject_label());
        FrameLayout fl_object = helper.getView(R.id.fl_object);
        fl_object.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ZlxHomePageActivity.class);
                intent.putExtra(AppSpContact.SP_KEY_LX_NUM, item.getLx_num());
                mContext.startActivity(intent);
            }
        });
        if(item.getSex() != null && item.getSex().equals("男")) {
            ri_round_bg.setBackground(mContext.getResources().getDrawable(R.color.color_object_boy));
        }else{
            ri_round_bg.setBackground(mContext.getResources().getDrawable(R.color.color_object_girl));
        }

    }


}
