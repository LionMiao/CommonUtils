package com.ehang.commonutils.system;

import android.content.Context;
import android.os.Vibrator;
import android.support.annotation.RequiresPermission;

import com.ehang.commonutils.ui.TomApplication;

/**
 * 手机震动的工具类
 * Created by KwokSiuWang on 2018/3/30.
 */

public class VibratorUtil {
    private Vibrator vibrator;
    private long[] vibratePattern;
    private int vibrateRepeat;

    public VibratorUtil() {
        this(new long[]{3000, 3000}, 0);
    }

    public VibratorUtil(long[] vibratePattern, int vibrateRepeat) {
        setVibrateParam(vibratePattern, vibrateRepeat);
        vibrator = (Vibrator) TomApplication.getContext().getSystemService(Context.VIBRATOR_SERVICE);
    }

    /**
     * @param vibratePattern 定义你的震动节奏。[多少秒后开始震动，震动多久，多久以后再开始震动，震动多久...]
     * @param vibrateRepeat  重复次数。-1:不再重复，0：无限重复。
     */
    public VibratorUtil setVibrateParam(long[] vibratePattern, int vibrateRepeat) {
        this.vibratePattern = vibratePattern;
        this.vibrateRepeat = vibrateRepeat;
        return this;
    }

    /**
     * 开始震动
     */
    @RequiresPermission("android.permission.VIBRATE")
    public void vibrate() {
        vibrator.vibrate(vibratePattern, vibrateRepeat);
    }

    @RequiresPermission("android.permission.VIBRATE")
    public void stop() {
        if (null != vibrator) {
            vibrator.cancel();
        }
    }

    @RequiresPermission("android.permission.VIBRATE")
    public void release() {
        if (null != vibrator) {
            vibrator.cancel();
            vibrator = null;
        }
    }
}