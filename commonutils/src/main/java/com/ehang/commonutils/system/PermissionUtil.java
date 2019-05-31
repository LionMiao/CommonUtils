package com.ehang.commonutils.system;

import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;

import androidx.core.app.ActivityCompat;

import com.ehang.commonutils.ui.TomApplication;

/**
 * 权限的工具。
 *
 * @author KwokSiuWang
 * @date 2018/4/9
 */

public class PermissionUtil {
    /**
     * 自定义的请求gps时候用的请求码。
     */
    public static final int REQUEST_CODE_GPS = 1;

    /**
     * 开启gps所需要的权限。
     */
    public static String[] PERMISSIONS_GPS = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    /**
     * 检查权限。
     *
     * @param permissions 权限数组。
     * @return 是否拥有此权限。
     */
    public static boolean checkPermission(String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(TomApplication.getContext(), permission)
                    != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
