package com.example.ylf019.zlxandroid.zlxcenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.model.BaseModel;
import com.example.ylf019.zlxandroid.http.model.PersonCenterModel;
import com.example.ylf019.zlxandroid.utils.ToastHelper;
import com.example.ylf019.zlxandroid.zlxpersoncenter.ZlxPerfectPersonActivity;
import com.example.ylf019.zlxandroid.zlxpersoncenter.ZlxPersonalTagActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

public class ZlxLoveRequirementActivity extends BaseActivity {


    @Bind(R.id.tv_title)
    TextView        mTvTitle;
    @Bind(R.id.layout_header_image)
    ImageView       mLayoutHeaderImage;
    @Bind(R.id.iv_user_avatar)
    CircleImageView mIvUserAvatar;
    @Bind(R.id.tv_age)
    TextView        mTvAge;
    @Bind(R.id.tv_occupation)
    TextView        mTvOccupation;
    @Bind(R.id.tv_height)
    TextView        mTvHeight;
    @Bind(R.id.tv_personal_tag)
    TextView        mTvPersonalTag;

    private List<String> optionsAge        = new ArrayList<>();
    private List<List<String>> optionsAge2        = new ArrayList<>();
    private List<String> optionsHigh       = new ArrayList<>();
    private List<List<String>> optionsHigh2       = new ArrayList<>();
    private List<String> optionsOccupation = new ArrayList<>();

    private String object_height_upper;//todo 初始化页面数据,初始化变量
    private String object_height_low;
    private String object_age_upper;
    private String object_age_low;
    private String object_occupation;
    private String object_label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zlx_love_requirement);
        ButterKnife.bind(this);
        mTvTitle.setText("心动的她");
        initData();
        initPickerDatas();
    }

    private void initData() {
        netHandler.getPersonCenterInfo(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), new MyCallBack<PersonCenterModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.showAlert(ZlxLoveRequirementActivity.this, "网络断开");
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Call call, PersonCenterModel data) {
                if (data.getError_id() == 0) {
                    //初始化保存参数的数据
                    object_height_upper = data.getInfo().getObject_height_upper();
                    object_height_low = data.getInfo().getObject_height_low();
                    object_age_upper = data.getInfo().getObject_age_upper();
                    object_age_low = data.getInfo().getObject_age_low();
                    object_occupation = data.getInfo().getObject_occupation();
                    object_label = data.getInfo().getObject_label();

                    Glide.with(ZlxLoveRequirementActivity.this).load(data.getInfo().getHeadurl())
                            .placeholder(R.drawable.img_header).into(mLayoutHeaderImage);
                    Glide.with(ZlxLoveRequirementActivity.this).load(data.getInfo().getHeadurl())
                            .dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvUserAvatar);
                    mTvOccupation.setText(data.getInfo().getObject_occupation());
                    String height = data.getInfo().getObject_height_low();
                    if(height != null && !TextUtils.isEmpty(height) && !height.equals("0")) {
                        mTvHeight.setText(data.getInfo().getObject_height_low() + "~" + data.getInfo().getObject_height_upper() + "厘米");
                    }else {
                        mTvHeight.setText("");
                    }
                    String age_low = data.getInfo().getObject_age_low();
                    if(age_low != null && !TextUtils.isEmpty(age_low) && !age_low.equals("0")) {
                        mTvAge.setText(age_low + "~" + data.getInfo().getObject_age_upper() + "岁");
                    }else {
                        mTvAge.setText("");
                    }

                    String label = data.getInfo().getObject_label();
                    if(label != null && !TextUtils.isEmpty(label) && !label.equals("0")) {
                        mTvPersonalTag.setText(data.getInfo().getObject_label() + "");
                    }else {
                        mTvPersonalTag.setText("");
                    }

                } else {
                    ToastHelper.showAlert(ZlxLoveRequirementActivity.this, "网络断开");
                }
            }
        });

    }

    private void initPickerDatas() {
        for (int i = 18; i < 100; i++) {
            optionsAge.add(i + "");
            ArrayList<String> list = new ArrayList<>();
            for (int j = i + 1; j < 101; j++) {
                list.add(j + "");
            }
            optionsAge2.add(list);
        }

        optionsOccupation.add("教师");
        optionsOccupation.add("工人");
        optionsOccupation.add("记者");
        optionsOccupation.add("医生");
        optionsOccupation.add("商人");
        optionsOccupation.add("翻译");
        optionsOccupation.add("服务员");
//        optionsOccupation.add("程序员");
        optionsOccupation.add("其他");
        for (int i = 130; i < 220; i++) {
            optionsHigh.add(i + "");
            ArrayList<String> list = new ArrayList<>();
            for (int j = i + 1; j < 221; j++) {
                list.add(j + "");
            }
            optionsHigh2.add(list);
        }
    }


    @OnClick({R.id.rl_toptitlebar_back, R.id.ll_item1, R.id.ll_item2, R.id.ll_item3, R.id.ll_item4, R.id.tv_save_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_toptitlebar_back:
                finish();
                break;
            case R.id.ll_item1:
                showPickerView1();
                break;
            case R.id.ll_item2:
                showPickerView2();
                break;
            case R.id.ll_item3:
                showPickerView3();
                break;
            case R.id.ll_item4:
                Intent intent = new Intent(this, ZlxPersonalTagActivity.class);
                startActivityForResult(intent, 26);
                break;
            case R.id.tv_save_info:
                //todo 保存资料
                netHandler.updateUserInfo(object_height_upper, object_height_low, object_age_upper, object_age_low,
                        object_occupation, object_label, mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), new MyCallBack<BaseModel>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ToastHelper.showAlert(ZlxLoveRequirementActivity.this, "网络断开");
                    }

                    @Override
                    public void onSuccess(Call call, BaseModel data) {
                        if(data != null && data.getError_id() == 0) {
                            ToastHelper.showAlert(ZlxLoveRequirementActivity.this, "保存成功");
                            finish();
                        }else {
                            ToastHelper.showAlert(ZlxLoveRequirementActivity.this, "网络断开");
                        }
                    }
                });
                break;
        }

    }

    private void showPickerView1() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                object_age_low = optionsAge.get(options1);
                object_age_upper = optionsAge2.get(options1).get(options2);
                mTvAge.setText(object_age_low + "~" + object_age_upper + "岁");

            }
        })
                .setTitleText("年龄")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(optionsAge, optionsAge2);
        pvOptions.show();
    }

    private void showPickerView2() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                object_occupation = optionsOccupation.get(options1);
                mTvOccupation.setText(object_occupation);
            }
        })
                .setTitleText("工作")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(optionsOccupation);
        pvOptions.show();
    }

    private void showPickerView3() {
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                object_height_low = optionsHigh.get(options1);
                object_height_upper = optionsHigh2.get(options1).get(options2);
                mTvHeight.setText(object_height_low + "~" + object_height_upper + "厘米");

            }
        })
                .setTitleText("身高")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(optionsHigh, optionsHigh2);
        pvOptions.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 666) {
            object_label = data.getStringExtra("data_tag");
            if(object_label != null && !TextUtils.isEmpty(object_label)) {
                mTvPersonalTag.setText(object_label);
            }
        }
    }

}
