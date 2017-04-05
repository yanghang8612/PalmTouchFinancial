package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.bean.User;
import com.huachuang.palmtouchfinancial.backend.bean.UserCertificationInfo;
import com.huachuang.palmtouchfinancial.backend.bean.UserDebitCard;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.LoginParams;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity implements View.OnFocusChangeListener {

    private static final String TAG = LoginActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.login_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.login_phone_number_layout)
    private TextInputLayout phoneNumberLayout;

    @ViewInject(R.id.login_password_layout)
    private TextInputLayout passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        passwordLayout.getEditText().setText("");
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            ((TextInputLayout) v.getParent().getParent()).setErrorEnabled(false);
        }
    }

    @Event(value = R.id.login_password_edit,
            type = TextView.OnEditorActionListener.class)
    private boolean onLoginPasswordEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_NULL || actionId == EditorInfo.IME_ACTION_SEARCH) {
            attemptLogin();
            return true;
        }
        return false;
    }

    @Event(R.id.sign_in_button)
    private void onSignInButtonClicked(View view) {
        attemptLogin();
    }

    @Event(R.id.register_link)
    private void onRegisterLinkClicked(View view) {
        RegisterActivity.actionStart(this);
    }

    private void attemptLogin() {
        hideKeyboard();
        String phoneNumber = phoneNumberLayout.getEditText().getText().toString();
        String password = passwordLayout.getEditText().getText().toString();

        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberLayout.setError("请输入手机号");
            return;
        }
        else if (!CommonUtils.validatePhone(phoneNumber)) {
            phoneNumberLayout.setError(getString(R.string.error_invalid_phone_number));
            return;
        }
        else {
            phoneNumberLayout.setErrorEnabled(false);
        }

        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("请输入密码");
            return;
        }
        else if (!CommonUtils.validatePassword(password)) {
            passwordLayout.setError(getString(R.string.error_invalid_password));
            return;
        }
        else {
            passwordLayout.setErrorEnabled(false);
        }

        x.http().post(new LoginParams(phoneNumber, password), new NetCallbackAdapter(this) {
            @Override
            public void onSuccess(String result) {
                JSONObject resultJsonObject;
                try {
                    SharedPreferences defaultPref = getSharedPreferences(DEFAULT_PRE, MODE_PRIVATE);
                    resultJsonObject = new JSONObject(result);
                    if (resultJsonObject.getBoolean("Status")) {
                        User user = JSON.parseObject(resultJsonObject.getString("User"), User.class);
                        //String token = resultJsonObject.getString("Token");
                        UserManager.setCurrentUser(user);
                        //UserManager.setToken(token);

                        if (user.getCertificationState()) {
                            UserManager.setCertificationInfo(
                                    JSON.parseObject(resultJsonObject.getString("CertificationInfo"), UserCertificationInfo.class));
                        }

                        if (user.getDebitCardState()) {
                            UserManager.setDebitCardInfo(
                                    JSON.parseObject(resultJsonObject.getString("DebitCard"), UserDebitCard.class));
                        }

                        SharedPreferences.Editor editor = defaultPref.edit();
                        editor.putString("phoneNumber", UserManager.getCurrentUser().getUserPhoneNumber());
                        editor.putString("password", UserManager.getCurrentUser().getUserPassword());
                        //editor.putString("token", token);
                        editor.apply();
                        editor.commit();

                        MainActivity.actionStart(LoginActivity.this);
                        finish();
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

    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String phoneNumber;
        private final String password;

        UserLoginTask(String phoneNumber, String password) {
            this.phoneNumber = phoneNumber;
            this.password = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(500);
            } catch (InterruptedException e) {
                return false;
            }

            // TODO: register the new account here.
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
        }

        @Override
        protected void onCancelled() {
        }
    }
}

