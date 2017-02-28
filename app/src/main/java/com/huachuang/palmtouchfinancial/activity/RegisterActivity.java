package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;

/**
 * Created by Asuka on 2017/2/28.
 */

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }
}
