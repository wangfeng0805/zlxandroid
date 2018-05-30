package com.example.ylf019.zlxandroid.home;

import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppContact;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.utils.ToastHelper;
import com.example.ylf019.zlxandroid.view.MyViewPager;
import com.github.chrisbanes.photoview.PhotoView;
import com.pnikosis.materialishprogress.ProgressWheel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class GalleryActivity extends BaseActivity {

    private MyViewPager       mViewPager;
    private ArrayList<String> mUrls;
    private int               mPosition;
    private int               mCurrentIndex;
    private TextView          mTvIndex;
    private String            urlIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();
        setContentView(R.layout.activity_gallery);
        mViewPager = (MyViewPager) findViewById(R.id.viewPager);
        mTvIndex = (TextView) findViewById(R.id.tv_index);

        Intent intent = getIntent();
        mUrls = intent.getStringArrayListExtra(AppContact.PARAM_KEY_PICTURES);
        mPosition = intent.getIntExtra(AppContact.PARAM_KEY_PICTURE_INDEX, 0);
        if (mUrls == null || mUrls.size() == 0) {
            finish();
            return;
        }
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mUrls.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public View instantiateItem(ViewGroup container, int position) {
                View view = LayoutInflater.from(GalleryActivity.this).inflate(R.layout.layout_photo_pager, container, false);
                final PhotoView photoView = (PhotoView) view.findViewById(R.id.photoview);
                final ProgressWheel progress_wheel = (ProgressWheel) view.findViewById(R.id.progress_wheel);
                String url = mUrls.get(position);
                if (url.contains("?imageslim"))
                    url = url.substring(0, url.length() -10);
                if (url.endsWith(".jpg") || url.endsWith(".png")) {
                    Glide.with(GalleryActivity.this).load(url).
                            error(R.drawable.ic_default_picture).into(photoView);
                    photoView.setTag(R.id.image_tag, position);
                    photoView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            int position = (int) v.getTag(R.id.image_tag);
                            urlIndex = mUrls.get(position);
                            if (urlIndex.contains("?imageslim"))
                                urlIndex = urlIndex.substring(0, urlIndex.length() - 10);
                            new AlertDialog.Builder(GalleryActivity.this).setTitle("提示")////设置对话框标题
                                    .setMessage("是否保存该图片？")//设置显示的内容
                                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {//添加确定按钮
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                            int index = urlIndex.lastIndexOf("/");
                                            String mFileName = urlIndex.substring(index + 1);
                                            savePicture(mFileName, urlIndex);
                                        }
                                    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {//添加返回按钮
                                @Override
                                public void onClick(DialogInterface dialog, int which) {//响应事件
                                }
                            }).show();//在按键响应事件中显示此对话框

                            return false;
                        }
                    });
                } else {
                    Glide.with(GalleryActivity.this).load(url).into(new GlideDrawableImageViewTarget(photoView) {
                        @Override
                        public void onResourceReady(GlideDrawable drawable, GlideAnimation anim) {
                            super.onResourceReady(drawable, anim);
                            //在这里添加一些图片加载完成的操作
                            photoView.setVisibility(VISIBLE);
                            progress_wheel.setVisibility(GONE);
                        }

                        @Override
                        public void onLoadFailed(Exception e, Drawable errorDrawable) {
                            super.onLoadFailed(e, errorDrawable);
                            photoView.setVisibility(VISIBLE);
                            progress_wheel.setVisibility(GONE);
                        }

                        @Override
                        public void onStop() {
                            super.onStop();
                            photoView.setVisibility(VISIBLE);
                            progress_wheel.setVisibility(GONE);
                        }

                        @Override
                        public void onLoadCleared(Drawable placeholder) {
                            super.onLoadCleared(placeholder);
                            photoView.setVisibility(VISIBLE);
                            progress_wheel.setVisibility(GONE);
                        }

                        @Override
                        public void onLoadStarted(Drawable placeholder) {
                            super.onLoadStarted(placeholder);
                            photoView.setVisibility(GONE);
                            progress_wheel.setVisibility(VISIBLE);
                        }
                    });


                }

                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentIndex = position;
                mTvIndex.setText((mCurrentIndex + 1) + " / " + mUrls.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTvIndex.setText((mCurrentIndex + 1) + " / " + mUrls.size());
        mViewPager.setCurrentItem(mPosition);

    }


    //Glide保存图片
    public void savePicture(final String fileName, String url) {
        Glide.with(GalleryActivity.this).load(url).asBitmap().toBytes().into(new SimpleTarget<byte[]>() {
            @Override
            public void onResourceReady(byte[] bytes, GlideAnimation<? super byte[]> glideAnimation) {
                try {
                    savaFileToSD(fileName, bytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //往SD卡写入文件的方法
    public void savaFileToSD(String filename, byte[] bytes) throws Exception {
        //如果手机已插入sd卡,且app具有读写sd卡的权限
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String filePath = AppContact.FILE_DATA_PICTURE_PATH;
            File dir1 = new File(filePath);
            if (!dir1.exists()) {
                dir1.mkdirs();
            }
            filename = filePath + "/" + filename;
            //这里就不要用openFileOutput了,那个是往手机内存中写数据的
            FileOutputStream output = new FileOutputStream(filename);
            output.write(bytes);
            //将bytes写入到输出流中
            output.close();
            //关闭输出流
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(this.getContentResolver(),
                        dir1.getAbsolutePath(), filename, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + filename)));
            ToastHelper.showBottomAlert(this, "图片已成功保存到" + filePath);
        } else
//            ToastHelper.showIconAndTextToastCenter(false, "SD卡不存在或者不可读写");
            ToastHelper.showBottomAlert(this, "SD卡不存在或者不可读写");
    }

}


