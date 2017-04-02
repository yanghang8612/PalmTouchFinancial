package com.huachuang.palmtouchfinancial.backend.net;

import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * Created by Asuka on 2017/2/28.
 */

public class NetUtils {

    private static String TAG = NetUtils.class.getSimpleName();

    public static void getJsonFromUrl(RequestParams params,
                                      HttpMethod method,
                                      final NetCallback<String> callback){
        x.http().request(method, params, new Callback.ProgressCallback<String>() {
            @Override
            public void onWaiting() {
                Log.d(TAG, "waiting");
            }

            @Override
            public void onStarted() {
                Log.d(TAG, "started");
            }

            @Override
            public void onLoading(long total, long current, boolean isDownloading) {
                Log.d(TAG, "loading");
            }

            @Override
            public void onSuccess(String result) {
                Log.d(TAG, "success" + result);
                callback.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Log.d(TAG, "error");
                callback.onFailure();
            }

            @Override
            public void onCancelled(CancelledException cex) {
                Log.d(TAG, "cancelled");
            }

            @Override
            public void onFinished() {
                Log.d(TAG, "finished");
            }
        });
    }
}
