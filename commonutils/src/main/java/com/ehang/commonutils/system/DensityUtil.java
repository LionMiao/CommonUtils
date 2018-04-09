package com.ehang.commonutils.system;

import android.util.TypedValue;

import com.ehang.commonutils.ui.TomApplication;


/**
 * DP，PX和SP之间的转换工具类。
 *
 * @author : Tom
 * @data : 2014年9月1日　　下午2:49:03
 **/
public class DensityUtil {

    public static int getWidth() {
        return TomApplication.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    public static int getHeight() {
        return TomApplication.getContext().getResources().getDisplayMetrics().heightPixels;
    }

    public static int getDpi() {
        return TomApplication.getContext().getResources().getDisplayMetrics().densityDpi;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = TomApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    public static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, TomApplication.getContext().getResources().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = TomApplication.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     */
    public static int px2sp(float pxValue) {
        final float fontScale = TomApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     */
    public static int sp2px(float spValue) {
        final float fontScale = TomApplication.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}
