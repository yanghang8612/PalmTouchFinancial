package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.net.params.VerificationCodeParams;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.xutils.common.Callback;
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

    @ViewInject(R.id.register_flipper)
    private ViewFlipper registerFlipper;

    @ViewInject(R.id.register_phone_number)
    private TextInputEditText phoneNumberEditText;

    @ViewInject(R.id.register_verification_code)
    private TextInputEditText verificationCodeEditText;

    @ViewInject(R.id.register_invitation_code)
    private TextInputEditText invitationCodeEditText;

    @ViewInject(R.id.register_recommender_id)
    private TextInputEditText recommenderIdEditText;

    @ViewInject(R.id.register_get_verification_code_button)
    private Button getVerificationCodeButton;

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
    private void registerNextStepButtonClicked(View view) {
        registerFlipper.setDisplayedChild(1);
    }

    @Event(value = R.id.register_get_verification_code_button)
    private void registerGetVerificationCodeButtonClicked(View view) {
        String phoneNumber = phoneNumberEditText.getText().toString();
        if (phoneNumber.equals("")) {
            phoneNumberEditText.setError("请输入手机号");
        }
        else if (!CommonUtils.validatePhone(phoneNumber)) {
            phoneNumberEditText.setError("请输入正确的手机号");
        }
        else {
            String code = CommonUtils.generateVerificationCode();
            x.http().post(new VerificationCodeParams(phoneNumber, code), new Callback.CommonCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Log.d(TAG, result);
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {

                }

                @Override
                public void onCancelled(CancelledException cex) {

                }

                @Override
                public void onFinished() {

                }
            });
        }
    }

    private CountDownTimer timer = new CountDownTimer(30000, 1000) {

        private int second = 30;

        @Override
        public void onTick(long millisUntilFinished) {
            getVerificationCodeButton.setText("" + (second--) + "S");
        }

        @Override
        public void onFinish() {
            second = 30;
        }
    };
}
