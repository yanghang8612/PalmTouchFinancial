package com.huachuang.palmtouchfinancial;

import android.app.Application;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.xutils.x;

/**
 * Created by Asuka on 2017/2/27.
 */

public class MyApplication extends Application {

    private static String APP_ID = "wxffb25beeebed0544";

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(true);
        IWXAPI api = WXAPIFactory.createWXAPI(this, APP_ID ,true);
        api.registerApp(APP_ID);
    }
}
