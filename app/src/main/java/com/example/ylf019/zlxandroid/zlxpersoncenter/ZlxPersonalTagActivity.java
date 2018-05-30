package com.example.ylf019.zlxandroid.zlxpersoncenter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.zlxpersoncenter.adapter.ZlxPersonalTagAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZlxPersonalTagActivity extends BaseActivity {

    @Bind(R.id.recyclerview)
    RecyclerView mRecyclerview;
    @Bind(R.id.tv_save_info)
    TextView     mTvSaveInfo;

    private List<String> mDataList = new ArrayList<>();
    private ZlxPersonalTagAdapter mZlxPersonalTagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zlx_personal_tag);
        ButterKnife.bind(this);
        initListData();
        mZlxPersonalTagAdapter = new ZlxPersonalTagAdapter(R.layout.item_layout_perosnal_tag, mDataList);
        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerview.setAdapter(mZlxPersonalTagAdapter);
    }

    private void initListData() {
        mDataList.add("萌萌哒");
        mDataList.add("女汉子");
        mDataList.add("强迫症");
        mDataList.add("温柔");
        mDataList.add("贤惠");
        mDataList.add("安静");
        mDataList.add("随性");
        mDataList.add("叛逆");
        mDataList.add("理想主义");
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    @OnClick({R.id.rl_toptitlebar_back, R.id.tv_save_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_toptitlebar_back:
                finish();
                break;
            case R.id.tv_save_info:
                Intent intent = new Intent();
                if(mZlxPersonalTagAdapter != null) {
                    StringBuffer buffer = new StringBuffer();
                    for (String checkedDatum : mZlxPersonalTagAdapter.mCheckedData) {
                        buffer.append(checkedDatum +" ");
                    }
                    intent.putExtra("data_tag",buffer.toString());
                }
                setResult(666, intent);
                finish();
                break;
        }
    }
}
