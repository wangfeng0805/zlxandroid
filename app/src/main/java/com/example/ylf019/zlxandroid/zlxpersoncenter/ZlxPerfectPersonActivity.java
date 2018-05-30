package com.example.ylf019.zlxandroid.zlxpersoncenter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.model.BaseModel;
import com.example.ylf019.zlxandroid.http.model.JsonBean;
import com.example.ylf019.zlxandroid.http.model.PersonCenterModel;
import com.example.ylf019.zlxandroid.utils.GetJsonDataUtil;
import com.example.ylf019.zlxandroid.utils.StringHelper;
import com.example.ylf019.zlxandroid.utils.ThreadUtil;
import com.example.ylf019.zlxandroid.utils.ToastHelper;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;

/**
 * 完善个人资料
 */
public class ZlxPerfectPersonActivity extends BaseActivity {


    @Bind(R.id.rl_toptitlebar_back)
    RelativeLayout  mRlToptitlebarBack;
    @Bind(R.id.tv_title)
    TextView        mTvTitle;
    @Bind(R.id.ll_item1)
    LinearLayout    mLlItem1;
    @Bind(R.id.tv_province)
    TextView        mTvProvince;
    @Bind(R.id.tv_sex)
    TextView        mTvSex;
    @Bind(R.id.tv_time)
    TextView        mTvTime;
    @Bind(R.id.tv_occupation)
    TextView        mTvOccupation;
    @Bind(R.id.tv_education)
    TextView        mTvEducation;
    @Bind(R.id.tv_save_info)
    TextView        mTvSaveInfo;
    @Bind(R.id.layout_header_image)
    ImageView       mLayoutHeaderImage;
    @Bind(R.id.iv_user_avatar)
    CircleImageView mIvUserAvatar;
    @Bind(R.id.tv_high)
    TextView        mTvHigh;
    @Bind(R.id.tv_personal_tag)
    TextView        mTvPersonalTag;

    //籍贯
    private ArrayList<JsonBean>                     options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>>            options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private List<String> area              = new ArrayList<>();
    //性别
    private List<String> optionsSex        = new ArrayList<>();
    private List<String> optionsOccupation = new ArrayList<>();
    private List<String> optionsEducation  = new ArrayList<>();
    private List<String> optionsHigh       = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zlx_perfect_person);
        ButterKnife.bind(this);
        mTvTitle.setText("个人资料");
        initData();
    }

    private void initInfo() {
        initarray();
        initPickerDatas();
        ThreadUtil.runOnSubThread(new Runnable() {
            @Override
            public void run() {
                // 子线程中解析省市区数据
                initJsonData();
            }
        });
    }

    private void initarray() {
        area.add("北京市");
        area.add("北京市");
        area.add("东城区");
    }

    private void initData() {
        netHandler.getPersonCenterInfo(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), new MyCallBack<PersonCenterModel>() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastHelper.showAlert(ZlxPerfectPersonActivity.this, "网络断开");
                initInfo();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Call call, PersonCenterModel data) {
                if(data != null) {
                    if (data.getError_id() == 0) {
                        Glide.with(ZlxPerfectPersonActivity.this).load(data.getInfo().getHeadurl())
                                .placeholder(R.drawable.img_header).into(mLayoutHeaderImage);
                        Glide.with(ZlxPerfectPersonActivity.this).load(data.getInfo().getHeadurl())
                                .dontAnimate().placeholder(R.drawable.sidebar_default_head_icon).into(mIvUserAvatar);
                        area.clear();
                        area.add(data.getInfo().getProvince());
                        area.add(data.getInfo().getCity());
                        area.add(data.getInfo().getArea());
                        mTvProvince.setText(area.get(0) + " " + area.get(1) + " " + area.get(2));
                        mTvSex.setText(data.getInfo().getSex());
                        mTvTime.setText(getTime(stringToDate(data.getInfo().getBorn_date())));
                        dateTime = data.getInfo().getBorn_date();
                        mTvEducation.setText(data.getInfo().getEducation());
                        mTvOccupation.setText(data.getInfo().getVocation());
                        String height = data.getInfo().getHeight();
                        if(height != null && !TextUtils.isEmpty(height) && !height.equals("0")) {
                            mTvHigh.setText(data.getInfo().getHeight() + "");
                        }else {
                            mTvHigh.setText(" ");
                        }
                        String label = data.getInfo().getLabel();
                        if(label != null && !TextUtils.isEmpty(label) && !label.equals("0")) {
                            mTvPersonalTag.setText(data.getInfo().getLabel() + "");
                        }else {
                            mTvPersonalTag.setText(" ");
                        }

                    } else {
                        ToastHelper.showAlert(ZlxPerfectPersonActivity.this, "网络断开");
                    }
                }
                initInfo();
            }
        });

    }

    private void initPickerDatas() {
        optionsSex.add("男");
        optionsSex.add("女");
        optionsOccupation.add("教师");
        optionsOccupation.add("工人");
        optionsOccupation.add("记者");
        optionsOccupation.add("医生");
        optionsOccupation.add("商人");
        optionsOccupation.add("翻译");
        optionsOccupation.add("服务员");
//        optionsOccupation.add("程序员");
        optionsOccupation.add("其他");
        optionsEducation.add("小学");
        optionsEducation.add("初中");
        optionsEducation.add("高中");
        optionsEducation.add("专科");
        optionsEducation.add("本科");
        optionsEducation.add("硕士");
        optionsEducation.add("博士");
        for (int i = 130; i < 220; i++) {
            optionsHigh.add(i + "厘米");
        }
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    @OnClick({R.id.rl_toptitlebar_back, R.id.ll_item1, R.id.ll_item2, R.id.ll_item3, R.id.ll_item4, R.id.ll_item5, R.id.ll_item6, R.id.ll_item7, R.id.tv_save_info})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_toptitlebar_back:
                finish();
                break;
            case R.id.ll_item1:
                showPickerView();
                break;
            case R.id.ll_item2:
                showPickerView1();
                break;
            case R.id.ll_item3:
                showPickerView2();
                break;
            case R.id.ll_item4:
                showPickerView3();
                break;
            case R.id.ll_item5:
                showPickerView4();
                break;
            case R.id.ll_item6:
                showPickerView5();
                break;
            case R.id.ll_item7:
                Intent intent = new Intent(this, ZlxPersonalTagActivity.class);
                startActivityForResult(intent, 26);
                break;
            case R.id.tv_save_info:
                //todo 保存个人资料
                String height2 = StringHelper.getTextViewContent(mTvHigh).replace("厘米", "");
                netHandler.saveMyInfo(StringHelper.getTextViewContent(mTvSex), area.get(2), area.get(1), area.get(0),
                        area.get(0) + area.get(1) + area.get(2), dateTime, StringHelper.getTextViewContent(mTvEducation),
                        StringHelper.getTextViewContent(mTvOccupation), mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM),
                        height2, StringHelper.getTextViewContent(mTvPersonalTag), new MyCallBack<BaseModel>() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                ToastHelper.showAlert(ZlxPerfectPersonActivity.this, "网络错误");
                            }

                            @Override
                            public void onSuccess(Call call, BaseModel data) {
                                if (data.getError_id() == 0) {
                                    setResult(666);
                                    ToastHelper.showAlert(ZlxPerfectPersonActivity.this, "资料保存成功");
                                    finish();
                                } else {
                                    ToastHelper.showAlert(ZlxPerfectPersonActivity.this, "网络错误");
                                }
                            }
                        });
                break;
        }
    }

    private void showPickerView() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() + " " +
                        options2Items.get(options1).get(options2) + "  " +
                        options3Items.get(options1).get(options2).get(options3);
                mTvProvince.setText(tx);
