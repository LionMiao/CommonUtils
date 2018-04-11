package com.ehang.commonutils.system;

import android.content.Context;
import android.location.Criteria;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;

import com.ehang.commonutils.debug.Log;
import com.ehang.commonutils.ui.TomApplication;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

/**
 * 定位工具类。
 * <p>
 * 得到LocationHelper的实例后，调用{@link #register(LocationListener)} 或者 {@link #register(LocationListener, Criteria)}方法注册监听器。
 * 不再需要时，调用{@link #unregister()}方法。
 *
 * @author KwokSiuWang
 * @date 2018/4/9
 */

public class LocationHelper implements GpsStatus.Listener, LocationListener {
    private static final String TAG = "LocationHelper";
    private static LocationHelper locationHelper;
    private LocationManager locationManager;
    private LocationListener locationListener;

    public static LocationHelper getInstance() {
        if (null == locationHelper) {
            synchronized (LocationHelper.class) {
                if (null == locationHelper) {
                    locationHelper = new LocationHelper();
                }
            }
        }
        return locationHelper;
    }


    private LocationHelper() {
    }

    /**
     * 注册一个监听器。
     * {@link #register(LocationListener, Criteria criteria)}
     *
     * @param locationListener 对位置变化的监听器。
     */
    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public void register(LocationListener locationListener) {
        //一些默认的配置
        Criteria criteria = new Criteria();
        //获取精准位置
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        //允许产生开销
        criteria.setCostAllowed(true);
        //消耗大的话，获取的频率高
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        //对速度的精确度
        criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);
        //对电量的要求
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        //手机位置移动
        criteria.setSpeedRequired(true);

        register(locationListener, criteria);
    }

    /**
     * 注册一个监听器。每次调用，localManager都会被重新创建一次。
     *
     * @param locationListener 对位置变化的监听器。
     * @param criteria         对gps的一些自定义配置。
     */
    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public void register(LocationListener locationListener, Criteria criteria) {
        //如果之前有人在注册，那就把他逼退。
        if (null != locationManager) {
            unregister();
        }
        Log.d(TAG, "正在开启gps");
        this.locationListener = locationListener;
        //判断有没有对应的权限。
        if (PermissionUtil.checkPermission(PermissionUtil.PERMISSIONS_GPS)) {
            Log.d(TAG, "已获得权限，开启gps");
            locationManager = (LocationManager) TomApplication.getContext().getSystemService(Context.LOCATION_SERVICE);
            if (locationManager != null) {
                locationManager.requestLocationUpdates(locationManager.getBestProvider(criteria, true), 1, 0f, this);
                locationManager.addGpsStatusListener(this);
                locationListener.onLocationRegisterSuccess();
                Log.d(TAG, "开启gps成功");
            } else {
                Log.d(TAG, "开启gps失败");
            }
        } else {
            locationListener.onLocationError("没有所需要的权限");
            Log.d(TAG, "没有所需的权限，开启gps失败");
            unregister();
        }
    }

    /**
     * 取消注册。会把listener和locationManager都同时取消掉。
     */
    @RequiresPermission(anyOf = {ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION})
    public void unregister() {
        if (null != locationListener) {
            locationListener.onLocationFinish();
            locationListener = null;
        }
        if (null != locationManager) {
            locationManager.removeUpdates(this);
            locationManager.removeGpsStatusListener(this);
            locationManager = null;
        }
    }

    @Override
    public void onGpsStatusChanged(int event) {
        Log.d(TAG, "onGpsStatusChanged : " + event);
    }

    @Override
    public void onLocationChanged(Location location) {
        locationListener.onLocationChanged(location);
        Log.d(TAG, "onLocationChanged : " + location.toString());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d(TAG, "onStatusChanged : " + provider);
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d(TAG, "onProviderEnabled : " + provider);
    }

    @Override
    public void onProviderDisabled(String provider) {
        locationListener.onLocationError(provider);
        Log.d(TAG, "onProviderDisabled : " + provider);
    }

    /**
     * 对location变化的监听器。
     */
    public interface LocationListener {
        /**
         * 成功注册是被调用。
         */
        void onLocationRegisterSuccess();

        /**
         * 出现异常时被调用。
         *
         * @param msg 错误信息。
         */
        void onLocationError(String msg);

        /**
         * locationManager不再工作时调用此方法。
         */
        void onLocationFinish();

        /**
         * location 发生变化的时候调用此方法。
         *
         * @param location 新的location。
         */
        void onLocationChanged(Location location);
    }
}
