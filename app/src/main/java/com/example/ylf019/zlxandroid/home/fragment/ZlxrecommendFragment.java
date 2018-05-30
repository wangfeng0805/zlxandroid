package com.example.ylf019.zlxandroid.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseFragment;
import com.example.ylf019.zlxandroid.home.adapter.ZlxrecommendAdapter;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.model.ZlxObjectListModel;
import com.example.ylf019.zlxandroid.utils.ToastHelper;
import com.example.ylf019.zlxandroid.zlxcenter.ZlxChooseActivity;
import com.example.ylf019.zlxandroid.zlxcenter.ZlxHomePageActivity;
import com.example.ylf019.zlxandroid.zlxcenter.ZlxObjectSearchActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZlxrecommendFragment extends BaseFragment {

    @Bind(R.id.recycler)
    RecyclerView      mRecycleView;
    @Bind(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
    //    @Bind(R.id.ll_empty)
//    LinearLayout      mLlEmpty;
    @Bind(R.id.btn_send)
    Button            mBtnSend;
    private int page = 0;
    private ZlxrecommendAdapter mZlxrecommendAdapter;
    private List<ZlxObjectListModel.DataBean> mObjectList = new ArrayList<>();
    private String province = "";
    private String sex = "";
    private String object_age_upper = "";
    private String object_age_low = "";
    private String object_height_upper = "";
    private String object_height_low = "";

    @Override
    protected void initData() {
        setUpViewComponent();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_zlxrecommend;
    }

    private void setUpViewComponent() {
        //todo 设置adapter,请求数据
        mEasylayout.setLoadMoreModel(LoadModel.COMMON_MODEL);
        mRecycleView.setLayoutManager(new GridLayoutManager(mContext, 2, LinearLayoutManager.VERTICAL, false));
        if (mZlxrecommendAdapter == null)
            mZlxrecommendAdapter = new ZlxrecommendAdapter(R.layout.item_find_object, mObjectList,
                    mSharedPreferencesHelper.getDouble("longitude"), mSharedPreferencesHelper.getDouble("latitude"));//todo
//        mZlxrecommendAdapter.openLoadAnimation();
//        mZlxrecommendAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
//        mZlxrecommendAdapter.setNotDoAnimationCount(3);//第一屏不显示动画

        province = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LOGIN_AREA);
        sex = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_OBJECT_SEX);
        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
            @Override
            public void onLoadMore() {
                page++;
                //加载更多
                onDataMore();
            }

            @Override
            public void onRefreshing() {
                //下拉刷新
                page = 0;
                onDataRefresh();
            }
        });
        mRecycleView.setAdapter(mZlxrecommendAdapter);
        onDataRefresh();
    }

    private void onDataRefresh() {
        netHandler.getUserObjectList(province, sex, object_age_upper, object_age_low, object_height_upper, object_height_low,
                page + "", new MyCallBack<ZlxObjectListModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
                mEasylayout.refreshComplete();
                ToastHelper.showAlert(mContext, "网络断开");
            }

            @Override
            public void onSuccess(Call call, ZlxObjectListModel data) {
                mEasylayout.refreshComplete();
                if (data != null) {
                    if (data.getCode() == 0) {
                        if (null != mObjectList && mObjectList.size() > 0)
                            mObjectList.clear();
                        mObjectList.addAll(data.getData());
                    } else {
                        if (null != mObjectList && mObjectList.size() > 0)
                            mObjectList.clear();
                        ToastHelper.showAlert(mContext, "没有数据");
                    }
                } else {
                    if (null != mObjectList && mObjectList.size() > 0)
                        mObjectList.clear();
                    ToastHelper.showAlert(mContext, "没有数据");
                }
                mZlxrecommendAdapter.notifyDataSetChanged();
            }
        });

    }

    private void onDataMore() {
        netHandler.getUserObjectList(province, sex, object_age_upper, object_age_low, object_height_upper, object_height_low,
                page + "", new MyCallBack<ZlxObjectListModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
                mEasylayout.loadMoreComplete();
                ToastHelper.showAlert(mContext, "网络断开");
            }

            @Override
            public void onSuccess(Call call, ZlxObjectListModel data) {
                mEasylayout.loadMoreComplete();
                if (data != null) {
                    if (data.getCode() == 0) {
                        mObjectList.addAll(data.getData());
                    } else {
                        ToastHelper.showAlert(mContext, "没有数据");
                    }
                } else {
                    ToastHelper.showAlert(mContext, "没有数据");
                }
                mZlxrecommendAdapter.notifyDataSetChanged();
            }
        });
    }

    @OnClick({R.id.tv_search, R.id.btn_send})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_search:
                //todo
                startActivity(new Intent(mContext, ZlxObjectSearchActivity.class));
                break;
            case R.id.btn_send:
                startActivityForResult(new Intent(mContext, ZlxChooseActivity.class), 26);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 666) {
            object_height_low = data.getStringExtra("object_height_low");
            object_height_upper = data.getStringExtra("object_height_upper");
            object_age_low = data.getStringExtra("object_age_low");
            object_age_upper = data.getStringExtra("object_age_upper");
            province = data.getStringExtra("province");
            onDataRefresh();
        }
    }

}
