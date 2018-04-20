package com.ehang.commonutils.system;

import android.support.annotation.NonNull;

import com.ehang.commonutils.ui.TomApplication;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 获取地区信息的工具类。
 *
 * @author KwokSiuWang
 * @date 2018/4/18
 */
public class AreaMsgUtil {
    /**
     * 获取本设备现在处于的地区。
     *
     * @param callback 回调方法。
     */
    public static void getCountry(final AreaMsgCallback callback) {
        if (callback == null) {
            return;
        }
        new Thread(() -> {
            String url = "http://int.dpool.sina.com.cn/iplookup/iplookup.php?format=json";
            OkHttpClient okHttpClient = new OkHttpClient.Builder()//设置超时，不设置可能会报异常
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .build();
            Request.Builder requestBuilder = new Request.Builder();
            requestBuilder
                    .url(url)
                    .method("GET", null);
            Call call = okHttpClient.newCall(requestBuilder.build());
            okHttpClient.newCall(call.request()).enqueue(new Callback() {
                @Override
                public void onFailure(@NonNull Call call, @NonNull IOException e) {
                    TomApplication.runOnUiThread(() -> callback.onFail(e.toString()));
                }

                @Override
                public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                    try {
                        JSONObject object = new JSONObject(response.body().string());
                        TomApplication.runOnUiThread(() -> {
                            try {
                                callback.onSuccess(object.getString("country"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onFail(e.toString());
                    }
                }
            });
        }).start();
    }

    /**
     * 获取到地区信息。
     */
    public interface AreaMsgCallback {
        /**
         * 成功获取地区信息。
         *
         * @param country 地区名字。
         */
        void onSuccess(String country);

        /**
         * 获取地区信息失败。
         *
         * @param error 失败原因。
         */
        void onFail(String error);
    }
}
