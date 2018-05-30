package com.example.ylf019.zlxandroid.zlxweibo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.info.WeiXinTokenInfo;
import com.example.ylf019.zlxandroid.http.model.BaseModel;
import com.example.ylf019.zlxandroid.http.model.PublishModel;
import com.example.ylf019.zlxandroid.utils.BitmapCompressUtils;
import com.example.ylf019.zlxandroid.utils.FileHelper;
import com.example.ylf019.zlxandroid.utils.GifSizeFilter;
import com.example.ylf019.zlxandroid.utils.ImageHelper;
import com.example.ylf019.zlxandroid.utils.StringHelper;
import com.example.ylf019.zlxandroid.utils.ToastHelper;
import com.example.ylf019.zlxandroid.utils.Utils;
import com.example.ylf019.zlxandroid.utils.ViewHelper;
import com.example.ylf019.zlxandroid.view.SimpleTextWatcher;
import com.example.ylf019.zlxandroid.zlxweibo.adapter.RNGChatBottomAdapter;
import com.example.ylf019.zlxandroid.zlxweibo.adapter.RNGPictureUploadAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ZlxWeiboCommentAddActivity extends BaseActivity {

    @Bind(R.id.et_content)
    EditText     mEtContent;
//    @Bind(R.id.iv_picture)
//    ImageView    mIvPicture;
//    @Bind(R.id.tv_picture_count)
//    TextView     mTvPictureCount;
//    @Bind(R.id.fl_picture)
//    FrameLayout  mFlPicture;
//    @Bind(R.id.iv_video)
//    ImageView    mIvVideo;
//    @Bind(R.id.fl_video)
//    FrameLayout  mFlVideo;
//    @Bind(R.id.rv_pictures)
//    RecyclerView mRvPictures;
//    @Bind(R.id.image_recycle)
//    RecyclerView mImageRecycle;
//    @Bind(R.id.fl_bottom_view)
//    FrameLayout  mFlBottomView;
    @Bind(R.id.tv_content_number)
    TextView     mTv_content_number;

    public static final int PHOTO_REQUEST_CODE_CHOOSE = 0x09;//matisse 选择图片/视频返回
    private String mWeiBoId, commentId;//微博ID 评论ID
    private List<String> mPictureData = new ArrayList<>();
    private RNGPictureUploadAdapter mPictureAdapter;
    private int mCurrentTabIndex = -1;//当前选则第几个TAB
    private String cropPicturePath;
    private String mComment_id;
    private String mTo_uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zlx_weibo_comment_add);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        mComment_id = intent.getStringExtra("comment_id");
        mTo_uid = intent.getStringExtra("to_uid");
        if(mComment_id == null||mTo_uid == null||TextUtils.isEmpty(mComment_id)||TextUtils.isEmpty(mTo_uid)) {
            Toast.makeText(ZlxWeiboCommentAddActivity.this, "评论失败!", Toast.LENGTH_SHORT).show();
        }

        mEtContent.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                ViewHelper.setGone(mTv_content_number, s.length() <= 140);
                mTv_content_number.setText(String.valueOf(140 - s.length()));
            }
        });
        mEtContent.setFilters(new InputFilter[]{emojiFilter});

    }

    InputFilter emojiFilter = new InputFilter() {
        Pattern emoji = Pattern.compile("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",
                Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            Matcher emojiMatcher = emoji.matcher(source);
            if (emojiMatcher.find()) {
                Toast.makeText(ZlxWeiboCommentAddActivity.this, "不支持输入表情", Toast.LENGTH_SHORT).show();
                return "";
            }
            return null;
        }
    };


    @OnClick(R.id.tv_back)
    void onBackClick() {
        hideSoftInput();
        finish();
    }

    private Handler mHandler = new Handler();


    @OnClick(R.id.tv_push)
    void onpush() {
        //todo 发布
        addWeiboComment();
        hideSoftInput();
    }

    private void addWeiboComment() {
//        netHandler.publishWeibo("o1-_h5BTGrctcR6Myf7LxbK1bK-g",  StringHelper.getEditTextContent(mEtContent), new MyCallBack<BaseModel>() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                ToastHelper.showAlert(ZlxWeiboCommentAddActivity.this, "网络断开");
//            }
//
//            @Override
//            public void onSuccess(Call call, BaseModel data) {
//                if(data.getError_id() == 0) {
//                    ToastHelper.showAlert(ZlxWeiboCommentAddActivity.this, "发布说说成功");
//                    setResult(666);
//                    finish();
//                }else {
//                    ToastHelper.showAlert(ZlxWeiboCommentAddActivity.this, "发布说说失败");
//                }
//            }
//        });
        netHandler.addWeiboComment(mComment_id, StringHelper.getEditTextContent(mEtContent),
                mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_USER_ID), mTo_uid, new MyCallBack<BaseModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(ZlxWeiboCommentAddActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Call call, BaseModel data) {
                if(data.getError_id() == 0) {
                    Toast.makeText(ZlxWeiboCommentAddActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                    setResult(666);
                    finish();
                }else {
                    Toast.makeText(ZlxWeiboCommentAddActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void hideSoftInput() {
        if (getWindow() != null && getWindow().getCurrentFocus() != null) {
            Utils.hideSoftInput(this, getWindow().getCurrentFocus().getWindowToken());
            mEtContent.clearFocus();
        }
    }

    private List<String> picList = new ArrayList<>();
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PHOTO_REQUEST_CODE_CHOOSE) {
                List<Uri> mSelected = Matisse.obtainResult(data);
                picList.clear();
                if (mSelected != null && mSelected.size() > 0) {
                    for (Uri uri : mSelected) {
                        cropPicturePath = ImageHelper.getRealPathFromURI(this, uri);
                        if (StringHelper.notEmpty(cropPicturePath)) {
                            final String croppedPicPath = BitmapCompressUtils.getInstant(null).compressBitmap(cropPicturePath, null);
                            if (FileHelper.isFileExists(croppedPicPath)) {
                                picList.add(croppedPicPath);
                                System.out.println("图片路径：" + croppedPicPath);
                            } else {
                                ToastHelper.showAlert(ZlxWeiboCommentAddActivity.this, "图片选择出错");
                            }
                        } else
                            ToastHelper.showAlert(ZlxWeiboCommentAddActivity.this, "图片选择出错");
                    }
                    uploadImg(picList);
                }
            }
        }
    }

    private void uploadImg(final List<String> picList) {
        if (picList == null || picList.size() == 0)
            return;
        for (String s : picList) {
            mPictureAdapter.addPicture(s);
        }
    }


    @Override
    public void onBackPressed() {
        if (mCurrentTabIndex != -1) {
            mCurrentTabIndex = -1;
//            updateTabViewStatus();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

}
