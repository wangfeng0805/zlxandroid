package com.example.ylf019.zlxandroid.login;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.ylf019.zlxandroid.MainApp;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.home.MainActivity;
import com.tencent.mm.opensdk.modelmsg.SendAuth;


public class LoadingActivity extends BaseActivity {

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //控制底部虚拟键盘
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            getWindow().getDecorView().setSystemUiVisibility(
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                            //                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                            | View.SYSTEM_UI_FLAG_IMMERSIVE);
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            //让虚拟键盘一直不显示
            Window window = getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|View.SYSTEM_UI_FLAG_IMMERSIVE;
            window.setAttributes(params);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        startAnimation();
    }

    /**
     * 设置动画并且启动动画
     */
    private void startAnimation() {
        String length = "3";
        if(length != null && !TextUtils.isEmpty(length)) {
            int lengths = Integer.parseInt(length);
            if(lengths < 3) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                        LoadingActivity.this.finish();
                    }
                }, 2000);
            }else {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        startActivity(new Intent(LoadingActivity.this, MainActivity.class));
//                        LoadingActivity.this.finish();
                        String lx_num = mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM, "");
                        if(lx_num == null || TextUtils.isEmpty(lx_num)) {
                            startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                            LoadingActivity.this.finish();
                        }else {
                            startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                            LoadingActivity.this.finish();
                        }
                    }
                }, lengths * 1000);
            }
        }else {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(LoadingActivity.this, LoginActivity.class));
                    LoadingActivity.this.finish();
                }
            }, 2000);
        }

    }

}
