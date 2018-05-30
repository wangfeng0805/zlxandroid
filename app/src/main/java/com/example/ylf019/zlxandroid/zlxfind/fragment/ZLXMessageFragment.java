package com.example.ylf019.zlxandroid.zlxfind.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppContact;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseFragment;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.info.LeaveMsgModel;
import com.example.ylf019.zlxandroid.http.info.WeiboInfo;
import com.example.ylf019.zlxandroid.utils.ToastHelper;
import com.example.ylf019.zlxandroid.zlxfind.adapter.ZLXMessageAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZLXMessageFragment extends BaseFragment {

    @Bind(R.id.recycler)
    RecyclerView mRecycleView;

    private String mLx_num;

    private ZLXMessageAdapter ZLXMessageAdapter;
    private List<LeaveMsgModel.InfoBean> messageData = new ArrayList<>();
    private int                 page          = 1;

    @Override
    protected void initData() {
        mLx_num = getArguments().getString("lx_num");
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        if (ZLXMessageAdapter == null)
            ZLXMessageAdapter = new ZLXMessageAdapter(R.layout.item_message_list, messageData);
        ZLXMessageAdapter.openLoadAnimation();
        ZLXMessageAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        ZLXMessageAdapter.setNotDoAnimationCount(3);//第一屏不显示动画
        ZLXMessageAdapter.setEnableLoadMore(false);//todo
//        ZLXMessageAdapter.onItemViewClick(this);
        mRecycleView.setAdapter(ZLXMessageAdapter);
        ZLXMessageAdapter.bindToRecyclerView(mRecycleView);
        ZLXMessageAdapter.disableLoadMoreIfNotFullPage();
//        ZLXMessageAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
//            @Override
//            public void onLoadMoreRequested() {
//                page++;
//                //加载更多
//                onDataMore();
//            }
//        });
        onDataRefresh();
    }


    private void onDataRefresh() {
        //数据刷新
        page = 1;
        netHandler.getLeaveMsgList(mLx_num, mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), new MyCallBack<LeaveMsgModel>() {

            @Override
            public void onFailure(Call call, IOException e) {
//                ToastHelper.showIconAndTextToastCenter(false,"请求失败,请重试");
            }

            @Override
            public void onSuccess(Call call, LeaveMsgModel data) {
                if(data!=null) {
                    if (null != messageData && messageData.size() > 0)
                        messageData.clear();
                    if(data.getInfo()!= null) {
                        if (data.getInfo().size() > 1) {
                            ZLXMessageAdapter.removeAllHeaderView();
                            messageData.addAll(data.getInfo());
                            ZLXMessageAdapter.notifyDataSetChanged();
                        }else {
                            if(data.getInfo().get(0) == null || data.getInfo().get(0).getContent() == null||
                            TextUtils.isEmpty(data.getInfo().get(0).getContent())) {
                                //todo 设置假数据显示空视图
                                View view = LayoutInflater.from(mContext).inflate(R.layout.head_item_empty_layout, null);
                                TextView tv_content = view.findViewById(R.id.tv_content);
                                ImageView iv_icon = view.findViewById(R.id.iv_icon);
                                iv_icon.setImageResource(R.drawable.empty_message);
                                tv_content.setText("还没有人留过言，快和老乡聊聊天吧");
                                ZLXMessageAdapter.addHeaderView(view);
                            }else {
                                ZLXMessageAdapter.removeAllHeaderView();
                                messageData.addAll(data.getInfo());
                                ZLXMessageAdapter.notifyDataSetChanged();
                            }
                        }
                    }else {
                        View view = LayoutInflater.from(mContext).inflate(R.layout.head_item_empty_layout, null);
                        TextView tv_content = view.findViewById(R.id.tv_content);
                        ImageView iv_icon = view.findViewById(R.id.iv_icon);
                        iv_icon.setImageResource(R.drawable.empty_message);
                        tv_content.setText("还没有人留过言，快和老乡聊聊天吧");
                        ZLXMessageAdapter.addHeaderView(view);
                    }
                }else {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.head_item_empty_layout, null);
                    TextView tv_content = view.findViewById(R.id.tv_content);
                    ImageView iv_icon = view.findViewById(R.id.iv_icon);
                    iv_icon.setImageResource(R.drawable.empty_message);
                    tv_content.setText("还没有人留过言，快和老乡聊聊天吧");
                    ZLXMessageAdapter.addHeaderView(view);
                }
            }
        });

    }

    private void onDataMore() {
        //todo 加载更多
        ZLXMessageAdapter.loadMoreEnd();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_zlxmessage;
    }

    public void singleData(String lx_num) {
        if (lx_num != null && !TextUtils.isEmpty(lx_num)) {
            this.mLx_num = lx_num;
            onDataRefresh();
        }
    }

    public static ZLXMessageFragment newInstance(String lx_num) {
        Bundle args = new Bundle();
        args.putString("lx_num", lx_num);
        ZLXMessageFragment tripFragment = new ZLXMessageFragment();
        tripFragment.setArguments(args);
        return tripFragment;
    }
}
