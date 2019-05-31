package com.ehang.commonutils.system;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import androidx.annotation.RequiresPermission;

import androidx.annotation.RequiresPermission;

import com.ehang.commonutils.ui.TomApplication;

/**
 * 判断网络连接的工具类
 *
 * @author KwokSiuWang
 * @date 2018/4/18
 */
public class NetworkUtil {
    /**
     * 查看网络是否可用。
     *
     * @return 网络是否可用。
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_NETWORK_STATE)
    public static Boolean isNetworkConnected() {
        ConnectivityManager manager = (ConnectivityManager) TomApplication.getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        return networkinfo != null && networkinfo.isAvailable();
    }
}
