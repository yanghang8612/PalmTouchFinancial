package com.huachuang.palmtouchfinancial.backend.net;

import android.util.Log;

import org.xutils.common.Callback;

/**
 * Created by Asuka on 2017/3/30.
 */

public abstract class NetCallbackAdapter implements Callback.CommonCallback<String> {

    @Override
    public abstract void onSuccess(String result);

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        ex.printStackTrace();
    }

    @Override
    public void onCancelled(CancelledException cex) {
        cex.printStackTrace();
    }

    @Override
    public void onFinished() {

    }
}
