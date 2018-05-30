package com.example.ylf019.zlxandroid.zlxcenter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.base.BaseActivity;
import com.example.ylf019.zlxandroid.http.model.JsonBean;
import com.example.ylf019.zlxandroid.utils.GetJsonDataUtil;
import com.example.ylf019.zlxandroid.utils.ToastHelper;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ZlxChooseActivity extends BaseActivity {


    @Bind(R.id.rl_toptitlebar_back)
    RelativeLayout mRlToptitlebarBack;
    @Bind(R.id.tv_province)
    TextView       mTvProvince;
    @Bind(R.id.tv_age)
    TextView       mTvAge;
    @Bind(R.id.tv_title)
    TextView       mTvTitle;
    @Bind(R.id.tv_height)
    TextView       mTvHeight;

    private Thread thread;
    //籍贯
    private ArrayList<JsonBean>                     options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>>            options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private List<String> area = new ArrayList<>();

    private List<String>       optionsAge   = new ArrayList<>();
    private List<List<String>> optionsAge2  = new ArrayList<>();
    private List<String>       optionsHigh  = new ArrayList<>();
    private List<List<String>> optionsHigh2 = new ArrayList<>();
    public static String object_age_upper = "";
    public static String object_age_low = "";
    public static String object_height_upper = "";//todo 初始化页面数据,初始化变量
    public static String object_height_low = "";
    public static String province = "";
    public static String province2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zlx_choose);
        ButterKnife.bind(this);
        mTvTitle.setText("条件筛选");
        if (thread == null) {//如果已创建就不再重新创建子线程了
//            Toast.makeText(ZlxPerfectPersonActivity.this, "Begin Parse Data", Toast.LENGTH_SHORT).show();
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    // 子线程中解析省市区数据
                    initJsonData();
                }
            });
            thread.start();
        }
        initData();
        initPickerDatas();
    }

    private void initData() {
        if(!TextUtils.isEmpty(province2)) {
            mTvProvince.setText(province2);
        }

        if(!TextUtils.isEmpty(object_age_low)) {
            mTvAge.setText(object_age_low + "~" + object_age_upper + "岁");
        }

        if(!TextUtils.isEmpty(object_height_low)) {
            mTvHeight.setText(object_height_low + "~" + object_height_upper + "厘米");
        }

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
        for (int i = 130; i < 220; i++) {
            optionsHigh.add(i + "");
            ArrayList<String> list = new ArrayList<>();
            for (int j = i + 1; j < 221; j++) {
                list.add(j + "");
            }
            optionsHigh2.add(list);
        }
    }

    @OnClick({R.id.rl_toptitlebar_back, R.id.ll_item1, R.id.ll_item2, R.id.ll_item3, R.id.tv_save_search_term})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_item1:
                showPickerView1();
                break;
            case R.id.ll_item2:
                //年龄
                showPickerView2();
                break;
            case R.id.ll_item3:
                //身高
                showPickerView3();
                break;
            case R.id.rl_toptitlebar_back:
                finish();
                break;
            case R.id.tv_save_search_term:
                //todo 保存信息
                ToastHelper.showAlert(ZlxChooseActivity.this, "筛选条件设置成功");
                Intent intent = new Intent();
                intent.putExtra("object_height_low", object_height_low);
                intent.putExtra("object_height_upper", object_height_upper);
                intent.putExtra("object_age_low", object_age_low);
                intent.putExtra("object_age_upper", object_age_upper);
                intent.putExtra("province", province);
                setResult(666, intent);
                finish();
                break;
        }
    }

    private void showPickerView2() {
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

    private void showPickerView1() {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() + " " +
                        options2Items.get(options1).get(options2) + "  " +
                        options3Items.get(options1).get(options2).get(options3);
                province = options1Items.get(options1).getPickerViewText();
                province2 = tx;
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

    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        super.onDestroy();
    }

}
