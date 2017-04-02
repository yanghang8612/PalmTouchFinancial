package com.huachuang.palmtouchfinancial;

import android.app.Application;
import android.content.Context;
import android.graphics.Color;

import com.huachuang.palmtouchfinancial.loader.HeaderImageLoader;
import com.imnjh.imagepicker.PickerConfig;
import com.imnjh.imagepicker.SImagePicker;

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
        SImagePicker.init(new PickerConfig.Builder().setAppContext(this)
                            .setImageLoader(new HeaderImageLoader(this))
                            .setToolbaseColor(Color.BLACK)
                            .build());
    }
}
