package com.example.ylf019.zlxandroid.view;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppContact;
import com.example.ylf019.zlxandroid.home.GalleryActivity;
import com.example.ylf019.zlxandroid.http.info.ZLXImageInfo;
import com.example.ylf019.zlxandroid.utils.StringHelper;
import com.example.ylf019.zlxandroid.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Author:    guozhangyu
 * Description: 微博图片View
 */
public class WeiBoPictureView extends FrameLayout {
    @Bind(R.id.gl_picture)
    GridLayout mGlPicture;
    private ArrayList<String> mList = new ArrayList<>();

    public WeiBoPictureView(Context context) {
        this(context, null);
    }

    public WeiBoPictureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WeiBoPictureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.include_wei_bo_picture_view, null, false);
        ButterKnife.bind(this, rootView);
        addView(rootView);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    /**
     * 绑定微博图片
     *
     * @param data 微博数据
     */
    public void bindViewData(List<ZLXImageInfo> data, int imageSize) {
        bindViewDataforImage(data, imageSize);
    }

    /**
     * 绑定微博图片
     */

    public void bindViewDataforImage(final List<ZLXImageInfo> picList, int imageSize) {
        mGlPicture.removeAllViews();
        mList.clear();
        if (picList != null && picList.size() > 0) {
            for (ZLXImageInfo rngImageBean : picList) {
                mList.add(rngImageBean.getPics());
            }
        }

        if (picList != null && picList.size() > 0) {
            int pictureWidth = Utils.getScreenWidth(getContext()) - 16;
            int pictureHeight;
            int margin = Utils.dip2px(getContext(), 2);
            if (picList.size() == 1) {
                if (imageSize == 1) {
                    pictureWidth = Utils.dip2px(getContext(), 200);
                    pictureHeight = Utils.dip2px(getContext(), 300);
                } else {
                    pictureWidth = Utils.dip2px(getContext(), 300);
                    pictureHeight = Utils.dip2px(getContext(), 200);
                }
                mGlPicture.setColumnCount(1);
            } else if (picList.size() == 2) {
                pictureWidth = (pictureWidth - margin * 6) / 2;
                pictureHeight = pictureWidth;
                mGlPicture.setColumnCount(2);
            } else if (picList.size() == 4) {
                pictureWidth = (pictureWidth - margin * 6) / 3;
                pictureHeight = pictureWidth;
                mGlPicture.setColumnCount(2);
            } else {
                pictureWidth = (pictureWidth - margin * 6) / 3;
                pictureHeight = pictureWidth;
                mGlPicture.setColumnCount(3);
            }
            mGlPicture.setRowCount((picList.size() - 1) / 3 + 1);
            for (int i = 0; i < picList.size(); i++) {
                final ZLXImageInfo picture = picList.get(i);
                final String pics = picture.getPics();
                int type = picture.getType();
                if (StringHelper.notEmpty(pics)) {
                    View view = LayoutInflater.from(getContext()).inflate(R.layout.include_weibo_imageview, null, false);
                    ImageView pictureView = (ImageView) view.findViewById(R.id.iv_picture);
                    RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.relative);
                    ImageView imageView = (ImageView) view.findViewById(R.id.iv_dongtu);
                    if (type == 1)
                        imageView.setVisibility(VISIBLE);
                    else
                        imageView.setVisibility(GONE);
                    Glide.with(getContext()).load(pics)
                            .asBitmap()
                            .placeholder(R.drawable.ic_default_picture)
                            .error(R.drawable.ic_default_picture)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .override(pictureWidth, pictureHeight)
                            .centerCrop()
                            .into(pictureView);
                    pictureView.setTag(i);
                    pictureView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int index = (Integer) v.getTag();
                            if (StringHelper.notEmpty(pics)) {
                                Intent intent = new Intent(getContext(), GalleryActivity.class);
                                intent.putStringArrayListExtra(AppContact.PARAM_KEY_PICTURES, mList);
                                intent.putExtra(AppContact.PARAM_KEY_PICTURE_INDEX, index);
                                getContext().startActivity(intent);
                            }
                        }
                    });
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                    params.width = pictureWidth;
                    params.height = pictureHeight;
                    int marginLeft = (i == 0 || (picList.size() == 4 && i == 2) || (picList.size() != 4 && (i % 3 == 0))) ? 2 : margin;
                    int marginRight = ((i == picList.size() - 1) || (picList.size() != 4 && (i % 3 == 2))) ? 2 : margin;
                    int marginBottom = margin;
                    params.setMargins(marginLeft, margin, marginRight, marginBottom);
                    relativeLayout.setLayoutParams(params);
                    mGlPicture.addView(view);
                }
            }
        }
    }
}
