package com.example.ylf019.zlxandroid.zlxpersoncenter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.model.BaseModel;
import com.example.ylf019.zlxandroid.http.model.ZlxMyFansModel;
import com.example.ylf019.zlxandroid.zlxpersoncenter.adapter.ZlxMyFansAdapter;
import com.example.ylf019.zlxandroid.zlxpersoncenter.adapter.ZlxMyGuanzhuAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ZlxGuanzhuListActivity extends BaseActivity implements ZlxMyGuanzhuAdapter.OnLikeInterface {

    @Bind(R.id.rl_toptitlebar_back)
    RelativeLayout    mRlToptitlebarBack;
    @Bind(R.id.tv_title)
    TextView          mTvTitle;
    @Bind(R.id.rl_topbar)
    RelativeLayout    mRlTopbar;
    @Bind(R.id.recycler)
    RecyclerView      mRecycler;
    @Bind(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    @Bind(R.id.tv_content)
    TextView          mTvContent;
    @Bind(R.id.ll_empty)
    LinearLayout      mLlEmpty;

    private int page = 1;
    private String mLx_num;
    private ZlxMyGuanzhuAdapter ZlxMyFansAdapter;
    private List<ZlxMyFansModel.InfoBean> mRNGDiscoveryModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zlx_zhuanzhu_list);
        ButterKnife.bind(this);
        mLx_num = getIntent().getStringExtra("lx_num");
        if (mLx_num == null || TextUtils.isEmpty(mLx_num)) {
            finish();
            return;
        }
        initData();
    }

    private void initData() {
        mTvTitle.setText("关注的老乡");
        initEmpty();
        mEasylayout.setLoadMoreModel(LoadModel.ADVANCE_MODEL);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        if (ZlxMyFansAdapter == null)
            ZlxMyFansAdapter = new ZlxMyGuanzhuAdapter(R.layout.item_guanzhu_list, mRNGDiscoveryModels);
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                page++;
                onDataMore();
            }

            @Override
            public void onRefreshing() {
                onDataRefresh();
            }
        });
        mRecycler.setAdapter(ZlxMyFansAdapter);
        onDataRefresh();
//        ZlxMyFansAdapter.setOnItemClickListener(this);
        ZlxMyFansAdapter.setOnLikeInterface(this);
    }

    private void initEmpty() {
//        mIvIcon.setImageResource(R.drawable.guanzhu_kong);
//        mTvContent.setText("还没有粉丝关注你, 多发说说才能有人关注你");
//        mIvFashuoshuo.setVisibility(View.VISIBLE);
//        mIvFashuoshuo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(RNGPersonCenterFansActivity.this, RNGWeiboAddActivity.class));
//            }
//        });
        mTvContent.setText("你还没有关注的人，快去关注老乡们吧");
    }

    private void onDataRefresh() {
        netHandler.getMyGuanzhuList(mLx_num, new MyCallBack<ZlxMyFansModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
//                ToastHelper.showIconAndTextToastCenter(false,"请求失败,请重试");
                mRNGDiscoveryModels.clear();
                ZlxMyFansAdapter.notifyDataSetChanged();
                mLlEmpty.setVisibility(View.VISIBLE);
                mRecycler.setVisibility(View.GONE);
                mEasylayout.refreshComplete();
            }

            @Override
            public void onSuccess(Call call, ZlxMyFansModel data) {
                if(data != null) {
                        if(data.getInfo() != null) {
                            mRNGDiscoveryModels.clear();
                            mRNGDiscoveryModels.addAll(data.getInfo());
                            ZlxMyFansAdapter.notifyDataSetChanged();
                            if (data.getInfo().size() == 0) {
                                mLlEmpty.setVisibility(View.VISIBLE);
                                mRecycler.setVisibility(View.GONE);
                            } else {
                                mLlEmpty.setVisibility(View.GONE);
                                mRecycler.setVisibility(View.VISIBLE);
                            }
                        }else {
                            mLlEmpty.setVisibility(View.GONE);
                            mRecycler.setVisibility(View.VISIBLE);
                        }

                }else {
                    mLlEmpty.setVisibility(View.VISIBLE);
                    mRecycler.setVisibility(View.GONE);
                }
                mEasylayout.refreshComplete();
            }
        });
    }

    private void onDataMore() {
        mEasylayout.setLoadMoreModel(LoadModel.NONE);
    }

    @OnClick(R.id.rl_toptitlebar_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @Override
    public void onLikeClick(ZlxMyFansModel.InfoBean item, int position) {

    }

    @Override
    public void onCancelFans(final int position, String id) {
        netHandler.cancelMyfans(id, new MyCallBack<BaseModel>() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onSuccess(Call call, BaseModel data) {
                mRNGDiscoveryModels.remove(position);
                ZlxMyFansAdapter.notifyDataSetChanged();
            }
        });
    }
}
