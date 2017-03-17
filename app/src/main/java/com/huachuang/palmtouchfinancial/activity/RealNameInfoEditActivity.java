package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_real_name_info_edit)
public class RealNameInfoEditActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RealNameInfoEditActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}