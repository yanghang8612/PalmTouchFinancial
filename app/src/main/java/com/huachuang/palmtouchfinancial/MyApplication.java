package com.huachuang.palmtouchfinancial;

import android.app.Application;

import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

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
        ZXingLibrary.initDisplayOpinion(this);
        GlobalVariable.api = WXAPIFactory.createWXAPI(this, GlobalParams.WECHAT_APP_ID ,true);
        GlobalVariable.api.registerApp(GlobalParams.WECHAT_APP_ID);
    }
}
