package com.example.ylf019.zlxandroid.zlxweibo;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.model.BaseModel;
import com.example.ylf019.zlxandroid.utils.ToastHelper;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class ZlxWriteMessageActivity extends BaseActivity {

    @Bind(R.id.et_content)
    EditText mEtContent;

    private String mLx_num;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zlx_write_message);
        ButterKnife.bind(this);
        mLx_num = getIntent().getStringExtra("lx_num");

    }

    @OnClick({R.id.tv_push, R.id.rl_topbar})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_push:
                netHandler.leaveMessage(mEtContent.getText().toString(), mLx_num, mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM),
                        new MyCallBack<BaseModel>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ToastHelper.showAlert(ZlxWriteMessageActivity.this, "网络断开");
                    }

                    @Override
                    public void onSuccess(Call call, BaseModel data) {
                        if(data != null) {
                            setResult(666);
                            finish();
                        }else {
                            ToastHelper.showAlert(ZlxWriteMessageActivity.this, "数据发布失败");
                        }
                    }
                });
                break;
            case R.id.rl_topbar:
                finish();
                break;
        }
    }

}
