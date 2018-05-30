package com.example.ylf019.zlxandroid.zlxweibo.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.utils.FileHelper;
import com.example.ylf019.zlxandroid.utils.StringHelper;
import com.example.ylf019.zlxandroid.utils.Utils;
import com.example.ylf019.zlxandroid.utils.ViewHelper;

import java.util.List;


/**
 * Description ${TODO}
 * Created by yangjinxin on 2017/11/16.
 */

public class RNGPictureUploadAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private int mMaxPictureCount = -1;// -1表示不做最大数据限制
    private OnTakePictureListener onTakePictureListener;

    public RNGPictureUploadAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(final BaseViewHolder helper, final String item) {
        ImageView mIvPicture = helper.getView(R.id.iv_picture);
        ImageView mIvDelete = helper.getView(R.id.iv_delete);
        int pictureHeight = Utils.dip2px(mContext, 170);
        int marginTop = Utils.dip2px(mContext, 12);
        int marginLeft = Utils.dip2px(mContext, 4);

        //初始化图片比例为1：1
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) mIvPicture.getLayoutParams();
        params.width = (int) (pictureHeight * 0.56f);
        params.height = pictureHeight;
        params.setMargins(marginLeft, marginTop, marginTop, marginTop);
        mIvPicture.setLayoutParams(params);
        if (isCanAddPicture() && helper.getPosition() == getItemCount() - 1 && item.equals("")) {//添加按钮
            mIvPicture.setImageResource(R.drawable.publish_page_add_image);
            ViewHelper.setGone(mIvDelete, true);
        }else {//todo 显示图片
            ViewHelper.setGone(mIvDelete, false);
            if(item != null && StringHelper.notEmpty(item)) {
                Glide.with(mContext).load(item).placeholder(R.drawable.ic_default_picture).error(R.drawable.ic_default_picture).into(mIvPicture);
            }else {
                mIvPicture.setImageResource(R.drawable.ic_default_picture);
            }
        }

        mIvPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isAddPicture(helper.getPosition())) {//添加图片
                    if (onTakePictureListener != null) {
                        onTakePictureListener.onTakePictureClick();
                    }
                } else {//查看大图
                    if (StringHelper.notEmpty(item)) {//跳转大图
//                        Intent intent = new Intent(mContext, PictureDetailActivity.class);
//                        intent.putExtra(AppContact.PARAM_KEY_PICTURE_URL, item);
//                        mContext.startActivity(intent);
                    }
                }

            }
        });

        mIvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePicture(helper.getPosition());
                if (StringHelper.notEmpty(item)) {
                    FileHelper.deleteFile(item);
                }
                if (onTakePictureListener != null) {
                    onTakePictureListener.onPictureDelete(helper.getPosition());
                }
            }
        });
    }

    //是否还可以添加图片
    public boolean isCanAddPicture() {
        return mMaxPictureCount == -1 || getData().size() <= mMaxPictureCount;
    }

    //是否添加图片按钮
    public boolean isAddPicture(int position) {
        return isCanAddPicture() && position == getItemCount() - 1;
    }

    //初始化最大图片数量
    public void setMaxPictureCount(int pictureCount) {
        this.mMaxPictureCount = pictureCount;
    }

    //添加图片
    public void addPicture(String picture) {
        if (StringHelper.notEmpty(picture)) {
            if (isCanAddPicture()) {
                    mData.add(picture);
                    refreshData();
                    notifyDataSetChanged();
            }
        }
    }

    //删除图片
    public void deletePicture(int position) {
            mData.remove(position);
            refreshData();
            notifyDataSetChanged();
    }

    public void refreshData() {
        getData().remove("");
        if(getData().size() < mMaxPictureCount) {
            getData().add("");
        }
    }

    public int getDataCount() {
        if(getData().contains("")) {
            return getItemCount() - 1;
        }else {
            return getItemCount();
        }
    }

    public void setOnTakePictureListener(OnTakePictureListener onTakePictureListener) {
        this.onTakePictureListener = onTakePictureListener;
    }

    public interface OnTakePictureListener {
        void onTakePictureClick();
        void onPictureDelete(int position);
    }

}
