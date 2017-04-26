package com.huachuang.palmtouchfinancial.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_setting)
public class SettingActivity extends BaseSwipeActivity {

    private static final String TAG = SettingActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, SettingActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.setting_toolbar)
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
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

    @Event(R.id.setting_share_app)
    private void shareAppClicked(View view) {
        ((MainActivity) getParent()).switchFragment(1);
        finish();
    }

    @Event(R.id.setting_check_update)
    private void checkUpdateClicked(View view) {
        showToast("已是最新版本");
    }

    @Event(R.id.setting_user_agreement)
    private void userAgreementClicked(View view) {
        UserAgreementActivity.actionStart(this);
    }

    @Event(R.id.setting_about_us)
    private void aboutUsClicked(View view) {
        AboutUsActivity.actionStart(this);
    }

    @Event(R.id.setting_hotline)
    private void hotlineClicked(View view) {
        new MaterialDialog.Builder(this)
                .content("客服电话 400-810-9910")
                .contentColorRes(R.color.black)
                .positiveText("呼叫")
                .negativeText("取消")
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "400 810 9910"));
                        if (ActivityCompat.checkSelfPermission(SettingActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        startActivity(intent);
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
