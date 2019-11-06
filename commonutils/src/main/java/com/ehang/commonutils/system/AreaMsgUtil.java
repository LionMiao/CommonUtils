package com.ehang.commonutils.system;

import com.ehang.commonutils.debug.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://int.dpool.sina.com.cn/iplookup/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        AreaMsgService areaMsgService = retrofit.create(AreaMsgService.class);
        areaMsgService.getAreaMsg().enqueue(new retrofit2.Callback<AreaMsg>() {
            @Override
            public void onResponse(retrofit2.Call<AreaMsg> call, retrofit2.Response<AreaMsg> response) {
                Log.d("AreaMsgUtil", "get area msg: " + response.body().toString());
                callback.onSuccess(response.body().country);
            }

            @Override
            public void onFailure(retrofit2.Call<AreaMsg> call, Throwable t) {
                Log.d("AreaMsgUtil", "get area msg: " + t.toString());
                callback.onFail(t.toString());
            }
        });
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

    private interface AreaMsgService {
        @GET("iplookup.php?format=json")
        retrofit2.Call<AreaMsg> getAreaMsg();
    }

    private class AreaMsg {
        private String country;
        private String province;
        private String city;
        private String district;
        private String type;

        @Override
        public String toString() {
            return "地区信息：\n" + "country = " + country + "\nprovince" + province + "\ncity = " + city;
        }
    }
}
