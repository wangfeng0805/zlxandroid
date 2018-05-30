package com.example.ylf019.zlxandroid.utils;

/**
 * Created by yjx on 2018/5/15.
 */

/**
 * 根据两个位置的经纬度，来计算两地的距离（单位为KM）
 * 参数为double类型
 *  long1 位置1经度
 *  lat1  位置1纬度
 *  long2 位置2经度
 *  lat2  位置2纬度
 */
public class Distance {

//    private static final double EARTH_RADIUS = 6378.137;
//
//    private static double rad(double d){
//        return d * Math.PI / 180.0;
//    }
//
//    public static double GetDistance(double long1, double lat1, double long2, double lat2) {
//        double a, b, d, sa2, sb2;
//        lat1 = rad(lat1);
//        lat2 = rad(lat2);
//        a = lat1 - lat2;
//        b = rad(long1 - long2);
//
//        sa2 = Math.sin(a / 2.0);
//        sb2 = Math.sin(b / 2.0);
//        d = 2   * EARTH_RADIUS
//                * Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
//                * Math.cos(lat2) * sb2 * sb2));
//        return d;
//    }
//
//    public static void main(String[] args) {
//        //根据两点间的经纬度计算距离，单位：km
//        System.out.println(GetDistance(114.21221, 22.68301, 114.21229, 22.68309)*1000);
//    }

    private static final double EARTH_RADIUS = 6378.137;
    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }
    /**
     * 依据两点间经纬度坐标（double值），计算两点间距离，
     *
     * @param lat1
     * @param lng1
     * @param lat2
     * @param lng2
     * @return 距离：单位为公里
     */
    public static double DistanceOfTwoPoints(double lat1,double lng1,
                                             double lat2,double lng2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
//        Log.i("距离",s+"");
        return s;
    }
}
