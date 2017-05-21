package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_register_vip)
public class RegisterVIPActivity extends BaseSwipeActivity {

    private static final String TAG = RegisterVIPActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
