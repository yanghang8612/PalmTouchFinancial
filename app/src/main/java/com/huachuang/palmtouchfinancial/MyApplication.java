package com.huachuang.palmtouchfinancial;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Asuka on 2017/2/27.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
    }
}
