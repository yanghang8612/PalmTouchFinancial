package com.huachuang.palmtouchfinancial.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.github.ybq.android.spinkit.SpinKitView;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.bean.User;
import com.huachuang.palmtouchfinancial.backend.bean.UserCertificationInfo;
import com.huachuang.palmtouchfinancial.backend.bean.UserDebitCard;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.LoginParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity {

    private static String TAG = SplashActivity.class.getSimpleName();

    @ViewInject(R.id.spin_kit)
    private SpinKitView progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (UserManager.getLoginState()) {
            MainActivity.actionStart(this);
            finish();
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }, 1000);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    SharedPreferences defaultPref = getSharedPreferences(DEFAULT_PRE, MODE_PRIVATE);
                    String phoneNumber = defaultPref.getString("phoneNumber", "");
                    String password = defaultPref.getString("password", "");
                    if (TextUtils.isEmpty(phoneNumber)) {
                        LoginActivity.actionStart(SplashActivity.this);
                        finish();
                        return;
                    }
                    x.http().post(new LoginParams(phoneNumber, password), new NetCallbackAdapter(SplashActivity.this, false) {
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

                                    MainActivity.actionStart(SplashActivity.this);
                                    finish();
                                }
                                else {
                                    LoginActivity.actionStart(SplashActivity.this);
                                    finish();
                                }
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }, 3000);
        }
    }
}
