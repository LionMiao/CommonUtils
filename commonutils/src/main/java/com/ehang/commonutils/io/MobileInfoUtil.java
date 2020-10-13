package com.ehang.commonutils.io;

/**
 * @author tom
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.ehang.commonutils.ui.TomApplication;

/**
 * 获取手机信息工具类
 *
 * @author tom
 */
@SuppressLint("MissingPermission")
public class MobileInfoUtil {
    public static String getUniqueCode() {
        return Settings.System.getString(TomApplication.getContext().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
    }

    /**
     * 获取手机IMEI
     */
    public static String getIMEI() {
        try {
            //实例化TelephonyManager对象
            TelephonyManager telephonyManager = (TelephonyManager) TomApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMEI号
            String imei = telephonyManager.getDeviceId();
            //在次做个验证，也不是什么时候都能获取到的啊
            if (imei == null) {
                imei = "";
            }
            return imei;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * 获取手机IMSI
     */
    public static String getIMSI() {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) TomApplication.getContext().getSystemService(Context.TELEPHONY_SERVICE);
            //获取IMSI号
            String imsi = telephonyManager.getSubscriberId();
            if (null == imsi) {
                imsi = "";
            }
            return imsi;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}