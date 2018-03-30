/**
 * ****************************************************************************
 * Copyright (C) 2005-2012 UCWEB Corporation. All rights reserved
 * File        : ResourcesUtils.java
 * <p/>
 * Description :
 * <p/>
 * Creation    : 2012.9.9
 * Author      : caisq@ucweb.com
 * History     : Modify, 2012.9.9, Cai Shanqing
 * ****************************************************************************
 **/

package com.ehang.commonutils.ui;

import android.content.Context;
import android.graphics.Color;

import java.io.IOException;
import java.io.InputStream;

public final class ResourcesUtils {
    public static final String mDefaultPath = "theme/default/";
    public static final String mNightPath = "theme/night/";
    public static final String mTransparentPath = "theme/transparent/";

    //主题色皮肤,如果新添加新的主题色皮肤时，请在isBuildinTheme()和isColorTheme()方法中添加此项
    public static final String mPinkPath = "theme/pink/";
    public static final String mBlackPath = "theme/black/";
    public static final String mGreenPath = "theme/green/";
    public static final String mOrangePath = "theme/orange/";

    static final String EN_US = "en-us";
    static final String ZH_CN = "zh-cn";

    static String mCurrentLanguageParentDir = "resources/strings/"; // TODO: 将这个变量移动到AttributeReader？
    public static final String mDefaultLanguageParentPath = "resources/strings/";
    static String mDefaultLanguage = EN_US;

    public static boolean isDebugAble() {
        return false;
    }

    static boolean isDefaultTheme(String themePath) {
        return themePath != null && themePath.equals(mDefaultPath);
    }

    static boolean isNightTheme(String themePath) {
        return themePath != null && themePath.equals(mNightPath);
    }

    public static boolean isBuildinTheme(String themePath) {
        boolean ret = false;
        ret = themePath != null
                && (themePath.equals(mDefaultPath) || themePath.equals(mNightPath) || themePath.equals(mTransparentPath)
                || themePath.equals(mPinkPath) || themePath.equals(mGreenPath) || themePath.equals(mBlackPath) || themePath.equals(mOrangePath));
        return ret;
    }

    public static boolean isColorTheme(String themePath) {
        if (themePath == null)
            return false;
        if (themePath.equals(mDefaultPath) || themePath.equals(mPinkPath) || themePath.equals(mGreenPath) || themePath.equals(mBlackPath) || themePath.equals(mOrangePath)) {
            return true;
        }
        return false;
    }

    public static String[] getColorTheme() {
        String[] themePaths = {mPinkPath, mBlackPath, mOrangePath, mGreenPath};
        return themePaths;
    }

    //24bits, not 32bits
    static String integerToHexColor(int value, boolean is24bitsnot32bits) {
        int alpha = Color.alpha(value);
        int red = Color.red(value);
        int green = Color.green(value);
        int blue = Color.blue(value);
        String color = null;
        if (is24bitsnot32bits) {
            color = "#" + Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue);
        } else {
            color = "#" + Integer.toHexString(alpha) + Integer.toHexString(red) + Integer.toHexString(green) + Integer.toHexString(blue);
        }
        return color;
    }

    static int getAttrsResourceIdByResourceName(String ResName) {
        int resourceId = 0;

        if (ResName.equals("state_focused")) {
            resourceId = android.R.attr.state_focused;
        } else if (ResName.equals("state_enabled")) {
            resourceId = android.R.attr.state_enabled;
        } else if (ResName.equals("state_selected")) {
            resourceId = android.R.attr.state_selected;
        } else if (ResName.equals("state_active")) {
            resourceId = android.R.attr.state_active;
        } else if (ResName.equals("state_active")) {
            resourceId = android.R.attr.state_active;
        } else if (ResName.equals("state_pressed")) {
            resourceId = android.R.attr.state_pressed;
        } else if (ResName.endsWith("state_checked")) {
            resourceId = android.R.attr.state_checked;
        } else if (ResName.endsWith("state_checkable")) {
            resourceId = android.R.attr.state_checkable;
        }

        return resourceId;
    }

    public static float convertSpToPixels(Context context, float sp) {
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return sp * scaledDensity;
    }

    public static float convertDipToPixels(Context context, float dips) {
        return dips * context.getResources().getDisplayMetrics().density + 0.5f;
    }

    public static String getValideString(String s, String defaultValue) {
        return s == null ? defaultValue : s;
    }

    /**
     * @note inputStream will be closed after read data.
     */
    public static byte[] readFile(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        int len = inputStream.available();
        byte[] byteArray = null;
        if (len > 0) {
            byteArray = new byte[len];
            inputStream.read(byteArray);
        }
        return byteArray;
    }

    /**
     * 皮肤相关的返回值
     */
    public static final int THEME_ERROR_NORMAL = 0;   // 验证皮肤有效
    public static final int THEME_ERROR_VERSION = 1;
    public static final int THEME_ERROR_FORMAT = 2;
    public static final int THEME_ERROR_UNKNOWN = 3;
    public static final int THEME_ERROR_EXIST = 4;
    public static final int THEME_ERROR_UPGRADE = 5;


    public static boolean startWidthIgnoreCase(String oriString, String startString) {
        if (oriString == null || startString == null) return false;

        int length = startString.length();
        if (length > oriString.length()) {
            return false;
        }
        if (startString.equalsIgnoreCase(oriString.substring(0, length))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 获取浏览器默认语言
     *
     * @return
     */
    public static String getDefaultLanguage() {
        return mDefaultLanguage;
    }
}
