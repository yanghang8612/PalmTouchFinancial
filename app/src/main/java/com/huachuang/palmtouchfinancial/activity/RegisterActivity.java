package com.huachuang.palmtouchfinancial.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
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
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

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
public class RegisterActivity extends BaseSwipeActivity implements View.OnFocusChangeListener {

    private static final String TAG = RegisterActivity.class.getSimpleName();

    private static int REQUEST_CODE_QR_CODE = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterActivity.class);
        context.startActivity(intent);
    }

    private String generatedVerificationCode = "";
    private boolean identifyCodeCheckState = false;
    private boolean invitationCodeCheckState = false;
    private boolean recommenderIDCheckState = false;
    private ProgressDialog progressDialog;

    @ViewInject(R.id.register_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.register_camera)
    private ImageView camera;

    @ViewInject(R.id.register_flipper)
    private ViewFlipper registerFlipper;

    @ViewInject(R.id.register_phone_number_layout)
    private TextInputLayout phoneNumberLayout;

    @ViewInject(R.id.register_verification_code_layout)
    private TextInputLayout verificationCodeLayout;

    @ViewInject(R.id.register_identify_code_layout)
    private TextInputLayout identifyCodeLayout;

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
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("加载中...");
        progressDialog.setCancelable(false);

        phoneNumberLayout.getEditText().setOnFocusChangeListener(this);
        verificationCodeLayout.getEditText().setOnFocusChangeListener(this);
