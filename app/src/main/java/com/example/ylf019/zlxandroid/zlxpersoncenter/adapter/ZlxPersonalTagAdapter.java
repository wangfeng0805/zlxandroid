package com.example.ylf019.zlxandroid.zlxpersoncenter.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.http.model.ZlxMyFansModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yjx on 2018/5/14.
 */

public class ZlxPersonalTagAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public List<String> mCheckedData = new ArrayList<>();

    public ZlxPersonalTagAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, final String item) {
        TextView tv_name_tag = helper.getView(R.id.tv_name_tag);
        CheckBox checkBox = helper.getView(R.id.cb_tag);
        tv_name_tag.setText(item);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) {
                    mCheckedData.add(item);
                }else {
                    if(mCheckedData.contains(item)) {
                        mCheckedData.remove(item);
                    }
                }
            }
        });
    }

}
