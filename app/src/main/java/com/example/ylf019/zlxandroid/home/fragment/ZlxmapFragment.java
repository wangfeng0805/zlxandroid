package com.example.ylf019.zlxandroid.home.fragment;


import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.Poi;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.example.ylf019.zlxandroid.R;
import com.example.ylf019.zlxandroid.appconfig.AppSpContact;
import com.example.ylf019.zlxandroid.base.BaseFragment;
import com.example.ylf019.zlxandroid.home.MainActivity;
import com.example.ylf019.zlxandroid.http.MyCallBack;
import com.example.ylf019.zlxandroid.http.model.BaseModel;
import com.example.ylf019.zlxandroid.http.model.MarketModel;
import com.example.ylf019.zlxandroid.utils.ToastHelper;
import com.example.ylf019.zlxandroid.zlxfind.MySpaceActivity;
import com.example.ylf019.zlxandroid.zlxfind.OtherSpaceActivity;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * A simple {@link Fragment} subclass.
 */
public class ZlxmapFragment extends BaseFragment implements BaiduMap.OnMarkerClickListener, BDLocationListener, BaiduMap.OnMapClickListener {


    @Bind(R.id.bmapView)
    MapView mBmapView;

    private static final String TAG = "Main";
    private BaiduMap mBaiduMap;
    public LocationClient mLocationClient;


    @Override
    protected void initData() {
        mBaiduMap = mBmapView.getMap();
//        mBaiduMap.setOnMarkerDragListener(this);
        mBaiduMap.setOnMarkerClickListener(this);
        mBaiduMap.setOnMapClickListener(this);
        mLocationClient = new LocationClient(mContext.getApplicationContext());
        mLocationClient.registerLocationListener(this);
        //配置定位参数
        initLocation();
        mLocationClient.start();

        //检查是否拥有定位的权限
//        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)!= PermissionChecker.PERMISSION_GRANTED||ActivityCompat.checkSelfPermission(mContext,Manifest.permission.ACCESS_FINE_LOCATION)!=PermissionChecker.PERMISSION_GRANTED){
//            //无法地位
//            //申请权限
//            ActivityCompat.requestPermissions(((MainActivity) mContext), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},100);
//        }
//        verifyStoragePermissions(((MainActivity) mContext));

        LocationManager locManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
//        if(!locManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
//            // 未打开位置开关，可能导致定位失败或定位不准，提示用户或做相应处理
//            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent);
//        }
        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        initMarket();
    }

