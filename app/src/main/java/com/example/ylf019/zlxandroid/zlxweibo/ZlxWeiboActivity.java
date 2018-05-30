package com.example.ylf019.zlxandroid.zlxweibo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppContact;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.appevent.WeiBoLikeEvent;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.info.WeiboInfo;
import com.example.ylf019.zlxandroid.http.info.ZLXImageInfo;
import com.example.ylf019.zlxandroid.http.info.ZlxCommentInfo;
import com.example.ylf019.zlxandroid.http.info.ZlxLoveInfo;
import com.example.ylf019.zlxandroid.http.model.BaseModel;
import com.example.ylf019.zlxandroid.utils.DateHelper;
import com.example.ylf019.zlxandroid.utils.StringHelper;
import com.example.ylf019.zlxandroid.utils.ToastHelper;
import com.example.ylf019.zlxandroid.utils.ViewHelper;
import com.example.ylf019.zlxandroid.view.WeiBoContentTextView;
import com.example.ylf019.zlxandroid.view.WeiBoPictureView;
import com.example.ylf019.zlxandroid.zlxweibo.adapter.ZLXWeiBoDetailCommentAdapter;
import com.example.ylf019.zlxandroid.zlxweibo.adapter.ZLXWeiBoDetailLoveAdapter;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class ZlxWeiboActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.top_panel_view)
    LinearLayout          topPanelView;
    @Bind(R.id.rl_toptitlebar_back)
    RelativeLayout        rl_toptitlebar_back;//返回
    @Bind(R.id.tv_title)
    TextView              tv_title;//标题
    @Bind(R.id.recycler)
    RecyclerView          mRecycleView;
    @Bind(R.id.easylayout)
    EasyRefreshLayout     mEasylayout;
