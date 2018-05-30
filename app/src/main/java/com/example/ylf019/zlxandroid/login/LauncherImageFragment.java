package com.example.ylf019.zlxandroid.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ylf019.zlxandroid.MainApp;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseViewPagerFragment;
import com.example.ylf019.zlxandroid.home.MainActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * Author:    ZhuWenWu
 * Version    V1.0
 * Date:      2015/10/22  11:13.
 * Description: 引导页
 * Modification  History:
 * Date         	Author        		Version        	Description
 * -----------------------------------------------------------------------------------
 * 2015/10/22        ZhuWenWu            1.0                    1.0
 * Why & What is modified:
 */
public class LauncherImageFragment extends BaseViewPagerFragment {
    @Bind(R.id.img_index)
    ImageView mIvIcon;
    @Bind(R.id.btn_enter)
    TextView  mIvBtn;
    private int mPosition;

    public LauncherImageFragment() {

    }

    public static LauncherImageFragment newInstance(int position) {
        LauncherImageFragment fragment = new LauncherImageFragment();
        Bundle args = new Bundle();
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void onInitPagerData() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_launcher_image;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPosition = getArguments().getInt("position", 0);
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpViewComponent();
    }

    @Override
    protected void initData() {

    }

    private void setUpViewComponent() {
        switch (mPosition) {
            case 2:
                mIvIcon.setImageResource(R.drawable.app_index_page_2);
                mIvBtn.setVisibility(View.GONE);
                break;
            case 3:
                mIvIcon.setImageResource(R.drawable.app_index_page_3);
                mIvBtn.setVisibility(View.GONE);
                break;
            case 4:
                mIvIcon.setImageResource(R.drawable.app_index_page_4);
                mIvBtn.setVisibility(View.VISIBLE);
                break;
            default:
                mIvIcon.setImageResource(R.drawable.app_index_page_1);
                mIvBtn.setVisibility(View.GONE);
                break;
        }
    }

    @OnClick(R.id.btn_enter)
    void onHomeScreen() {
        if (mPosition == 4) {
            String open_id = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LOGIN_ID, "");
            if(open_id == null || TextUtils.isEmpty(open_id)) {
                wxLogin();
            }else {
                startMainActivity();
            }
        }
    }

    private void wxLogin() {
        if (!MainApp.api.isWXAppInstalled()) {//"您还未安装微信客户端"
            Toast.makeText(mContext, "您还未安装微信客户端",Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
//            LoadingActivity.this.finish();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        MainApp.api.sendReq(req);
        getActivity().finish();
//            startLoginActivity();
    }

    private void startMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
//        intent.putExtra(AppParamContact.PARAM_KEY_IS_HOME_LOGIN, true);
        startActivity(intent);
        getActivity().finish();
    }

}
