package com.example.ylf019.zlxandroid.login;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.ylf019.zlxandroid.MainApp;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appevent.LoginSuccessEvent;
import com.example.ylf019.zlxandroid.appevent.WeiBoLikeEvent;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.home.adapter.ZLXWeiboListAdapter;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends BaseActivity {

//    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.tv_login_btn)
    public void onClick() {
        wxLogin();
    }

    public void wxLogin() {
        if (!MainApp.api.isWXAppInstalled()) {//"您还未安装微信客户端"
            Toast.makeText(this, "您还未安装微信客户端",Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
//            LoadingActivity.this.finish();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        MainApp.api.sendReq(req);
//        startActivity(new Intent(LoadingActivity.this, MainActivity.class));
//        mHandler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                LoginActivity.this.finish();
//            }
//        }, 600);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginSuccess(LoginSuccessEvent event) {
        LoginActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

}
