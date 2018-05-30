package com.example.ylf019.zlxandroid.zlxfind.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppContact;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseFragment;
import com.example.ylf019.zlxandroid.home.adapter.ZLXWeiboListAdapter;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.info.LeaveMsgModel;
import com.example.ylf019.zlxandroid.http.info.WeiboInfo;
import com.example.ylf019.zlxandroid.http.model.PersonCenterModel;
import com.example.ylf019.zlxandroid.zlxfind.adapter.ZLXMessageAdapter;
import com.example.ylf019.zlxandroid.zlxweibo.ZlxWeiboAddActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZLXPersonBBFragment extends BaseFragment {

    @Bind(R.id.recycler)
    RecyclerView mRecycleView;

    private String              mLx_num;
    private ZLXWeiboListAdapter ZLXWeiboListAdapter;
    private List<WeiboInfo> messageData = new ArrayList<>();
    private int             page        = 1;

    @Override
    protected void initData() {
        mLx_num = getArguments().getString("lx_num");
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        if (ZLXWeiboListAdapter == null)
            ZLXWeiboListAdapter = new ZLXWeiboListAdapter(R.layout.item_weibo_list2, messageData);
        ZLXWeiboListAdapter.openLoadAnimation();
        ZLXWeiboListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        ZLXWeiboListAdapter.setNotDoAnimationCount(3);//第一屏不显示动画
        ZLXWeiboListAdapter.setEnableLoadMore(false);//todo
//        ZLXMessageAdapter.onItemViewClick(this);
        mRecycleView.setAdapter(ZLXWeiboListAdapter);
        ZLXWeiboListAdapter.bindToRecyclerView(mRecycleView);
        ZLXWeiboListAdapter.disableLoadMoreIfNotFullPage();
//        ZLXWeiboListAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                page++;
//                //加载更多
//                onDataMore();
//            }
//        });
        onDataRefresh();
    }

    private View initHeadView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_head_shuoshuo_send, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(mContext, ZlxWeiboAddActivity.class),999);
            }
        });
        return view;
    }

    private void onDataRefresh() {
        netHandler.getPersonSplaceBBList(mLx_num, mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), new MyCallBack<List<WeiboInfo>>() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onSuccess(Call call, List<WeiboInfo> data) {
                if(data!=null) {
                    if (null != messageData && messageData.size() > 0)
                        messageData.clear();
                    ZLXWeiboListAdapter.removeAllHeaderView();
                    if(data.size() > 1) {
//                        data.remove(0);
                        messageData.addAll(data);
                        ZLXWeiboListAdapter.notifyDataSetChanged();
                    }else {
                        //todo 设置假数据显示空视图
                        if(data.get(0) == null || data.get(0).getContent() == null || TextUtils.isEmpty(data.get(0).getContent())) {
                            View view = LayoutInflater.from(mContext).inflate(R.layout.head_item_empty_layout, null);
                            TextView tv_content = view.findViewById(R.id.tv_content);
                            tv_content.setText("还没有发表过说说");
                            ZLXWeiboListAdapter.addHeaderView(view);
                        }else {
                            messageData.addAll(data);
                            ZLXWeiboListAdapter.notifyDataSetChanged();
                        }
                    }
                    if(mLx_num.equals(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM))) {
                        ZLXWeiboListAdapter.removeAllHeaderView();
                        ZLXWeiboListAdapter.addHeaderView(initHeadView());
                    }
                }else {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.head_item_empty_layout, null);
                    TextView tv_content = view.findViewById(R.id.tv_content);
                    tv_content.setText("还没有发表过说说");
                    ZLXWeiboListAdapter.addHeaderView(view);
                }
            }
        });

    }

    private void onDataMore() {
        ZLXWeiboListAdapter.loadMoreEnd();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_zlxperson_bb;
    }


    public void singleData(String open_id) {
        if (open_id != null && !TextUtils.isEmpty(open_id)) {
            this.mLx_num = open_id;
            onDataRefresh();
        }
    }

    public static ZLXPersonBBFragment newInstance(String lx_num) {
        Bundle args = new Bundle();
        args.putString("lx_num", lx_num);
        ZLXPersonBBFragment tripFragment = new ZLXPersonBBFragment();
        tripFragment.setArguments(args);
        return tripFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 666) {
            onDataRefresh();
        }
    }

}
