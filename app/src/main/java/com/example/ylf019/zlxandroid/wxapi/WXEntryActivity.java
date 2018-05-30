
package com.example.ylf019.zlxandroid.wxapi;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.ylf019.zlxandroid.MainApp;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.appevent.LoginSuccessEvent;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.home.MainActivity;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.info.WeiXinTokenInfo;
import com.example.ylf019.zlxandroid.http.info.WeiboInfo;
import com.example.ylf019.zlxandroid.http.model.WeiXinUserInfoModel;
import com.example.ylf019.zlxandroid.http.model.ZlxLoginModel;
import com.example.ylf019.zlxandroid.http.model.ZlxObjectListModel;
import com.example.ylf019.zlxandroid.login.LoadingActivity;
import com.example.ylf019.zlxandroid.utils.StringHelper;
import com.example.ylf019.zlxandroid.utils.ToastHelper;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.Call;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //如果没回调onResp，八成是这句没有写
        MainApp.api.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
        ToastHelper.showAlert(this, "hahah");
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
// app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
//        LogUtils.sf(resp.errStr);
//        LogUtils.sf("错误码 : " + resp.errCode + "");
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) ToastHelper.showAlert(this, "分享失败");
                else ToastHelper.showAlert(this, "登录失败");
                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN: //拿到了微信返回的code,立马再去请求access_token\
                        String code = ((SendAuth.Resp) resp).code;
//                        ToastHelper.showAlert(this, "code = " + code); //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
//                        System.out.println("code = " + code);
                        netHandler.getAccessToken("wxd408d2c4479e46a4", "494f4a1c885662624117ab69a171ffaf", code, new MyCallBack<WeiXinTokenInfo>() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                ToastHelper.showAlert(WXEntryActivity.this, "网络错误");
                            }

                            @Override
                            public void onSuccess(Call call, final WeiXinTokenInfo data) {
                                Log.e("返回WeiXinTokenInfo数据", new Gson().toJson(data));
                                selectUser(data);
                            }
                        });
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        ToastHelper.showAlert(this, "微信分享成功");
                        finish();
                        break;
                }
                break;
        }
    }

    private void selectUser(final WeiXinTokenInfo data) {
        netHandler.selectUserByUnionid(data.getUnionid(), new MyCallBack<ZlxLoginModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.showAlert(WXEntryActivity.this, "网络错误");
            }

            @Override
            public void onSuccess(Call call, final ZlxLoginModel data2) {
                if(data2 == null || data2.getData() == null || data2.getData().getId() ==null ||
                        data2.getData().getOpen_id() == null || TextUtils.isEmpty(data2.getData().getOpen_id())) {
                    //TODO 微信拉取数据上传
                    getUserInfoUpdate(data,data2);
                }else {
                    setLoginSuccessData(data2);
                    startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
                    EventBus.getDefault().post(new LoginSuccessEvent());
                    WXEntryActivity.this.finish();
                }
            }
        });
    }

    private void getUserInfoUpdate(WeiXinTokenInfo data, final ZlxLoginModel data2) {
        //新用户数据更新
//                                            netHandler.updateMyLocation();

        netHandler.getWeiXinUserInfo(data.getAccess_token(), data.getOpenid(), new MyCallBack<WeiXinUserInfoModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.showAlert(WXEntryActivity.this, "网络错误");
            }

            @Override
            public void onSuccess(Call call, WeiXinUserInfoModel data) {
                if(data != null) {
                    netHandler.newUserInfo(data.getNickname(), data.getSex() + "", data.getProvince(),
                            data.getCity(), data.getOpenid(), data.getHeadimgurl(), data2.getData().getLx_num(),
                            new MyCallBack<ZlxObjectListModel>() {
                                @Override
                                public void onFailure(Call call, IOException e) {
                                    ToastHelper.showAlert(WXEntryActivity.this, "网络错误");
                                }

                                @Override
                                public void onSuccess(Call call, ZlxObjectListModel data) {
                                    if(data != null && data.getCode() == 0) {
                                        setLoginSuccessData(data2);
                                        startActivity(new Intent(WXEntryActivity.this, MainActivity.class));
                                        EventBus.getDefault().post(new LoginSuccessEvent());
                                        WXEntryActivity.this.finish();
                                    }
                                }
                            });
                }
            }
        });
    }

    private void setLoginSuccessData(ZlxLoginModel data) {
        if (StringHelper.notEmpty(data.getData().getOpen_id())) {
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LOGIN_ID,
                    data.getData().getOpen_id());
        } else {
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LOGIN_ID, "");
        }

        if (StringHelper.notEmpty(data.getData().getProvince())) {
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LOGIN_AREA,
                    data.getData().getProvince());
        } else {
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LOGIN_AREA, "");
        }

        if (StringHelper.notEmpty(data.getData().getLx_num())) {
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LX_NUM,
                    data.getData().getLx_num());
        } else {
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_LX_NUM, "");
        }

        if (StringHelper.notEmpty(data.getData().getSex())) {
            if (data.getData().getSex().equals("男")) {
                mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_OBJECT_SEX,
                        "女");
            } else {
                mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_OBJECT_SEX,
                        "男");
            }
        } else {
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_OBJECT_SEX, "");
        }

        if (StringHelper.notEmpty(data.getData().getId())) {
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_USER_ID,
                    data.getData().getId());
        } else {
            mSharedPreferencesHelper.putString(AppSpContact.SP_KEY_USER_ID, "");
        }
    }
}