//                Toast.makeText(ZlxPerfectPersonActivity.this, tx, Toast.LENGTH_SHORT).show();
                area.clear();
                area.add(options1Items.get(options1).getPickerViewText());
                area.add(options2Items.get(options1).get(options2));
                area.add(options3Items.get(options1).get(options2).get(options3));
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    private void showPickerView1() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String sex = optionsSex.get(options1);
                mTvSex.setText(sex);
//                Toast.makeText(ZlxPerfectPersonActivity.this, tx, Toast.LENGTH_SHORT).show();
            }
        })
                .setTitleText("性别")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(optionsSex);//三级选择器
        pvOptions.show();
    }

    private String dateTime = "1990-1-1";

    private void showPickerView2() {
        //时间选择器
        TimePickerView pvTime = new TimePickerBuilder(ZlxPerfectPersonActivity.this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
//                Toast.makeText(ZlxPerfectPersonActivity.this, getTime(date), Toast.LENGTH_SHORT).show();
                mTvTime.setText(getTime(date));
                dateTime = getTime2(date);
            }
        }).build();
        pvTime.show();
    }

    private void showPickerView3() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String occupation = optionsOccupation.get(options1);
                mTvOccupation.setText(occupation);
            }
        })
                .setTitleText("职业")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(optionsOccupation);
        pvOptions.show();
    }

    private void showPickerView4() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String education = optionsEducation.get(options1);
                mTvEducation.setText(education);
            }
        })
                .setTitleText("学历")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(optionsEducation);
        pvOptions.show();
    }

    private void showPickerView5() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String education = optionsHigh.get(options1);
                mTvHigh.setText(education);
            }
        })
                .setTitleText("身高")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        pvOptions.setPicker(optionsHigh);
        pvOptions.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 666) {
            String data_tag = data.getStringExtra("data_tag");
            if(data_tag != null && !TextUtils.isEmpty(data_tag)) {
                mTvPersonalTag.setText(data_tag);
            }
        }
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日");
        return format.format(date);
    }

    private String getTime2(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public Date stringToDate(String str) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
//        try {
//            // Fri Feb 24 00:00:00 CST 2012
//            date = format.parse(str);
//        }catch (ParseException e) {
//            e.printStackTrace();
//        }
        // 2012-02-24
        date = java.sql.Date.valueOf(str);

        return date;
    }

}
