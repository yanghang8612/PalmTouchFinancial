package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_register_vip)
public class RegisterVIPActivity extends BaseSwipeActivity {

    private static final String TAG = RegisterVIPActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterVIPActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.register_vip_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.register_vip_user_phone_number)
    private TextView userPhoneNumberView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        userPhoneNumberView.setText(UserManager.getUserPhoneNumber());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Event(R.id.buy_vip_button)
    private void bugVipButtonClicked(View view) {
        if (!UserManager.getCurrentUser().getDebitCardState()) {
            new MaterialDialog.Builder(this)
                    .content("申请前请先完善结算卡信息！")
                    .contentColorRes(R.color.black)
                    .positiveText("确认")
                    .autoDismiss(false)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
        else {
            UploadLicenseActivity.actionStart(this);
        }
    }
}
