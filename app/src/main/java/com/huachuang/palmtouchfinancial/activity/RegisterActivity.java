package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ViewFlipper;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by Asuka on 2017/2/28.
 */

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    @ViewInject(R.id.register_flipper)
    private ViewFlipper registerFlipper;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        registerFlipper.setInAnimation(this, R.anim.push_left_in);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Event(value = R.id.register_next_step_button)
    private void onRegisterNextStepButtonClicked(View view) {
        registerFlipper.setDisplayedChild(1);
    }
}
