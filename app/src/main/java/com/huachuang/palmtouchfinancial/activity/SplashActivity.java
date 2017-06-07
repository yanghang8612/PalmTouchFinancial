package com.huachuang.palmtouchfinancial.activity;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.github.ybq.android.spinkit.SpinKitView;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.bean.User;
import com.huachuang.palmtouchfinancial.backend.bean.UserCertificationInfo;
import com.huachuang.palmtouchfinancial.backend.bean.UserDebitCard;
import com.huachuang.palmtouchfinancial.backend.bean.UserMobilePay;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.LoginParams;
import com.huachuang.palmtouchfinancial.loader.AdImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends BaseActivity {

    private static String TAG = SplashActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        SharedPreferences defaultPref = getSharedPreferences(DEFAULT_PRE, MODE_PRIVATE);
        boolean isFirst = defaultPref.getBoolean("isFirst", true);
        if (isFirst) {
            setContentView(R.layout.activity_splash_first);
            SharedPreferences.Editor editor = defaultPref.edit();
            editor.putBoolean("isFirst", false);
            editor.apply();
            editor.commit();

            Banner banner = (Banner) findViewById(R.id.splash_first_banner);
            final View startButton = findViewById(R.id.splash_first_button);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LoginActivity.actionStart(SplashActivity.this);
                    finish();
                }
            });
            List<Integer> images = new ArrayList<>();
            images.add(R.drawable.first_1);
            images.add(R.drawable.first_2);
            images.add(R.drawable.first_3);
            banner.setIndicatorGravity(BannerConfig.RIGHT)
                    .setImages(images)
                    .setImageLoader(new AdImageLoader())
                    .setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                        @Override
                        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                        }

                        @Override
                        public void onPageSelected(int position) {
                            if (position == 3) {
                                startButton.setVisibility(View.VISIBLE);
                            }
                            else {
                                startButton.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onPageScrollStateChanged(int state) {

                        }
                    });
            banner.isAutoPlay(false).start();
        }
        else {
            setContentView(R.layout.activity_splash);
            final SpinKitView progressBar = (SpinKitView) findViewById(R.id.spin_kit);
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
                                try {
                                    SharedPreferences defaultPref = getSharedPreferences(DEFAULT_PRE, MODE_PRIVATE);
                                    JSONObject resultJsonObject = new JSONObject(result);
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

                                        if (user.getMobilePayState() == 1) {
                                            UserManager.setMobilePay(
                                                    JSON.parseObject(resultJsonObject.getString("MobilePay"), UserMobilePay.class));
                                        }

                                        SharedPreferences.Editor editor = defaultPref.edit();
                                        editor.putString("phoneNumber", UserManager.getCurrentUser().getUserPhoneNumber());
                                        editor.putString("password", UserManager.getCurrentUser().getUserPassword());
                                        //editor.putString("token", token);
                                        editor.apply();
                                        editor.commit();

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
}
