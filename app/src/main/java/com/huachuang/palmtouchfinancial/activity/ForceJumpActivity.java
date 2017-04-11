package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.fragment.FragmentFactory;
import com.huachuang.palmtouchfinancial.fragment.HomepageFragment;
import com.huachuang.palmtouchfinancial.loader.AdImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_force_jump)
public class ForceJumpActivity extends BaseActivity {

    public static final String TAG = ForceJumpActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ForceJumpActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.startActivity(intent);
    }

    @ViewInject(R.id.force_jump_ads)
    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.jump_1);
        images.add(R.drawable.jump_2);
        banner.setImages(images).setImageLoader(new AdImageLoader()).setDelayTime(3000).setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position == 0) {
                    new MaterialDialog.Builder(ForceJumpActivity.this)
                            .content("会员注册即将开启，敬请期待")
                            .contentColorRes(R.color.black)
                            .positiveText("确认")
                            .autoDismiss(false)
                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
                else {
                    if (UserManager.getCurrentUser().getCertificationState()) {
                        SpecificApplyActivity.actionStart(ForceJumpActivity.this, 7);
                    }
                    else {
                        new MaterialDialog.Builder(ForceJumpActivity.this)
                                .content("请先进行实名认证")
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
                }
            }
        }).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((HomepageFragment) FragmentFactory.getInstanceByIndex(0)).startAdCarousel();
    }

    @Event(R.id.close)
    private void closeClicked(View view) {
        getWindow().getDecorView().setBackgroundColor(Color.TRANSPARENT);
        finish();
    }
}
