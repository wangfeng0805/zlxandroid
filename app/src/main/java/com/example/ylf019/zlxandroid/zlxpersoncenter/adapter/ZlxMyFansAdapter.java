package com.example.ylf019.zlxandroid.zlxpersoncenter.adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.http.model.ZlxMyFansModel;
import com.example.ylf019.zlxandroid.utils.SharedPreferencesHelper;
import com.example.ylf019.zlxandroid.utils.ViewHelper;
import com.example.ylf019.zlxandroid.zlxfind.MySpaceActivity;
import com.example.ylf019.zlxandroid.zlxfind.OtherSpaceActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Description ${TODO}
 * Created by yangjinxin on 2017/11/24.
 */

public class ZlxMyFansAdapter extends BaseQuickAdapter<ZlxMyFansModel.InfoBean, BaseViewHolder> {

    private OnLikeInterface mOnLikeInterface;
    private boolean mIsfollowNotShow = false;

    public ZlxMyFansAdapter(@LayoutRes int layoutResId, @Nullable List<ZlxMyFansModel.InfoBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final ZlxMyFansModel.InfoBean item) {
        CircleImageView mIvAvatar = helper.getView(R.id.iv_avatar);
//        ImageView imageView = helper.getView(R.id.iv_guanzhu);
//        TextView mTv_name = helper.getView(R.id.tv_name);
//        TextView tv_content = helper.getView(R.id.tv_content);
        helper.getView(R.id.iv_guanzhu);
        helper.setText(R.id.tv_name,item.getNickname()).setText(R.id.tv_content, item.getCity()+item.getArea());
        Glide.with(mContext).load(item.getHeadurl()).placeholder(R.drawable.sidebar_default_head_icon).dontAnimate().into(mIvAvatar);
        mIvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });


    }

    public void setOnLikeInterface(OnLikeInterface onLikeInterface) {
        this.mOnLikeInterface = onLikeInterface;
    }

    public interface OnLikeInterface {
        void onLikeClick(ZlxMyFansModel.InfoBean item, int position);
    }

}
