package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ViewFlipper;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import cn.pedant.SweetAlert.SweetAlertDialog;

@ContentView(R.layout.activity_real_name_info)
public class RealNameInfoActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RealNameInfoActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.real_name_info_flipper)
    private ViewFlipper realNameInfoFlipper;

    @ViewInject(R.id.real_name_info_name_edit)
    private EditText nameEdit;

    private short currentFlipper = 0;

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
            if (currentFlipper == 1) {
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("确认不保存退出吗?")
                        .setCancelText("取消")
                        .setConfirmText("确认")
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                finish();
                            }
                        })
                        .show();
            }
            else {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Event(R.id.real_name_info_button)
    private void realNameInfoButtonClicked(View view) {
        if (currentFlipper == 0) {
            realNameInfoFlipper.setInAnimation(this, R.anim.push_left_in);
            realNameInfoFlipper.setDisplayedChild(1);
            ((Button) view).setText("保存认证信息");
            currentFlipper = 1;
        }
        else {
            realNameInfoFlipper.setInAnimation(this, R.anim.push_right_in);
            realNameInfoFlipper.setDisplayedChild(0);
            ((Button) view).setText("修改认证信息");
            currentFlipper = 0;
        }
    }
}
