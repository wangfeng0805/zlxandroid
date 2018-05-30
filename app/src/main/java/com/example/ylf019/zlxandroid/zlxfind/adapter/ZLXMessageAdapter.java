package com.example.ylf019.zlxandroid.zlxfind.adapter;

import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.http.info.LeaveMsgModel;
import com.example.ylf019.zlxandroid.http.info.WeiboInfo;
import com.example.ylf019.zlxandroid.utils.DateHelper;
import com.example.ylf019.zlxandroid.utils.ViewHelper;
import com.example.ylf019.zlxandroid.view.WeiBoContentTextView;
import com.example.ylf019.zlxandroid.view.WeiBoPictureView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by yjx on 2018/4/20.
 */

public class ZLXMessageAdapter extends BaseQuickAdapter<LeaveMsgModel.InfoBean, BaseViewHolder> {



    public ZLXMessageAdapter(int layoutResId, @Nullable List<LeaveMsgModel.InfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, LeaveMsgModel.InfoBean item) {
        //todo
        helper.setText(R.id.tv_user_name, item.getNick_name())
                .setText(R.id.tv_time, DateHelper.getWeiBoDataTime(item.getLeave_time()));
        CircleImageView mIvAvatar = helper.getView(R.id.iv_avatar);
        WeiBoContentTextView mTvContent = helper.getView(R.id.tv_content);
        WeiBoPictureView mPictureView = helper.getView(R.id.picture_view);
        Glide.with(mContext).load(item.getAvatarurl()).placeholder(R.drawable.sidebar_default_head_icon).dontAnimate().into(mIvAvatar);
        mTvContent.setWeiBoContent(item.getContent());
        ViewHelper.setGone(mPictureView, true);
    }

}
