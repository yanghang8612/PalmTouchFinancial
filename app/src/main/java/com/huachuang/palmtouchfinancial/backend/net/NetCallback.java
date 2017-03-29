package com.huachuang.palmtouchfinancial.backend.net;

/**
 * Created by Asuka on 2017/3/29.
 */

public interface NetCallback<T> {

    void onSuccess(T result);

    void onFailure();
}