//        invitationCodeLayout.getEditText().setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View v, boolean hasFocus) {
//                if (hasFocus)  {
//                    Intent intent = new Intent(RegisterActivity.this, CaptureActivity.class);
//                    startActivityForResult(intent, REQUEST_CODE_QR_CODE);
//                }
//            }
//        });
        identifyCodeLayout.getEditText().setOnFocusChangeListener(this);
        passwordLayout.getEditText().setOnFocusChangeListener(this);
        confirmPasswordLayout.getEditText().setOnFocusChangeListener(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == REQUEST_CODE_QR_CODE) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                String content = bundle.getString(CodeUtils.RESULT_STRING);
                final String[] result = content.split("/");
                if (!TextUtils.isEmpty(content) && result[0].equals("palmtouch")) {
                    if (result.length == 2) {
                        identifyCodeLayout.getEditText().setText(result[1]);
                    }
                    else {
                        identifyCodeLayout.getEditText().setText(result[2]);
//                        new MaterialDialog.Builder(this)
//                                .content("推荐人也填写该代理商吗?")
//                                .contentColorRes(R.color.black)
//                                .positiveText("确认")
//                                .negativeText("取消")
//                                .onPositive(new MaterialDialog.SingleButtonCallback() {
//                                    @Override
//                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                        recommenderIDLayout.getEditText().setText(result[1]);
//                                    }
//                                })
//                                .onNegative(new MaterialDialog.SingleButtonCallback() {
//                                    @Override
//                                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                                        dialog.dismiss();
//                                    }
//                                })
//                                .show();
                    }
                }
            }
            else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                showToast("解析二维码失败");
            }
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            ((TextInputLayout) v.getParent().getParent()).setErrorEnabled(false);
        }
    }

    @Event(R.id.register_camera)
    private void registerCameraClicked(View view) {
        Intent intent = new Intent(RegisterActivity.this, CaptureActivity.class);
        startActivityForResult(intent, REQUEST_CODE_QR_CODE);
    }

    @Event(R.id.register_get_verification_code_button)
    private void registerGetVerificationCodeButtonClicked(View view) {
        hideKeyboard();
        final String phoneNumber = phoneNumberLayout.getEditText().getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberLayout.setError("请输入手机号");
        }
        else if (!CommonUtils.validatePhone(phoneNumber)) {
            phoneNumberLayout.setError("请输入正确的手机号");
        }
        else {
            phoneNumberLayout.setErrorEnabled(false);
            x.http().post(new VerifyPhoneNumberParams(phoneNumber), new NetCallbackAdapter(this, progressDialog ,false) {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject resultJsonObject = new JSONObject(result);
                        if (resultJsonObject.getBoolean("Status")) {
                            Log.d(TAG, resultJsonObject.getString("Info"));
                            generatedVerificationCode = CommonUtils.generateVerificationCode();
                            x.http().post(
                                    new GetVerificationCodeParams(phoneNumber, generatedVerificationCode),
                                    new NetCallbackAdapter(RegisterActivity.this, progressDialog) {
                                @Override
                                public void onSuccess(String result) {
                                    Log.d(TAG, "Xutils Net Callback" + result);
                                    timer.start();
                                }
                            });
                        }
                        else {
                            progressDialog.dismiss();
                            showToast(resultJsonObject.getString("Info"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    @Event(R.id.register_next_step_button)
    private void registerNextStepButtonClicked(View view) {
        hideKeyboard();

        String phoneNumber = phoneNumberLayout.getEditText().getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
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
        if (TextUtils.isEmpty(userVerificationCode)) {
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

        final String identifyCode = identifyCodeLayout.getEditText().getText().toString();
        if (TextUtils.isEmpty(identifyCode)) {
            identifyCodeLayout.setError("请输入邀请码或推荐人手机");
            return;
        }
        else if (!CommonUtils.validateInvitationCode(identifyCode) && !CommonUtils.validatePhone(identifyCode)) {
            identifyCodeLayout.setError("6位数字大写字母组合的邀请码或11位推荐人手机号，请检查后重试");
            return;
        }
        else {
            identifyCodeLayout.setErrorEnabled(false);
        }

        if (CommonUtils.validatePhone(identifyCode)) {
            x.http().post(new VerifyRecommenderIDParams(identifyCode), new NetCallbackAdapter(this, progressDialog) {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject resultJSONObject = new JSONObject(result);
                        if (resultJSONObject.getBoolean("Status")) {
                            Log.d(TAG, resultJSONObject.getString("Info"));
                            identifyCodeCheckState = true;
                            identifyCodeLayout.setErrorEnabled(false);
                        }
                        else {
                            Log.d(TAG, resultJSONObject.getString("Info"));
                            identifyCodeCheckState = false;
                            identifyCodeLayout.setError(resultJSONObject.getString("Info"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFinished() {
                    super.onFinished();
                    if (identifyCodeCheckState) {
                        if (!agreeCheckBox.isChecked()) {
                            showToast("请阅读并同意《用户协议》");
                        }
                        else {
                            camera.setVisibility(View.GONE);
                            registerFlipper.setDisplayedChild(1);
                        }
                    }
                }
            });
        }
        else {
            x.http().post(new VerifyInvitationCodeParams(identifyCode), new NetCallbackAdapter(this, progressDialog) {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject resultJSONObject = new JSONObject(result);
                        if (resultJSONObject.getBoolean("Status")) {
                            Log.d(TAG, resultJSONObject.getString("Info"));
                            identifyCodeCheckState = true;
                            identifyCodeLayout.setErrorEnabled(false);
                        }
                        else {
                            Log.d(TAG, resultJSONObject.getString("Info"));
                            identifyCodeCheckState = false;
                            identifyCodeLayout.setError(resultJSONObject.getString("Info"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFinished() {
                    super.onFinished();
                    if (identifyCodeCheckState) {
                        if (!agreeCheckBox.isChecked()) {
                            showToast("请阅读并同意《用户协议》");
                        }
                        else {
                            camera.setVisibility(View.GONE);
                            registerFlipper.setDisplayedChild(1);
                        }
                    }
                }
            });
        }
    }

    @Event(R.id.next_step_button)
    private void nextStepButtonClicked(View view) {
        hideKeyboard();
        String password = passwordLayout.getEditText().getText().toString();
        String confirmPassword = confirmPasswordLayout.getEditText().getText().toString();
        if (TextUtils.isEmpty(password)) {
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
                    identifyCodeLayout.getEditText().getText().toString(),
                    passwordLayout.getEditText().getText().toString());
            x.http().post(params, new NetCallbackAdapter(this, progressDialog) {
                @Override
                public void onSuccess(String result) {
                    showToast("注册成功，即将跳转到登录");
                    RegisterActivity.this.finish();
                }
            });
        }
    }

    @Event(R.id.register_agreement_link)
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
