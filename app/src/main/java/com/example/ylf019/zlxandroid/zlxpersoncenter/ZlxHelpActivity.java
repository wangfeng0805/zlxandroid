package com.example.ylf019.zlxandroid.zlxpersoncenter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.base.BaseActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZlxHelpActivity extends BaseActivity {

    @Bind(R.id.tv_title)
    TextView mTvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zlx_help);
        ButterKnife.bind(this);
        mTvTitle.setText("帮助");
    }

    @OnClick(R.id.rl_toptitlebar_back)
    public void onClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }
}
