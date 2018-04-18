package com.ehang.commonutils.system;

import android.app.ActivityManager;
import android.content.Context;

import com.ehang.commonutils.ui.TomApplication;

/**
 * @author tom
 */
public class SystemManager {
    /**
     * 判断服务是否正在运行
     *
     * @param serviceName 服务名
     */
    public static boolean isServiceRunning(String serviceName) {
        ActivityManager am = (ActivityManager) TomApplication.getContext().getSystemService(Context.ACTIVITY_SERVICE);
        if (am != null) {
            for (ActivityManager.RunningServiceInfo i : am.getRunningServices(Integer.MAX_VALUE)) {
                if (i.service.getShortClassName().contains(serviceName)) {
                    return true;
                }
            }
        }
        return false;
    }
}
