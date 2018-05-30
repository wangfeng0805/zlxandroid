package com.example.ylf019.zlxandroid.zlxfind;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.ajguan.library.EasyRefreshLayout;
import com.ajguan.library.LoadModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppContact;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.appevent.WeiBoLikeEvent;
import com.example.ylf019.zlxandroid.base.BaseFragment;
import com.example.ylf019.zlxandroid.base.BaseViewPagerFragment;
import com.example.ylf019.zlxandroid.home.adapter.ZLXWeiboListAdapter;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.info.WeiboInfo;
import com.example.ylf019.zlxandroid.http.model.BaseModel;
import com.example.ylf019.zlxandroid.zlxweibo.ZlxWeiboActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZlxfindFragment extends BaseViewPagerFragment implements com.example.ylf019.zlxandroid.home.adapter.ZLXWeiboListAdapter.onItemViewClick {

    @Bind(R.id.recycler)
    RecyclerView      mRecycleView;
    @Bind(R.id.easylayout)
    EasyRefreshLayout mEasylayout;
//    @Bind(R.id.iv_icon)
//    ImageView         mIvIcon;
//    @Bind(R.id.tv_content)
//    TextView          mTvContent;
//    @Bind(R.id.iv_fashuoshuo)
//    ImageView         mIvFashuoshuo;
    @Bind(R.id.ll_empty)
    LinearLayout      mLlEmpty;

    private ZLXWeiboListAdapter ZLXWeiboListAdapter;
    private List<WeiboInfo> weiboListData = new ArrayList<>();
    private int page = 0;
    private boolean isShow;//当前页面是否显示

    @Override
    protected void initData() {
        EventBus.getDefault().register(this);
        setUpViewComponent();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_zlxfind;
    }

    void setUpViewComponent() {
//        initEmpty();
        mEasylayout.setLoadMoreModel(LoadModel.ADVANCE_MODEL);
        mRecycleView.setLayoutManager(new LinearLayoutManager(mContext));
        if (ZLXWeiboListAdapter == null)
            ZLXWeiboListAdapter = new ZLXWeiboListAdapter(R.layout.item_weibo_list, weiboListData);//todo
        ZLXWeiboListAdapter.openLoadAnimation();
        ZLXWeiboListAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        ZLXWeiboListAdapter.setNotDoAnimationCount(3);//第一屏不显示动画
//        ZLXWeiboListAdapter.addHeaderView(initHeadView());
        ZLXWeiboListAdapter.onItemViewClick(this);
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
                onDataRefresh();
            }
        });
        mRecycleView.setAdapter(ZLXWeiboListAdapter);
        onDataRefresh();
    }

    private void onDataRefresh() {
        page = 0;
        String open_id = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LOGIN_ID);
        netHandler.findUserList(page +"", "10", mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LOGIN_AREA),
                mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), "老乡新动态", new MyCallBack<List<WeiboInfo>>() {
            @Override
            public void onFailure(Call call, IOException e) {
                mEasylayout.refreshComplete();
                Toast.makeText(mContext,"请求失败,请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Call call, List<WeiboInfo> data) {
                mEasylayout.refreshComplete();
                if(data != null &&data.size() > 0) {
                    if (null != weiboListData && weiboListData.size() > 0)
                        weiboListData.clear();
                    weiboListData.addAll(data);
                    ZLXWeiboListAdapter.notifyDataSetChanged();
                    mLlEmpty.setVisibility(View.GONE);
                    mRecycleView.setVisibility(View.VISIBLE);
                }else {
                    mLlEmpty.setVisibility(View.VISIBLE);
                    mRecycleView.setVisibility(View.GONE);
                }

            }
        });
    }

    private void onDataMore() {
        page++;
        netHandler.findUserList(page +"", "5", mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LOGIN_AREA),
                mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), "老乡新动态", new MyCallBack<List<WeiboInfo>>() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(mContext,"请求失败,请重试", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Call call, List<WeiboInfo> data) {
                    mEasylayout.loadMoreComplete();
                    if (data != null && data.size() > 0) {
                        ZLXWeiboListAdapter.addData(data);
                    }
//                        Toast.makeText(mContext,"请求失败,请重试", Toast.LENGTH_SHORT);
            }
        });
    }


    @Override
    public void onLikeClick(final int position, String openId, String id, String dy_user, boolean isLike) {
        //TODO 点赞的回调接口
        if(!isLike) {
            netHandler.requestLoveWeibo(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), dy_user, id, new MyCallBack<BaseModel>() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onSuccess(Call call, BaseModel data) {
                    if(data.getError_id() == 0) {
//                        ZLXWeiboListAdapter.changeItemLikeByPosition(weiBoPosition, event);
//                        onDataRefresh();
                        WeiBoLikeEvent event = new WeiBoLikeEvent(position, true);
                        EventBus.getDefault().post(event);
                    }
                }
            });
        }
    }

    @Override
    public void onLlitemClick(int position, WeiboInfo item) {
        //todo 对应条目的点击事件
        Intent intent = new Intent(mContext, ZlxWeiboActivity.class);
//        intent.putExtra("weiboinfo", item);
        intent.putExtra(AppContact.PARAM_WEIBO_ID, item.getId());
        intent.putExtra(AppContact.PARAM_KEY_POSITION, position);
        startActivityForResult(intent, 22);
    }

//    @Override todo
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == 666) {
//            onDataRefresh();
//        }
//    }

    @Override
    public void onFollowBtnClick(String follow_u_id, final int pos, boolean isFollowed) {
        if(isFollowed) {
//            //取消关注
//            netHandler.cancelMyfans(follow_u_id, new MyCallBack<BaseModel>() {
//                @Override
//                public void onFailure(Call call, IOException e) {
//                    Toast.makeText(mContext,"取消关注失败", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onSuccess(Call call, BaseModel data) {
//                    if(data.getError_id() == 0) {
//                        weiboListData.get(pos).setU_id("");
//                        ZLXWeiboListAdapter.notifyDataSetChanged();
//                        Toast.makeText(mContext,"取消关注成功", Toast.LENGTH_SHORT).show();
//                    }else {
//                        Toast.makeText(mContext,"取消关注失败", Toast.LENGTH_SHORT).show();
//                    }
//                }
//            });
        }else {
            //进行关注
            netHandler.followVillager(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), follow_u_id, new MyCallBack<BaseModel>() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Toast.makeText(mContext,"关注失败", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(Call call, BaseModel data) {
                    if(data.getError_id() == 0) {
//                        weiboListData.get(pos).setU_id("o1-_h5BTGrctcR6Myf7LxbK1bK-g");
//                        ZLXWeiboListAdapter.notifyDataSetChanged();
                        onDataRefresh();
                        Toast.makeText(mContext,"关注成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(mContext,"关注失败", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
            isShow = true;
        else
            isShow = false;
    }

    @Override
    protected void onInitPagerData() {
        if(isShow && ZLXWeiboListAdapter != null) {
            onDataRefresh();
            mRecycleView.scrollToPosition(0);
        }
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLikeEvent(WeiBoLikeEvent event) {
        if (event == null)
            return;
//        if (!isShow) {
//            onDataRefresh();
//        } else
            ZLXWeiboListAdapter.changeItemLikeByPosition(event.id, event);
    }

}
