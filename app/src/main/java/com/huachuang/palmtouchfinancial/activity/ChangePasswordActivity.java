package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.ChangePasswordParams;
import com.huachuang.palmtouchfinancial.util.ActivityCollector;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_change_password)
public class ChangePasswordActivity extends BaseSwipeActivity {

    private static String TAG = ChangePasswordActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ChangePasswordActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.change_password_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.old_password_layout)
    private TextInputLayout oldPasswordLayout;

    @ViewInject(R.id.new_password_layout)
    private TextInputLayout newPasswordLayout;

    @ViewInject(R.id.confirm_new_password_layout)
    private TextInputLayout confirmNewPasswordLayout;

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

    @Event(R.id.change_password_button)
    private void changePasswordButtonClicked(View view) {
        String oldPassword = oldPasswordLayout.getEditText().getText().toString();
        if (TextUtils.isEmpty(oldPassword)) {
            oldPasswordLayout.setError("请输入旧密码");
            return;
        }
        else if (!oldPassword.equals(UserManager.getCurrentUser().getUserPassword())) {
            oldPasswordLayout.setError("旧密码错误，请检查后重试");
            return;
        }
        else {
            oldPasswordLayout.setErrorEnabled(false);
        }

        final String newPassword = newPasswordLayout.getEditText().getText().toString();
        if (TextUtils.isEmpty(newPassword)) {
            newPasswordLayout.setError("请输入新密码");
            return;
        }
        else if (!CommonUtils.validatePassword(newPassword)) {
            newPasswordLayout.setError("密码至少包含6位字符");
            return;
        }
        else {
            newPasswordLayout.setErrorEnabled(false);
        }

        String confirmNewPassword = confirmNewPasswordLayout.getEditText().getText().toString();
        if (TextUtils.isEmpty(confirmNewPassword)) {
            confirmNewPasswordLayout.setError("请输入确认新密码");
            return;
        }
        else if (!confirmNewPassword.equals(newPassword)) {
            confirmNewPasswordLayout.setError("两次输入密码不一致");
            return;
        }
        else {
            confirmNewPasswordLayout.setErrorEnabled(false);
        }

        x.http().post(new ChangePasswordParams(newPassword), new NetCallbackAdapter(this, true) {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject resultJsonObject = new JSONObject(result);
                    if (resultJsonObject.getBoolean("Status")) {
                        showToast("修改成功，请重新登录");
                        ActivityCollector.backToLogin();
                    }
                    else {
                        showToast(resultJsonObject.getString("Info"));

                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
