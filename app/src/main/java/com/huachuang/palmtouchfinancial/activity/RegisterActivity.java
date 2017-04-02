package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.GetVerificationCodeParams;
import com.huachuang.palmtouchfinancial.backend.net.params.RegisterParams;
import com.huachuang.palmtouchfinancial.backend.net.params.VerifyInvitationCodeParams;
import com.huachuang.palmtouchfinancial.backend.net.params.VerifyPhoneNumberParams;
import com.huachuang.palmtouchfinancial.backend.net.params.VerifyRecommenderIDParams;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by Asuka on 2017/2/28.
 */

@ContentView(R.layout.activity_register)
public class RegisterActivity extends BaseActivity {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    private String generatedVerificationCode = "";
    private boolean invitationCodeCheckState = false;
    private boolean recommenderIDCheckState = false;

    @ViewInject(R.id.register_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.register_flipper)
    private ViewFlipper registerFlipper;

    @ViewInject(R.id.register_phone_number_layout)
    private TextInputLayout phoneNumberLayout;

    @ViewInject(R.id.register_verification_code_layout)
    private TextInputLayout verificationCodeLayout;

    @ViewInject(R.id.register_invitation_code_layout)
    private TextInputLayout invitationCodeLayout;

    @ViewInject(R.id.register_recommender_id_layout)
    private TextInputLayout recommenderIDLayout;

    @ViewInject(R.id.register_agree_check_box)
    private CheckBox agreeCheckBox;

    @ViewInject(R.id.register_get_verification_code_button)
    private Button getVerificationCodeButton;

    @ViewInject(R.id.register_countdown_button)
    private Button countdownButton;

    @ViewInject(R.id.register_password_layout)
    private TextInputLayout passwordLayout;