    private void initMarket() {
        netHandler.getUserInfo(new MyCallBack<MarketModel>() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onSuccess(Call call, MarketModel data) {
                for (MarketModel.InfoBean infoBean : data.getInfo()) {
                    if(!TextUtils.isEmpty(infoBean.getLongitude())) {
                        addMarker(infoBean);
                    }
                }
            }
        });
    }

    private void addMarker(MarketModel.InfoBean infoBean) {
        //定义Maker坐标点
        LatLng point = new LatLng(Double.parseDouble(infoBean.getLatitude()), Double.parseDouble(infoBean.getLongitude()));
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.drawable.icon_market_focuse);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point).icon(bitmap);
        //在地图上添加Marker，并显示
        mMarker = (Marker) mBaiduMap.addOverlay(option);
        mMarker.setDraggable(true);
        Bundle bundle = new Bundle();
        bundle.putString("nickname",infoBean.getNickname());
        bundle.putString("province",infoBean.getProvince());
        bundle.putString("city",infoBean.getCity());
        bundle.putString("open_id",infoBean.getOpen_id());

        bundle.putString("lx_num",infoBean.getLx_num());
        bundle.putString("vocation",infoBean.getVocation());
        bundle.putString("education",infoBean.getEducation());
        bundle.putString("age",infoBean.getAge()+"");
        bundle.putString("born_date",infoBean.getBorn_date());

        mMarker.setExtraInfo(bundle);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_zlxmap;
    }

    /**
     * @param marker 地图浮点点击事件
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {
//        TextView textView = new TextView(mContext);
//        textView.setBackgroundColor(Color.WHITE);
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_market_user_info, null);
        TextView tv_name = view.findViewById(R.id.tv_name);
        TextView tv_address = view.findViewById(R.id.tv_address);
        TextView tv_vocation = view.findViewById(R.id.tv_vocation);
        TextView tv_education = view.findViewById(R.id.tv_education);
        TextView tv_age = view.findViewById(R.id.tv_age);
        TextView tv_born_date = view.findViewById(R.id.tv_born_date);
        Bundle extraInfo = marker.getExtraInfo();

        String nickname = extraInfo.getString("nickname");
        String province = extraInfo.getString("province");
        String lx_num = extraInfo.getString("lx_num");

        String city = extraInfo.getString("city");
        String open_id = extraInfo.getString("open_id");
        String vocation = extraInfo.getString("vocation");
        String education = extraInfo.getString("education");
        String age = extraInfo.getString("age");
        String born_date = extraInfo.getString("born_date");

        tv_name.setText("  " + nickname);
        tv_address.setText("籍贯:"+province+city);//nickname+"\n"
        tv_vocation.setText("职业:" +vocation);
        tv_education.setText("学历:" +education);
        tv_age.setText("年龄:" +age);
        tv_born_date.setText("生日:" +born_date);

        //添加弹出窗覆盖物
        InfoWindow infoWindow = new InfoWindow(view,marker.getPosition(),0);
        mBaiduMap.showInfoWindow(infoWindow);

        showDialog(lx_num);
        return true;
    }


    @Override
    public void onMapClick(LatLng latLng) {
        mBaiduMap.hideInfoWindow();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        Log.d(TAG, "onMapPoiClick: "+mapPoi.getName()+"/"+mapPoi.getPosition());

//        TextView textView = new TextView(mContext);
//        textView.setBackgroundColor(Color.YELLOW);
//        textView.setText(mapPoi.getName()+"\n"+mapPoi.getPosition());
//
//        //添加弹出窗覆盖物
//        InfoWindow infoWindow = new InfoWindow(textView,mapPoi.getPosition(),0);
//        mBaiduMap.showInfoWindow(infoWindow);


        return true;
    }

    private void initLocation(){
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span=1000;//每5s获取一次位置信息
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

        option.SetIgnoreCacheException(false);
        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);//能够过滤掉假位置
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

        mLocationClient.setLocOption(option);
        Log.d(TAG, "initLocation: ");
    }

    private BDLocation currentLocation;
    private boolean isFirstIn = true;

    @Override
    public void onReceiveLocation(BDLocation location) {
        currentLocation = location;
        Log.d(TAG, "onReceiveLocation: "+location);

        //获取定位结果
        StringBuffer sb = new StringBuffer(256);

        sb.append("time : ");
        sb.append(location.getTime());    //获取定位时间

        sb.append("\nerror code : ");
        sb.append(location.getLocType());    //获取类型类型

        sb.append("\nlatitude : ");
        sb.append(location.getLatitude());    //获取纬度信息

        sb.append("\nlontitude : ");
        sb.append(location.getLongitude());    //获取经度信息

        sb.append("\nradius : ");
        sb.append(location.getRadius());    //获取定位精准度

        if (location.getLocType() == BDLocation.TypeGpsLocation){
            // GPS定位结果
            sb.append("\nspeed : ");
            sb.append(location.getSpeed());    // 单位：公里每小时

            sb.append("\nsatellite : ");
            sb.append(location.getSatelliteNumber());    //获取卫星数

            sb.append("\nheight : ");
            sb.append(location.getAltitude());    //获取海拔高度信息，单位米

            sb.append("\ndirection : ");
            sb.append(location.getDirection());    //获取方向信息，单位度

            sb.append("\naddr : ");
            sb.append(location.getAddrStr());    //获取地址信息

            sb.append("\ndescribe : ");
            sb.append("gps定位成功");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){

            // 网络定位结果
            sb.append("\naddr : ");
            sb.append(location.getAddrStr());    //获取地址信息

            sb.append("\noperationers : ");
            sb.append(location.getOperators());    //获取运营商信息

            sb.append("\ndescribe : ");
            sb.append("网络定位成功");

        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {

            // 离线定位结果
            sb.append("\ndescribe : ");
            sb.append("离线定位成功，离线定位结果也是有效的");

        } else if (location.getLocType() == BDLocation.TypeServerError) {

            sb.append("\ndescribe : ");
            sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");

        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {

            sb.append("\ndescribe : ");
            sb.append("网络不同导致定位失败，请检查网络是否通畅");

        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {

            sb.append("\ndescribe : ");
            sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }

        sb.append("\nlocationdescribe : ");
        sb.append(location.getLocationDescribe());    //位置语义化信息

        List<Poi> list = location.getPoiList();    // POI数据
        if (list != null) {
            sb.append("\npoilist size = : ");
            sb.append(list.size());
            for (Poi p : list) {
                sb.append("\npoi= : ");
                sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }

        Log.i("BaiduLocationApiDem", sb.toString());

        if (isFirstIn){
            MyLocationData locData = new MyLocationData.Builder()
//                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(location.getDirection()).latitude(location.getLatitude()).accuracy(1500)
                    .longitude(location.getLongitude()).build();

            mBaiduMap.setMyLocationData(locData);
            //将地图移动到定位的中心点
            mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLng(new LatLng(location.getLatitude(),location.getLongitude())));
//            addMarker(location.getLatitude(),location.getLongitude());
            isFirstIn = false;
            MapStatusUpdate u = MapStatusUpdateFactory.zoomTo(16);
            mBaiduMap.animateMapStatus(u);
        }

        mSharedPreferencesHelper.putDouble("latitude", location.getLatitude());
        mSharedPreferencesHelper.putDouble("longitude", location.getLongitude());
        netHandler.updateMyLocation(location.getLatitude() + "", location.getLongitude() + "",
                mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM), new MyCallBack<BaseModel>() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        ToastHelper.showAlert(mContext, "网络断开");
                    }

                    @Override
                    public void onSuccess(Call call, BaseModel data) {

                    }
                });
    }

    private Marker mMarker;
    private void addMarker(double left , double right) {
        //定义Maker坐标点
        LatLng point = new LatLng(left, right);
        //构建Marker图标
        BitmapDescriptor bitmap = BitmapDescriptorFactory
                .fromResource(R.mipmap.icon_openmap_focuse_mark);
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point).icon(bitmap);
        //在地图上添加Marker，并显示
        mMarker = (Marker) mBaiduMap.addOverlay(option);
        mMarker.setDraggable(true);
    }

    @Override
    public void onResume() {
        mBmapView.onResume();
        super.onResume();
    }

    @Override
    public void onPause() {
        mBmapView.onPause();
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mBmapView.onDestroy();
        super.onDestroy();
    }

    private void showDialog(final String lx_num) {
        final Dialog dialog = new Dialog(mContext, R.style.dialog_style);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.gravity = Gravity.CENTER;
        window.setAttributes(attributes);
        dialog.setContentView(R.layout.view_enter_room);
        TextView iv_back = (TextView) dialog.findViewById(R.id.text_dialog);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                dialog.dismiss();
               if(lx_num.equals(mSharedPreferencesHelper.getString(AppSpContact.SP_KEY_LX_NUM))) {
                   Intent intent = new Intent(mContext, MySpaceActivity.class);
                   intent.putExtra("lx_num",lx_num);
                   startActivity(intent);
               }else {
                   Intent intent = new Intent(mContext, OtherSpaceActivity.class);
                   intent.putExtra("lx_num",lx_num);
                   startActivity(intent);
               }
                dialog.dismiss();
            }
        });
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode==100){
            if (grantResults[0]==PermissionChecker.PERMISSION_GRANTED){
                Toast.makeText(mContext, "允许通过网络定位", Toast.LENGTH_SHORT).show();
            }else if (grantResults[1]==PermissionChecker.PERMISSION_GRANTED){
                //Toast.makeText(this, "允许高精度定位:开始定位", Toast.LENGTH_SHORT).show();
//                if( getFragment(0).isAdded()) {
                    mLocationClient.start();
//                }
            }else{
                Toast.makeText(mContext, "拒绝定位功能", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private static final int      REQUEST_EXTERNAL_STORAGE = 1;
    private static       String[] PERMISSIONS_STORAGE      = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};


    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
