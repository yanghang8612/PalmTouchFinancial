package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.ChangePhoneParams;
import com.huachuang.palmtouchfinancial.backend.net.params.GetVerificationCodeParams;
import com.huachuang.palmtouchfinancial.backend.net.params.VerifyPhoneNumberParams;
import com.huachuang.palmtouchfinancial.util.ActivityCollector;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_change_phone)
public class ChangePhoneActivity extends BaseSwipeActivity implements View.OnFocusChangeListener {

    private static String TAG = ChangePhoneActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ChangePhoneActivity.class);
        context.startActivity(intent);
    }

    private String stepOneVerificationCode;
    private String stepTwoVerificationCode;

    @ViewInject(R.id.change_phone_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.change_phone_flipper)
    private ViewFlipper changePhoneFlipper;

    @ViewInject(R.id.change_phone_old_number)
    private TextView oldNumber;

    @ViewInject(R.id.change_phone_step_one_verification_code_layout)
    private TextInputLayout stepOneVerificationCodeLayout;

    @ViewInject(R.id.change_phone_step_one_get_verification_code_button)
    private Button stepOneGetVerificationCodeButton;

    @ViewInject(R.id.change_phone_step_one_countdown_button)
    private Button stepOneCountdownButton;

    @ViewInject(R.id.change_phone_new_number)
    private TextInputLayout newNumber;

    @ViewInject(R.id.change_phone_step_two_verification_code_layout)
    private TextInputLayout stepTwoVerificationCodeLayout;

    @ViewInject(R.id.change_phone_step_two_get_verification_code_button)
    private Button stepTwoGetVerificationCodeButton;

    @ViewInject(R.id.change_phone_step_two_countdown_button)
    private Button stepTwoCountdownButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        changePhoneFlipper.setDisplayedChild(1);
        changePhoneFlipper.setInAnimation(this, R.anim.push_left_in);
        oldNumber.setText(UserManager.getUserPhoneNumber());
        newNumber.getEditText().setOnFocusChangeListener(this);
        stepOneVerificationCodeLayout.getEditText().setOnFocusChangeListener(this);
        stepTwoVerificationCodeLayout.getEditText().setOnFocusChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            ((TextInputLayout) v.getParent().getParent()).setErrorEnabled(false);
        }
    }

    @Event(R.id.change_phone_step_one_get_verification_code_button)
    private void stepOneGetVerificationCodeButtonClicked(View view) {
        stepOneVerificationCode = CommonUtils.generateVerificationCode();
        x.http().post(
                new GetVerificationCodeParams(UserManager.getUserPhoneNumber(), stepOneVerificationCode),
                new NetCallbackAdapter(this, false) {
                    @Override
                    public void onSuccess(String result) {
                        Log.d(TAG, "Xutils Net Callback" + result);
                        stepOneTimer.start();
                    }
                });
    }

    @Event(R.id.change_phone_step_two_get_verification_code_button)
    private void stepTwoGetVerificationCodeButtonClicked(View view) {
        hideKeyboard();
        final String phoneNumber = newNumber.getEditText().getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            newNumber.setError("请输入手机号");
        }
        else if (!CommonUtils.validatePhone(phoneNumber)) {
            newNumber.setError("请输入正确的手机号");
        }
        else {
            newNumber.setErrorEnabled(false);
            stepTwoVerificationCode = CommonUtils.generateVerificationCode();
            x.http().post(
                    new GetVerificationCodeParams(phoneNumber, stepTwoVerificationCode),
                    new NetCallbackAdapter(this, false) {
                        @Override
                        public void onSuccess(String result) {
                            Log.d(TAG, "Xutils Net Callback" + result);
                            stepTwoTimer.start();
                        }
                    });
        }
    }

    @Event(R.id.change_phone_next_step_button)
    private void nextStepButtonClicked(View view) {
        hideKeyboard();
        String userStepOneVerificationCode = stepOneVerificationCodeLayout.getEditText().getText().toString();
        if (TextUtils.isEmpty(userStepOneVerificationCode)) {
            stepOneVerificationCodeLayout.setError("请输入验证码");
        }
        else if (!userStepOneVerificationCode.equals(stepOneVerificationCode)) {
            stepOneVerificationCodeLayout.setError("请输入正确的验证码");
        }
        else {
            stepOneVerificationCodeLayout.setErrorEnabled(false);
            changePhoneFlipper.setDisplayedChild(1);
        }
    }

    @Event(R.id.change_phone_confirm_button)
    private void confirmButtonClicked(View view) {
        hideKeyboard();
        final String phoneNumber = newNumber.getEditText().getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            newNumber.setError("请输入手机号");
            return;
        }
        else if (!CommonUtils.validatePhone(phoneNumber)) {
            newNumber.setError("请输入正确的手机号");
            return;
        }
        else {
            newNumber.setErrorEnabled(false);
        }

        String userStepTwoVerificationCode = stepTwoVerificationCodeLayout.getEditText().getText().toString();
        if (TextUtils.isEmpty(userStepTwoVerificationCode)) {
            stepTwoVerificationCodeLayout.setError("请输入验证码");
        }
        else if (!userStepTwoVerificationCode.equals(stepTwoVerificationCode)) {
            stepTwoVerificationCodeLayout.setError("请输入正确的验证码");
        }
        else {
            stepTwoVerificationCodeLayout.setErrorEnabled(false);
            RequestParams params = new ChangePhoneParams(phoneNumber);
            x.http().post(params, new NetCallbackAdapter(this, true) {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject resultJSONObject = new JSONObject(result);
                        if (resultJSONObject.getBoolean("Status")) {
                            showToast("修改成功，请重新登录");
                            ActivityCollector.backToLogin();
                        }
                        else {
                            showToast(resultJSONObject.getString("Info"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private CountDownTimer stepOneTimer = new CountDownTimer(31000, 1000) {

        private int second = 30;

        @Override
        public void onTick(long millisUntilFinished) {
            stepOneGetVerificationCodeButton.setVisibility(View.GONE);
            stepOneCountdownButton.setVisibility(View.VISIBLE);
            stepOneCountdownButton.setText("重新获取" + (second--) + "S");
        }

        @Override
        public void onFinish() {
            second = 30;
            stepOneCountdownButton.setVisibility(View.GONE);
            stepOneGetVerificationCodeButton.setVisibility(View.VISIBLE);
        }
    };

    private CountDownTimer stepTwoTimer = new CountDownTimer(31000, 1000) {

        private int second = 30;

        @Override
        public void onTick(long millisUntilFinished) {
            stepTwoGetVerificationCodeButton.setVisibility(View.GONE);
            stepTwoCountdownButton.setVisibility(View.VISIBLE);
            stepTwoCountdownButton.setText("重新获取" + (second--) + "S");
        }

        @Override
        public void onFinish() {
            second = 30;
            stepTwoCountdownButton.setVisibility(View.GONE);
            stepTwoGetVerificationCodeButton.setVisibility(View.VISIBLE);
        }
    };
}
