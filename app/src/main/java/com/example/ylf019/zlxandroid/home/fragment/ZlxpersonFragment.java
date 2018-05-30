package com.example.ylf019.zlxandroid.home.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseFragment;
import com.example.ylf019.zlxandroid.home.MainActivity;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.model.PersonCenterModel;
import com.example.ylf019.zlxandroid.login.LoginActivity;
import com.example.ylf019.zlxandroid.view.ParallaxScollListView;
import com.example.ylf019.zlxandroid.zlxcenter.ZlxLoveRequirementActivity;
import com.example.ylf019.zlxandroid.zlxfind.MySpaceActivity;
import com.example.ylf019.zlxandroid.zlxfind.OtherSpaceActivity;
import com.example.ylf019.zlxandroid.zlxpersoncenter.ZlxHelpActivity;
import com.example.ylf019.zlxandroid.zlxpersoncenter.ZlxMyFansListActivity;
import com.example.ylf019.zlxandroid.zlxpersoncenter.ZlxPerfectPersonActivity;
import com.example.ylf019.zlxandroid.zlxpersoncenter.ZlxGuanzhuListActivity;
import com.example.ylf019.zlxandroid.zlxpersoncenter.adapter.ZlxPersoncenterAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZlxpersonFragment extends BaseFragment {

    @Bind(R.id.layout_listview)
    ParallaxScollListView mListView;
    private ImageView             mImageView;
    private List<PersonCenterModel.InfoBean> mList = new ArrayList<>();
    private ZlxPersoncenterAdapter mZlxPersoncenterAdapter;
    private CircleImageView mIv_user_avatar;

    @Override
    protected void initData() {
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
//                android.R.layout.simple_expandable_list_item_1,
//                new String[]{
//                        "First Item",
//                        "Second Item",
//                        "Third Item",
//                        "Fifth Item",
//                        "Sixth Item",
//                        "Seventh Item",
//                        "Eighth Item",
//                        "Ninth Item",
//                        "Tenth Item",
//                        "....."
//                }
//        );
//        mListView.setAdapter(adapter);
        View header = LayoutInflater.from(mContext).inflate(R.layout.listview_header, null);
        mImageView = (ImageView) header.findViewById(R.id.layout_header_image);
        mIv_user_avatar = header.findViewById(R.id.iv_user_avatar);

        mListView.setZoomRatio(ParallaxScollListView.ZOOM_X2);
        mListView.setParallaxImageView(mImageView);
        mListView.addHeaderView(header);
        mZlxPersoncenterAdapter = new ZlxPersoncenterAdapter(mContext, mList);
        mListView.setAdapter(mZlxPersoncenterAdapter);
        mZlxPersoncenterAdapter.onItemViewClick(new ZlxPersoncenterAdapter.onItemViewClick() {
            @Override
            public void onStartClick(int position) {
                switch(position) {
                    case 0:
                        Intent intent0 = new Intent(mContext, ZlxPerfectPersonActivity.class);
                        startActivityForResult(intent0, 16);
                        break;
                    case 1:
                        //todo 心动的她
                        startActivity(new Intent(mContext, ZlxLoveRequirementActivity.class));

                        break;
                    case 2:
                        Intent intent = new Intent(mContext, MySpaceActivity.class);
                        intent.putExtra("lx_num", mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM));
                        mContext.startActivity(intent);
                        break;
                    case 3:
                        Intent intent1 = new Intent(mContext, ZlxMyFansListActivity.class);
                        intent1.putExtra("lx_num", mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM));
                        mContext.startActivity(intent1);
                        break;
                    case 4:
                        Intent intent2 = new Intent(mContext, ZlxGuanzhuListActivity.class);
                        intent2.putExtra("lx_num", mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM));
                        mContext.startActivity(intent2);
                        break;
                    case 5:
                        startActivity(new Intent(mContext, ZlxHelpActivity.class));
                        break;
                    case 7:
                        //todo 退出登录
                        loginOutUser();
                        break;
                }
            }
        });
        refreshData();
    }

    private void loginOutUser() {
        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LOGIN_ID, "");
        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LOGIN_AREA, "");
        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LX_NUM, "");
        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_OBJECT_SEX, "");
        mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_USER_ID, "");
        startActivity(new Intent(mContext, LoginActivity.class));
        ((MainActivity) mContext).finish();
    }

    private void refreshData() {
        netHandler.getPersonCenterInfo(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), new MyCallBack<PersonCenterModel>() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onSuccess(Call call, PersonCenterModel data) {
               if(data != null) {
                   mList.clear();
                   for (int i = 0 ;i < 8;i++) {
                       mList.add(data.getInfo());
                   }
                   Glide.with(mContext).load(data.getInfo().getHeadurl()).into(mImageView);
                   Glide.with(mContext).load(data.getInfo().getHeadurl()).dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIv_user_avatar);
                   mZlxPersoncenterAdapter.notifyDataSetChanged();
               }
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 666) {
            refreshData();
        }
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_zlxperson;
    }

}
