package com.huachuang.palmtouchfinancial.activity;

import android.os.Bundle;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_personal_info)
public class PersonalInfoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
    }
}
