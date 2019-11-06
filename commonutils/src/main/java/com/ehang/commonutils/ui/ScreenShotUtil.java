package com.ehang.commonutils.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.Window;

/**
 * 屏幕截图的工具类。
 *
 * @author KwokSiuWang
 * @date 2018/5/4
 */
public class ScreenShotUtil {
    private int barHeight = -1;

    /**
     * 获取状态栏高度
     *
     * @return 状态栏高度
     */
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = TomApplication.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = TomApplication.getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 获取屏幕截屏
     *
     * @param activity      需要截图的activity
     * @param containTopBar 是否包含状态栏
     * @return 截图。
     */
    public Bitmap getScreenshot(Activity activity, boolean containTopBar) {
        try {
            Window window = activity.getWindow();
            View view = window.getDecorView();
            view.setDrawingCacheEnabled(true);
            view.buildDrawingCache(true);
            Bitmap bmp1 = view.getDrawingCache();
            //除去状态栏和标题栏
            int height = containTopBar ? 0 : getStatusBarHeight();
            return Bitmap.createBitmap(bmp1, 0, height, bmp1.getWidth(), bmp1.getHeight() - height);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取View截图
     *
     * @param view 需要截图的view。
     * @return 截图。
     */
    public Bitmap getScreenshot(View view) {
        try {
            view.setDrawingCacheEnabled(true);
            Bitmap tBitmap = view.getDrawingCache();
            // 拷贝图片，否则在setDrawingCacheEnabled(false)以后该图片会被释放掉
            tBitmap = Bitmap.createBitmap(tBitmap);
            view.setDrawingCacheEnabled(false);
            return tBitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