    @ViewInject(R.id.register_confirm_password_layout)
    private TextInputLayout confirmPasswordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        registerFlipper.setInAnimation(this, R.anim.push_left_in);
        countdownButton.setEnabled(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (registerFlipper.getDisplayedChild() == 1) {
                new MaterialDialog.Builder(this)
                        .content("确认取消注册吗?")
                        .contentColorRes(R.color.black)
                        .positiveText("确认")
                        .negativeText("取消")
                        .autoDismiss(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                finish();
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
            else {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Event(value = R.id.register_get_verification_code_button)
    private void registerGetVerificationCodeButtonClicked(View view) {
        hideKeyboard();
        final String phoneNumber = phoneNumberLayout.getEditText().getText().toString();
        if (phoneNumber.equals("")) {
            phoneNumberLayout.setError("请输入手机号");
        }
        else if (!CommonUtils.validatePhone(phoneNumber)) {
            phoneNumberLayout.setError("请输入正确的手机号");
        }
        else {
            phoneNumberLayout.setErrorEnabled(false);
            x.http().post(new VerifyPhoneNumberParams(phoneNumber), new NetCallbackAdapter() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject resultJSONObject = new JSONObject(result);
                        if (resultJSONObject.getBoolean("Status")) {
                            Log.d(TAG, resultJSONObject.getString("Info"));
                            generatedVerificationCode = CommonUtils.generateVerificationCode();
                            x.http().post(new GetVerificationCodeParams(phoneNumber, generatedVerificationCode), new NetCallbackAdapter() {
                                @Override
                                public void onSuccess(String result) {
                                    Log.d(TAG, "Xutils Net Callback" + result);
                                    timer.start();
                                }
                            });
                        }
                        else {
                            Log.d(TAG, resultJSONObject.getString("Info"));
                            Toast.makeText(RegisterActivity.this, resultJSONObject.getString("Info"), Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    @Event(value = R.id.register_next_step_button)
    private void registerNextStepButtonClicked(View view) {
        hideKeyboard();
        String phoneNumber = phoneNumberLayout.getEditText().getText().toString();
        if (phoneNumber.equals("")) {
            phoneNumberLayout.setError("请输入手机号");
            return;
        }
        else if (!CommonUtils.validatePhone(phoneNumber)) {
            phoneNumberLayout.setError("请输入正确的手机号");
            return;
        }
        else {
            phoneNumberLayout.setErrorEnabled(false);
        }
        String userVerificationCode = verificationCodeLayout.getEditText().getText().toString();
        if (userVerificationCode.equals("")) {
            verificationCodeLayout.setError("请输入验证码");
            return;
        }
        else if (!userVerificationCode.equals(generatedVerificationCode)) {
            verificationCodeLayout.setError("请输入正确的验证码");
            return;
        }
        else {
            verificationCodeLayout.setErrorEnabled(false);
        }
        final String invitationCode = invitationCodeLayout.getEditText().getText().toString();
        final String recommenderID = recommenderIDLayout.getEditText().getText().toString();
        if (invitationCode.equals("")) {
            invitationCodeLayout.setError("请输入邀请码");
            return;
        }
        else {
            invitationCodeLayout.setErrorEnabled(false);
        }
        x.http().post(new VerifyInvitationCodeParams(invitationCode), new NetCallbackAdapter() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject resultJSONObject = new JSONObject(result);
                    if (resultJSONObject.getBoolean("Status")) {
                        Log.d(TAG, resultJSONObject.getString("Info"));
                        invitationCodeCheckState = true;
                        invitationCodeLayout.setErrorEnabled(false);
                    }
                    else {
                        Log.d(TAG, resultJSONObject.getString("Info"));
                        invitationCodeCheckState = false;
                        invitationCodeLayout.setError(resultJSONObject.getString("Info"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFinished() {
                if (!agreeCheckBox.isChecked()) {
                    Toast.makeText(RegisterActivity.this, "请阅读并同意《用户协议》", Toast.LENGTH_SHORT).show();
                }
                else {
                    registerFlipper.setDisplayedChild(1);
                }
            }
        });
        if (!recommenderID.equals("")) {
            x.http().post(new VerifyRecommenderIDParams(recommenderID), new NetCallbackAdapter() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject resultJSONObject = new JSONObject(result);
                        if (resultJSONObject.getBoolean("Status")) {
                            Log.d(TAG, resultJSONObject.getString("Info"));
                            recommenderIDCheckState = true;
                            recommenderIDLayout.setErrorEnabled(false);
                        }
                        else {
                            Log.d(TAG, resultJSONObject.getString("Info"));
                            recommenderIDCheckState = false;
                            recommenderIDLayout.setError(resultJSONObject.getString("Info"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFinished() {
                    if (invitationCodeCheckState && (recommenderID.equals("") || (!recommenderID.equals("") && recommenderIDCheckState))) {
                        if (!agreeCheckBox.isChecked()) {
                            Toast.makeText(RegisterActivity.this, "请阅读并同意《用户协议》", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            registerFlipper.setDisplayedChild(1);
                        }
                    }
                }
            });
        }
        else {
            recommenderIDLayout.setErrorEnabled(false);
        }
    }

    @Event(value = R.id.next_step_button)
    private void nextStepButtonClicked(View view) {
        hideKeyboard();
        String password = passwordLayout.getEditText().getText().toString();
        String confirmPassword = confirmPasswordLayout.getEditText().getText().toString();
        if (password.equals("")) {
            passwordLayout.setError("请输入密码");
        }
        else if (!CommonUtils.validatePassword(password)) {
            passwordLayout.setError("密码至少包含6位字符");
        }
        else if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError("两次输入密码不一致");
        }
        else {
            RegisterParams params = new RegisterParams(
                    phoneNumberLayout.getEditText().getText().toString(),
                    invitationCodeLayout.getEditText().getText().toString(),
                    recommenderIDLayout.getEditText().getText().toString(),
                    passwordLayout.getEditText().getText().toString());
            x.http().post(params, new NetCallbackAdapter() {
                @Override
                public void onSuccess(String result) {
                    Toast.makeText(RegisterActivity.this, "注册成功，即将跳转到登录", Toast.LENGTH_SHORT).show();
                    RegisterActivity.this.finish();
                }
            });
        }
    }

    @Event(value = R.id.register_agreement_link)
    private void agreementLinkClicked(View view) {
        UserAgreementActivity.actionStart(this);
    }

    private CountDownTimer timer = new CountDownTimer(31000, 1000) {

        private int second = 30;

        @Override
        public void onTick(long millisUntilFinished) {
            getVerificationCodeButton.setVisibility(View.GONE);
            countdownButton.setVisibility(View.VISIBLE);
            countdownButton.setText("重新获取" + (second--) + "S");
        }

        @Override
        public void onFinish() {
            second = 30;
            countdownButton.setVisibility(View.GONE);
            getVerificationCodeButton.setVisibility(View.VISIBLE);
        }
    };
}
