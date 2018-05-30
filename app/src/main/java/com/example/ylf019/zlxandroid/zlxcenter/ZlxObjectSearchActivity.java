package com.example.ylf019.zlxandroid.zlxcenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.home.adapter.ZLXWeiboListAdapter;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.model.BaseModel;
import com.example.ylf019.zlxandroid.http.model.ZlxObjectListModel;
import com.example.ylf019.zlxandroid.utils.ToastHelper;
import com.example.ylf019.zlxandroid.zlxcenter.adapter.ZlxObjectSearchAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ZlxObjectSearchActivity extends BaseActivity {


    @Bind(R.id.et_search)
    EditText          mEtSearch;
    @Bind(R.id.recycler)
    RecyclerView      mRecycler;
    @Bind(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
//    @Bind(R.id.tv_content)
//    TextView          mTvContent;
//    @Bind(R.id.ll_empty)
//    LinearLayout      mLlEmpty;

    private int page = 0;
    private List<ZlxObjectListModel.DataBean> mObjectList = new ArrayList<>();
    private ZlxObjectSearchAdapter mZlxObjectSearchAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zlx_object_search);
        ButterKnife.bind(this);
        initData();
        initSearchView();
    }

    private void initSearchView() {
        mEtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null && charSequence.length() > 0) {
                    netHandler.searchObjectList(charSequence.toString(), new MyCallBack<ZlxObjectListModel>() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            ToastHelper.showAlert(ZlxObjectSearchActivity.this, "网络断开");
                        }

                        @Override
                        public void onSuccess(Call call, ZlxObjectListModel data) {
                            //todo 数据显示
                            if(data != null) {
                                if(data.getCode() == 0) {
                                    mObjectList.clear();
                                    mObjectList.addAll(data.getData());
                                    mZlxObjectSearchAdapter.notifyDataSetChanged();
//                                    mEasylayout.refreshComplete();
                                }else {
                                    ToastHelper.showAlert(ZlxObjectSearchActivity.this, "没有相关用户");
                                }
                            }else {
                                ToastHelper.showAlert(ZlxObjectSearchActivity.this, "数据获取失败");
                            }
                        }
                    });

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void initData() {
        //        initEmpty();
        mEasylayout.setLoadMoreModel(LoadModel.NONE);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        if (mZlxObjectSearchAdapter == null)
            mZlxObjectSearchAdapter = new ZlxObjectSearchAdapter(R.layout.item_search_object, mObjectList,
                    mSharedPreferencesHelper.getDouble("longitude"), mSharedPreferencesHelper.getDouble("latitude"));
        mZlxObjectSearchAdapter.openLoadAnimation();
        mZlxObjectSearchAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        mZlxObjectSearchAdapter.setNotDoAnimationCount(3);//第一屏不显示动画
//        ZLXWeiboListAdapter.addHeaderView(initHeadView());
//        mZlxObjectSearchAdapter.onItemViewClick(this);
//        mEasylayout.addEasyEvent(new EasyRefreshLayout.EasyEvent() {
//            @Override
//            public void onLoadMore() {
//                page++;
//                //加载更多
//                onDataMore();
//            }
//
//            @Override
//            public void onRefreshing() {
//                //下拉刷新
//                onDataRefresh();
//            }
//        });
        mRecycler.setAdapter(mZlxObjectSearchAdapter);
//        onDataRefresh();
    }

    private void onDataRefresh() {
        mZlxObjectSearchAdapter.notifyDataSetChanged();
        mEasylayout.refreshComplete();
    }

    private void onDataMore() {
        mZlxObjectSearchAdapter.notifyDataSetChanged();
        mEasylayout.loadMoreComplete();
    }

    @OnClick(R.id.rl_toptitlebar_back)
    public void onClick() {
        finish();
    }

}
