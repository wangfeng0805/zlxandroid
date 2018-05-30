package com.example.ylf019.zlxandroid.home.adapter;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.http.model.PersonSpaceModel;

import java.util.List;

/**
 * Created by yjx on 2018/4/19.
 */

public class ZLXMultiWeiboListAdapter extends BaseMultiItemQuickAdapter<PersonSpaceModel,BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ZLXMultiWeiboListAdapter(List<PersonSpaceModel> data) {
        super(data);
//        addItemType(0, R.layout.item_weibo_list);// 0 微博  1 视频 2 图集 3 资讯
//        addItemType(1, R.layout.item_weibo_list);
//        addItemType(2, R.layout.weibo_item_find_photo);
//        addItemType(3, R.layout.weibo_item_reprint_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, PersonSpaceModel item) {

    }


}
