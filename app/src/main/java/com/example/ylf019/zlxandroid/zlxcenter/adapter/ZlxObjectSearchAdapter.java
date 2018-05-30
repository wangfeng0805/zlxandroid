package com.example.ylf019.zlxandroid.zlxcenter.adapter;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.http.info.LeaveMsgModel;
import com.example.ylf019.zlxandroid.http.model.ZlxObjectListModel;
import com.example.ylf019.zlxandroid.utils.Distance;
import com.example.ylf019.zlxandroid.zlxcenter.ZlxHomePageActivity;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

/**
 * Created by yjx on 2018/5/14.
 */

public class ZlxObjectSearchAdapter extends BaseQuickAdapter<ZlxObjectListModel.DataBean, BaseViewHolder> {

    private double mLong1;
    private double mLat1;

    public ZlxObjectSearchAdapter(int layoutResId, @Nullable List<ZlxObjectListModel.DataBean> data,  double long1, double lat1) {
        super(layoutResId, data);
        mLong1 = long1;
        mLat1 = lat1;
    }

    @Override
    protected void convert(BaseViewHolder helper, final ZlxObjectListModel.DataBean item) {
        RoundedImageView iv_picture = helper.getView(R.id.iv_picture);
        RoundedImageView ri_round_bg = helper.getView(R.id.ri_round_bg);
        if(item.getLatitude() != null && item.getLongitude() != null) {
            double distance = Distance.DistanceOfTwoPoints(mLat1, mLong1, Double.parseDouble(item.getLatitude()), Double.parseDouble(item.getLongitude()));
             int dis = (int) distance;
            helper.setText(R.id.tv_distance, dis + "公里");
        }
        Glide.with(mContext).load(item.getHeadurl()).placeholder(R.drawable.ic_default_picture).dontAnimate().into(iv_picture);
        helper.setText(R.id.tv_name, item.getNickname())
                .setText(R.id.tv_address, item.getAddress())
                .setText(R.id.tv_personal_tag, "个性标签:" + item.getLabel())
                .setText(R.id.tv_user_info, item.getAge() + "岁 " + item.getHeight()+ "厘米 " + item.getVocation());

        LinearLayout ll_object = helper.getView(R.id.ll_object);
        TextView tv_name = helper.getView(R.id.tv_name);
        if(item.getSex() != null) {
            if(item.getSex().equals("男")) {
                tv_name.setCompoundDrawablesWithIntrinsicBounds
                        (0, 0, R.drawable.icon_register_boy_selected, 0);
            }else {
                tv_name.setCompoundDrawablesWithIntrinsicBounds
                        (0, 0, R.drawable.icon_register_girl_selected, 0);
            }
        }

        ll_object.setOnClickListener(new View.OnClickListener() {
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
