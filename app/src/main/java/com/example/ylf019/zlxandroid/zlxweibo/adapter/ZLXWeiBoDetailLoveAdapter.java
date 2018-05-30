package com.example.ylf019.zlxandroid.zlxweibo.adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppContact;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.http.info.ZlxLoveInfo;
import com.example.ylf019.zlxandroid.utils.SharedPreferencesHelper;
import com.example.ylf019.zlxandroid.utils.StringHelper;
import com.example.ylf019.zlxandroid.zlxfind.MySpaceActivity;
import com.example.ylf019.zlxandroid.zlxfind.OtherSpaceActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 *      yjx
 */
public class ZLXWeiBoDetailLoveAdapter extends BaseQuickAdapter<ZlxLoveInfo, BaseViewHolder> {

    public ZLXWeiBoDetailLoveAdapter(@LayoutRes int layoutResId, @Nullable List<ZlxLoveInfo> data) {
        super(layoutResId, data);
    }

    public void setLikeList(List<ZlxLoveInfo> loves) {
        mData = loves;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, final ZlxLoveInfo item) {
        helper.setText(R.id.like_name, item.getZan_nick_name()).setText(R.id.like_time, item.getZan_time());
        CircleImageView comment_cir = helper.getView(R.id.comment_cir);
        Glide.with(mContext).load(item.getZan_head_url()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(comment_cir);
        comment_cir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (item != null) {
                    if (item.getLx_num() != null && StringHelper.notEmpty(item.getLx_num())) {
                        if(item.getLx_num().equals(SharedPreferencesHelper.getInstance().getString(AppSpContact.SP_KEY_LX_NUM))) {
                            Intent intent = new Intent(mContext, MySpaceActivity.class);
                            intent.putExtra("lx_num", item.getLx_num());
                            mContext.startActivity(intent);
                        }else {
                            Intent intent = new Intent(mContext, OtherSpaceActivity.class);
                            intent.putExtra("lx_num", item.getLx_num());
                            mContext.startActivity(intent);
                        }
                    }
                }
            }
        });
    }
}
