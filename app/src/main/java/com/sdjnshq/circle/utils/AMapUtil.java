package com.sdjnshq.circle.utils;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.sdjnshq.circle.CircleApplication;

public class AMapUtil {
    private static AMapLocationClient mLocationClient;

    public static void init() {
        //声明定位回调监听器
        //初始化定位
        mLocationClient = new AMapLocationClient(CircleApplication.getInstance());

        //声明AMapLocationClientOption对象
        AMapLocationClientOption mLocationOption = null;
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        // 每十分钟定位一次
        mLocationOption.setInterval(1000 * 60 * 60*30);
        mLocationOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mLocationOption);

    }

    public static void startLocation() {
        if (mLocationClient != null) {
            mLocationClient.startLocation();
        }
    }

    public static void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
    }
    //设置定位回调监听
    public static void setLocationListener(AMapLocationListener locationListener) {
        if (mLocationClient != null) {
            mLocationClient.setLocationListener(locationListener);
        }
    }
}
