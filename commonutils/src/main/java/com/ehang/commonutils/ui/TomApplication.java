package com.ehang.commonutils.ui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;

/**
 * 通用的Application,包含以下功能
 * <p>1.管理、获取当前处于顶层的Activity
 * <p>2.获取应用上下文
 * Created by tom on 2018/3/29.
 */

public class TomApplication extends Application {
    private Activity topActivity;
    private Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        registerTopActivityChangedListener();
    }

    /**
     * 获取全局的应用上下文
     *
     * @return 当前App的上下文
     * @see Application#getApplicationContext()
     */
    public Context getContext() {
        return context;
    }

    /**
     * 获取当前栈顶的Activity，方便使用需要Activity对象的方法，如Toast等
     * <p> 如果当前栈顶Activity销毁，且无其他Activity调用OnResume方法，则获取不到Activity对象，所有使用前必须判空
     *
     * @return 栈顶的Activity，如销毁则返回null
     */
    public Activity getTopActivity() {
        return topActivity;
    }

    private void registerTopActivityChangedListener() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                topActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (topActivity != null && activity.getClass().getSimpleName().equals(topActivity.getClass().getSimpleName())) {
                    topActivity = null;
                }
            }
        });
    }

}