//    @Bind(R.id.ll_more)
//    LinearLayout          llMore;
    @Bind(R.id.iv_like_tag)
    ImageView             ivLikeTag;
    @Bind(R.id.tv_like_tag)
    TextView              tvLikeTag;
    @Bind(R.id.ll_comment)
    LinearLayout          mLlComment;
    @Bind(R.id.ll_like)
    LinearLayout          mLlLike;

    private WeiboInfo                 mWeiboInfo;
    private ZLXWeiBoDetailLoveAdapter loveAdapter;
    private List<ZlxCommentInfo> commentListData = new ArrayList<>();
    private List<ZlxLoveInfo>    lovesListData   = new ArrayList<>();
    private ZLXWeiBoDetailCommentAdapter commentAdapter;
    private CircleImageView mIvAvatar;
    private TextView mTvUserName;
    private TextView mTvTime;
    private LinearLayout llComment;
    private LinearLayout llLike;
    private WeiBoContentTextView mTvContent;
    private WeiBoPictureView mPictureView;
    private Object mDetailData;
    private boolean isComment = true, isFirstClickLove = false;//是否为第一次点击点赞模块
    private String weiBoId;
    private int weiBoPosition;

    private int commentPage = 1, lovesPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zlx_weibo);
        ButterKnife.bind(this);
        setUpViewComponent();
    }

    private void setUpViewComponent() {
        tv_title.setText("说说详情");
        mEasylayout.setLoadMoreModel(LoadModel.ADVANCE_MODEL);
        weiBoId = getIntent().getStringExtra(AppContact.PARAM_WEIBO_ID);
        weiBoPosition = getIntent().getIntExtra(AppContact.PARAM_KEY_POSITION, 0);
        if (weiBoId == null) {
            ToastHelper.showAlert(this, "未知微博");
            finish();
        }
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        if (commentAdapter == null)
            commentAdapter = new ZLXWeiBoDetailCommentAdapter(R.layout.item_rng_weibo_detail_comment, commentListData);
        commentAdapter.openLoadAnimation();
        commentAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        commentAdapter.setNotDoAnimationCount(3);//第一屏不显示动画
        commentAdapter.addHeaderView(initWeiboHeader());
//        commentAdapter.onCommentClick(this);
        if (loveAdapter == null)
            loveAdapter = new ZLXWeiBoDetailLoveAdapter(R.layout.item_rng_weibo_detail_like, lovesListData);
        loveAdapter.openLoadAnimation();
        loveAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        loveAdapter.setNotDoAnimationCount(5);//第一屏不显示动画
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                //加载更多评论或点赞
//                if (isComment) {
//                    commentPage++;
//                    onCommentDataMore();
//                } else {
//                    lovesPage++;
//                    onLikeDataMore();
//                }
                mEasylayout.loadMoreComplete();
            }

            @Override
            public void onRefreshing() {
                //下拉刷新
                getDetailData();
            }
        });
        getDetailData();
        mLlComment.setOnClickListener(this);
        mLlLike.setOnClickListener(this);
        rl_toptitlebar_back.setOnClickListener(this);
    }


    @SuppressLint("SetTextI18n")
    private void setDetailData() {
        Glide.with(this).load(mWeiboInfo.getHeadurl()).placeholder(R.drawable.sidebar_default_head_icon).dontAnimate().into(mIvAvatar);
        mTvContent.setWeiBoContent(mWeiboInfo.getContent());
        mTvTime.setText(DateHelper.getWeiBoDataTime(mWeiboInfo.getAdd_time()));
        if (mWeiboInfo.getImg_url_arr() != null && mWeiboInfo.getImg_url_arr().size() > 0) {
            ViewHelper.setGone(mPictureView, false);
            List<ZLXImageInfo> list = new ArrayList<>();
            for (String s : mWeiboInfo.getImg_url_arr()) {
                ZLXImageInfo zlxImageInfo = new ZLXImageInfo();
                zlxImageInfo.setPics("https://waptest1.ylfcf.com/Data/upload/" + s);
                zlxImageInfo.setType(0);
                list.add(zlxImageInfo);
            }
            mPictureView.bindViewData(list, mWeiboInfo.getImg_url_arr().size());
        } else {
            ViewHelper.setGone(mPictureView, true);
        }
        tvCommentNum1.setText("评论" + commentListData.size());
        tvLikeNum1.setText("赞" + lovesListData.size());

        if(tvCommentNum2 != null) {
            tvCommentNum2.setText("评论" + commentListData.size());
        }
        if(tvLikeNum2 != null) {
            tvLikeNum2.setText("赞" + lovesListData.size());
        }

        if (isComment) {
            mRecycleView.setAdapter(commentAdapter);
            commentAdapter.notifyDataSetChanged();
        } else {
            mRecycleView.setAdapter(loveAdapter);
            loveAdapter.setLikeList(lovesListData);
            loveAdapter.notifyDataSetChanged();
        }
    }

    private boolean isFirst = true;
    private RelativeLayout mLl_empty1;
    private RelativeLayout mLl_empty2;
    private TextView       tvCommentNum1;
    private TextView       tvCommentNum2;
    private TextView       tvLikeNum1;
    private TextView       tvLikeNum2;

    View initWeiboHeader() {
        View view = LayoutInflater.from(this).inflate(R.layout.item_weibo_detail_headview, mRecycleView, false);
        LinearLayout llWeiBo = (LinearLayout) view.findViewById(R.id.ll_wei_bo);
        mIvAvatar = (CircleImageView) view.findViewById(R.id.iv_avatar);
        mTvUserName = (TextView) view.findViewById(R.id.tv_user_name);
//        tvPhoneSource = (TextView) view.findViewById(R.id.tv_phone_source);
        mTvTime = (TextView) view.findViewById(R.id.tv_time);
//        mTvAudioTime = (TextView) view.findViewById(R.id.tv_audio_time);
//        ivGuanTag = (ImageView) view.findViewById(R.id.iv_guan);
//        mIvAudio = (ImageView) view.findViewById(R.id.iv_audio);
//        mLlAudio = (LinearLayout) view.findViewById(R.id.ll_audio);
        llComment = (LinearLayout) view.findViewById(R.id.ll_comment_tag);
        llLike = (LinearLayout) view.findViewById(R.id.ll_like_tag);
//        fl_audio = (FrameLayout) view.findViewById(R.id.fl_audio);
        mTvContent = (WeiBoContentTextView) view.findViewById(R.id.tv_content);
        mPictureView = (WeiBoPictureView) view.findViewById(R.id.picture_view);
        if (isFirst) {
            mLl_empty1 = (RelativeLayout) view.findViewById(R.id.ll_empty);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
            TextView textView = (TextView) view.findViewById(R.id.tv_content2);
            imageView.setImageResource(R.drawable.empty_bb);
            tvCommentNum1 = (TextView) view.findViewById(R.id.tv_comment_num);
            tvLikeNum1 = (TextView) view.findViewById(R.id.tv_like_num);
//            ivAttention1 = (ImageView) view.findViewById(R.id.iv_attention);
//            tvPx1 = (TextView) view.findViewById(R.id.tv_px);
            textView.setText("还未有任何评论");
            isFirst = false;
//            ivAttention1.setOnClickListener(this);
//            tvPx1.setOnClickListener(this);
        } else {
            mLl_empty2 = (RelativeLayout) view.findViewById(R.id.ll_empty);
            tvCommentNum2 = (TextView) view.findViewById(R.id.tv_comment_num);
            tvLikeNum2 = (TextView) view.findViewById(R.id.tv_like_num);
//            ivAttention2 = (ImageView) view.findViewById(R.id.iv_attention);
//            tvPx2 = (TextView) view.findViewById(R.id.tv_px);
            ImageView imageView = (ImageView) view.findViewById(R.id.iv_icon);
            TextView textView = (TextView) view.findViewById(R.id.tv_content2);
            imageView.setImageResource(R.drawable.empty_message);
            textView.setText("还未有任何点赞");
//            ivAttention2.setOnClickListener(this);
//            tvPx2.setOnClickListener(this);
        }
//        fl_audio.setOnClickListener(this);
//        mIvAudio.setOnClickListener(this);
        llComment.setOnClickListener(this);
        llLike.setOnClickListener(this);
//        if (isPhotoClick)//从图集中点击进入不显示说说的头部信息
//            llWeiBo.setVisibility(View.GONE);
//        else
        llWeiBo.setVisibility(View.VISIBLE);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_comment_tag:
                clickComment();
                isComment = true;
                break;

            case R.id.ll_like_tag:
//                mEasylayout.setLoadMoreModel(LoadModel.ADVANCE_MODEL);
                if (!isFirstClickLove) {
                    loveAdapter.addHeaderView(initWeiboHeader());
                    setDetailData();
                }
                isFirstClickLove = true;
                isComment = false;
                mRecycleView.setAdapter(loveAdapter);
//                loveAdapter.setLikeList(lovesListData);
                if (lovesListData.size() == 0) {
                    mLl_empty2.setVisibility(View.VISIBLE);
                } else {
                    mLl_empty2.setVisibility(View.GONE);
                }
                tvCommentNum1.setTextColor(getResources().getColor(R.color.secondary_text));
                if (tvCommentNum2 != null) {
                    tvCommentNum2.setTextColor(getResources().getColor(R.color.secondary_text));
                }
                tvLikeNum1.setTextColor(getResources().getColor(R.color.primary_text));
                if (tvLikeNum2 != null) {
                    tvLikeNum2.setTextColor(getResources().getColor(R.color.primary_text));
                }
                break;

            case R.id.ll_comment:
                //todo 评论按钮
                Intent intent = new Intent(ZlxWeiboActivity.this, ZlxWeiboCommentAddActivity.class);
                intent.putExtra("comment_id", mWeiboInfo.getId());
                intent.putExtra("to_uid", mWeiboInfo.getDy_user_id());
                startActivityForResult(intent, 26);
                setResult(666);
                break;

            case R.id.ll_like:
                //todo 点赞按钮
                if(mWeiboInfo.getIs_zan() == 0) {
                    netHandler.requestLoveWeibo(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), mWeiboInfo.getDy_user_id(), mWeiboInfo.getId(), new MyCallBack<BaseModel>() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            ToastHelper.showAlert(ZlxWeiboActivity.this, "网络断开");
                        }

                        @Override
                        public void onSuccess(Call call, BaseModel data) {
                            if(data.getError_id() == 0) {
//                        ZLXWeiboListAdapter.changeItemLikeByPosition(weiBoPosition, event);
//                                onDataRefresh();
                                WeiBoLikeEvent event = new WeiBoLikeEvent(weiBoPosition, true);
                                EventBus.getDefault().post(event);
                                mWeiboInfo.setIs_zan(1);
//                                setWeiboLoveStats();
                                getDetailData();
//                                setResult(666);
                            }else {
//                                ToastHelper.showAlert(ZlxWeiboActivity.this, "网络异常");
                                ToastHelper.showAlert(ZlxWeiboActivity.this, "已经赞过了哦");
                            }
                        }
                    });
                }else {
                    ToastHelper.showAlert(this, "已经赞过了哦");
                }
                break;
            case R.id.rl_toptitlebar_back:
                finish();
                break;
        }

    }

    void setWeiboLoveStats() {
        if (mWeiboInfo.getIs_zan() == 1) {
            tvLikeTag.setTextColor(getResources().getColor(R.color.primary_text));
            tvLikeTag.setText("已赞");
            ivLikeTag.setImageResource(R.drawable.icon_liked);
        } else {
            tvLikeTag.setTextColor(getResources().getColor(R.color.secondary_text));
            tvLikeTag.setText("赞");
            ivLikeTag.setImageResource(R.drawable.icon_unlike);
        }
    }


    private void clickComment() {
        mRecycleView.setAdapter(commentAdapter);
//                if (isHot == 0)
//                    commentAdapter.setCommentList(commentListData);
//                else
//                    commentAdapter.setCommentList(commentHotListData);
        if (commentListData.size() == 0) {
            mLl_empty1.setVisibility(View.VISIBLE);
        } else {
            mLl_empty1.setVisibility(View.GONE);
        }
        tvCommentNum1.setTextColor(getResources().getColor(R.color.primary_text));
        if (tvCommentNum2 != null) {
            tvCommentNum2.setTextColor(getResources().getColor(R.color.primary_text));
        }
        tvLikeNum1.setTextColor(getResources().getColor(R.color.secondary_text));
        if (tvLikeNum2 != null) {
            tvLikeNum2.setTextColor(getResources().getColor(R.color.secondary_text));
        }
    }

    private void getDetailData() {
//        ProgressDialogHelper.showProgress(this);
        if (weiBoId == null || StringHelper.isEmpty(weiBoId))
            return;
        netHandler.getWeiboDetail(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), weiBoId, new MyCallBack<List<WeiboInfo>>() {
            @Override
            public void onFailure(Call call, IOException e) {
//                ProgressDialogHelper.dismissProgress();
                ToastHelper.showAlert(ZlxWeiboActivity.this, "请求失败,请重试");
                mEasylayout.refreshComplete();
            }

            @Override
            public void onSuccess(Call call, List<WeiboInfo> data1) {
//                ProgressDialogHelper.dismissProgress();
                if(data1 != null && data1.size() > 0) {
                    WeiboInfo data = data1.get(0);
                    if (data != null) {
                        if (data.getError_id() == 0) {
                            if (null != lovesListData && lovesListData.size() > 0)
                                lovesListData.clear();
                            if (null != commentListData && commentListData.size() > 0)
                                commentListData.clear();

                            if(data.getZan_arr() != null) {
                                lovesListData.addAll(data.getZan_arr());
                            }
                            if (data.getArr() != null) {
                                commentListData.addAll(data.getArr());
                            }
                            if(commentAdapter != null) {
                                commentAdapter.notifyDataSetChanged();
                            }
                            if(loveAdapter != null) {
                                loveAdapter.notifyDataSetChanged();
                            }
                            mWeiboInfo = data;
                            setWeiboLoveStats();
                            setDetailData();
                            if (isComment) {
                                if (data.getArr() != null && data.getArr().size() > 0) {
                                    mLl_empty1.setVisibility(View.GONE);
                                } else {
                                    mLl_empty1.setVisibility(View.VISIBLE);
                                }
                            } else {
                                if (data.getZan_arr() != null && data.getZan_arr().size() > 0) {
                                    mLl_empty2.setVisibility(View.GONE);
                                } else {
                                    mLl_empty2.setVisibility(View.VISIBLE);
                                }
                            }
                        } else {
//                        ToastHelper.showIconAndTextToastCenter(false, data.getMessage());
//                        if (data.getMessage().equals("该微博不存在或已被删除"))
//                            finish();
                        }
                        commentPage = 1;
                        lovesPage = 1;
                    } else
                        ToastHelper.showAlert(ZlxWeiboActivity.this, "请求失败,请重试");
                }else
                    ToastHelper.showAlert(ZlxWeiboActivity.this, "请求失败,请重试");
                mEasylayout.refreshComplete();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 666) {
            getDetailData();
        }
    }
}
