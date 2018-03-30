package com.ehang.commonutils;

import android.app.Application;
import android.content.Context;

import java.lang.ref.WeakReference;

/**
 * 初始化，获得全局的应用上下文
 * <p>
 * Created by tom on 2018/3/30.
 */

public class CommonUtils {
    private static WeakReference<Context> context;

    /**
     * 初始化，传递全局的应用上下文.
     * 在Application创建时初始化
     *
     * @param context 使用{@link Application#getApplicationContext()}
     */
    public static void init(Context context) {
        CommonUtils.context = new WeakReference<>(context);
    }
}
