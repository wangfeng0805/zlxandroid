package com.example.ylf019.zlxandroid.zlxweibo;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.http.MyCallBack;
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

public class ZlxWeiboAddActivity extends BaseActivity implements RNGPictureUploadAdapter.OnTakePictureListener {

    @Bind(R.id.et_content)
    EditText     mEtContent;
    @Bind(R.id.rv_pictures)
    RecyclerView mRvPictures;
    @Bind(R.id.tv_content_number)
    TextView     mTv_content_number;

    public static final int PHOTO_REQUEST_CODE_CHOOSE = 0x09;//matisse 选择图片/视频返回
    @Bind(R.id.item_tab1)
    TextView mItemTab1;
    @Bind(R.id.item_tab2)
    TextView mItemTab2;
    @Bind(R.id.item_tab3)
    TextView mItemTab3;
    @Bind(R.id.item_tab4)
    TextView mItemTab4;
    @Bind(R.id.item_tab5)
    TextView mItemTab5;
    private String mWeiBoId, commentId;//微博ID 评论ID
    private List<String> mPictureData = new ArrayList<>();
    private RNGPictureUploadAdapter mPictureAdapter;
    private int mCurrentTabIndex = -1;//当前选则第几个TAB
    private boolean isLoading;
    private String  cropPicturePath;
    //    private UploadManager uploadManager;
    private boolean booleanExtra;
    private String  mPictureUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zlx_weibo_add);
        ButterKnife.bind(this);
        setUpViewComponent();
        mEtContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideTab();
            }
        });
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
                Toast.makeText(ZlxWeiboAddActivity.this, "不支持输入表情", Toast.LENGTH_SHORT).show();
                return "";
            }
            return null;
        }
    };

    private void hideTab() {
        mCurrentTabIndex = -1;
    }

    private void setUpViewComponent() {
        setUpRecycle();
    }

    /**
     * 初始化图片栏
     */
    private void setUpRecycle() {
        mRvPictures.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        mPictureData.add("");
        mPictureAdapter = new RNGPictureUploadAdapter(R.layout.item_wei_bo_upload_picture, mPictureData);
        mRvPictures.setAdapter(mPictureAdapter);
        mPictureAdapter.setMaxPictureCount(9);
        mPictureAdapter.setOnTakePictureListener(this);
    }

    @OnClick(R.id.tv_back)
    void onBackClick() {
        hideSoftInput();
        finish();
    }

    private Handler mHandler = new Handler();

    @OnClick(R.id.tv_submit)
    void onpush() {
        //todo 发布
        if (!isLoading && checkInput()) {
            if (mPictureAdapter.getData().size() == 1 && mPictureAdapter.getData().get(0).equals("")) {
                publishContent();
            } else {
                publishComment();
            }
        }
        hideSoftInput();
    }

    private void publishContent() {
        netHandler.publishWeibo(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LOGIN_ID), StringHelper.getEditTextContent(mEtContent), label,
                mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), new MyCallBack<BaseModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.showAlert(ZlxWeiboAddActivity.this, "网络断开");
            }

            @Override
            public void onSuccess(Call call, BaseModel data) {
                if (data.getError_id() == 0) {
                    ToastHelper.showAlert(ZlxWeiboAddActivity.this, "发布说说成功");
                    setResult(666);
                    finish();
                } else {
                    ToastHelper.showAlert(ZlxWeiboAddActivity.this, "发布说说失败");
                }
            }
        });
    }

    private String mImg_url = "";
    private int    i        = 0;

    /**
     * 完成文件上传之后发布微博
     */
    private void publishComment() {
//        isLoading = true;
        final String content = StringHelper.getEditTextContent(mEtContent);
//        String picList = new Gson().toJson(mPictureAdapter.getData());
        final StringBuffer stringBuffer = new StringBuffer();
        for (final String image : mPictureAdapter.getData()) {
            if (image.equals("")) {
                i++;
                continue;
            }
            File file = new File(image);
            netHandler.loadImage(file, null, new MyCallBack<PublishModel>() {
                @Override
                public void onFailure(Call call, IOException e) {
                    ToastHelper.showAlert(ZlxWeiboAddActivity.this, "网络断开");
                }

                @Override
                public void onSuccess(Call call, PublishModel data) {
//                    ToastHelper.showAlert(ZlxWeiboAddActivity.this, "成功!!!");
                    stringBuffer.append(data.getImg_url() + ",");
                    i++;
                    if (mPictureAdapter.getData().size() == i) {
                        if (!stringBuffer.toString().equals("")) {
                            stringBuffer.deleteCharAt(stringBuffer.length() - 1);
                        }
                        mImg_url = stringBuffer.toString();
                        netHandler.publishWeibo(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LOGIN_ID), content, mImg_url, label,
                                mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), new MyCallBack<BaseModel>() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                ToastHelper.showAlert(ZlxWeiboAddActivity.this, "网络断开");
                            }

                            @Override
                            public void onSuccess(Call call, BaseModel data) {
                                if (data.getError_id() == 0) {
                                    ToastHelper.showAlert(ZlxWeiboAddActivity.this, "发布说说成功");
                                    setResult(666);
                                    finish();
                                } else {
                                    ToastHelper.showAlert(ZlxWeiboAddActivity.this, "发布说说失败");
                                }
                            }
                        });
                    }
                }
            });
        }
    }

    /**
     * 发布成功重置View状态
     */
    private void resetViewStatus() {
        mEtContent.setText("");
        mCurrentTabIndex = -1;
    }

    @Override
    public void onPictureDelete(int position) {
//        int pictureCount = mPictureAdapter.getDataCount();
//        ViewHelper.setGone(mTvPictureCount, pictureCount == 0);
//        mTvPictureCount.setText(String.valueOf(pictureCount));
    }

    //跳转添加图片
    @Override
    public void onTakePictureClick() {
        if (!isLoading) {
            if (getCurrentFocus() != null) {
                Utils.hideSoftInput(this, getCurrentFocus().getWindowToken());
            }
            onPickPhoto();
        }
    }

    private void onPickPhoto() {
        Matisse.from(this)
                .choose(MimeType.ofAll())
                .countable(true)
                .capture(false)
                .maxSelectable(mPictureAdapter != null && mPictureAdapter.getDataCount() > 0 ? 9 - mPictureAdapter.getDataCount() : 9)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .captureStrategy(
                        new CaptureStrategy(true, "com.zhihu.matisse.sample.fileprovider"))
                .theme(R.style.Matisse_Dracula)
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new GlideEngine())
                .forResult(PHOTO_REQUEST_CODE_CHOOSE);
    }

    private void hideSoftInput() {
        if (getWindow() != null && getWindow().getCurrentFocus() != null) {
            Utils.hideSoftInput(this, getWindow().getCurrentFocus().getWindowToken());
            mEtContent.clearFocus();
        }
    }

    private boolean checkInput() {
        if (StringHelper.isEditTextEmpty(mEtContent)) {
//            ToastHelper.showIconAndTextToastCenter(false, "内容不能为空");
            ToastHelper.showAlert(this, "内容不能为空");
            return false;
        }
        if (StringHelper.getEditTextContent(mEtContent).length() > 140) {
//            ToastHelper.showIconAndTextToastCenter(false, "内容长度在140字以内");
            ToastHelper.showAlert(this, "内容长度在140字以内");
            return false;
        }
        return true;
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
                                ToastHelper.showAlert(ZlxWeiboAddActivity.this, "图片选择出错");
                            }
                        } else
                            ToastHelper.showAlert(ZlxWeiboAddActivity.this, "图片选择出错");
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
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    private String label = "家乡美景";

    @OnClick({R.id.item_tab1, R.id.item_tab2, R.id.item_tab3, R.id.item_tab4, R.id.item_tab5})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.item_tab1:
                label = "家乡美景";
                setTabColor(mItemTab1);
                break;
            case R.id.item_tab2:
                label = "民风民俗";
                setTabColor(mItemTab2);
                break;
            case R.id.item_tab3:
                label = "广告";
                setTabColor(mItemTab3);
                break;
            case R.id.item_tab4:
                label = "求助";
                setTabColor(mItemTab4);
                break;
            case R.id.item_tab5:
                label = "老乡新动态";
                setTabColor(mItemTab5);
                break;
        }
    }

    private void setTabColor(TextView itemTab) {
        mItemTab1.setBackgroundResource(R.drawable.tab_shuoshuo_bg);
        mItemTab2.setBackgroundResource(R.drawable.tab_shuoshuo_bg);
        mItemTab3.setBackgroundResource(R.drawable.tab_shuoshuo_bg);
        mItemTab4.setBackgroundResource(R.drawable.tab_shuoshuo_bg);
        mItemTab5.setBackgroundResource(R.drawable.tab_shuoshuo_bg);
        mItemTab1.setTextColor(getResources().getColor(R.color.primary_text));
        mItemTab2.setTextColor(getResources().getColor(R.color.primary_text));
        mItemTab3.setTextColor(getResources().getColor(R.color.primary_text));
        mItemTab4.setTextColor(getResources().getColor(R.color.primary_text));
        mItemTab5.setTextColor(getResources().getColor(R.color.primary_text));
        itemTab.setBackgroundResource(R.drawable.tab_shuoshuo_selected_bg);
        itemTab.setTextColor(Color.WHITE);
    }


}
