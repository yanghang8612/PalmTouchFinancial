package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.fragment.FragmentFactory;
import com.huachuang.palmtouchfinancial.fragment.HomepageFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int defaultFragmentIndex = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private int preFragmentIndex = 0;

    @ViewInject(R.id.bottom_navigation_bar)
    private BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbarAndStatusBar();
        initBottomNavigationBar();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((HomepageFragment) FragmentFactory.getInstanceByIndex(0)).stopAdCarousel();
                ForceJumpActivity.actionStart(MainActivity.this);
            }
        }, 500);
    }

    private void showDefaultFragment() {
        Fragment defaultFragment = FragmentFactory.getInstanceByIndex(defaultFragmentIndex);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment, defaultFragment);
        transaction.commit();
    }

    public void switchFragment(int toIndex) {
//        if (toIndex == 0 || toIndex == 3) {
//            toolbar.setBackgroundColor(Color.TRANSPARENT);
//        }
//        else {
//            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
//        }
//        if (Build.VERSION.SDK_INT >= 21) {
//            if (toIndex == 0 || toIndex == 3) {
//                getWindow().setStatusBarColor(Color.TRANSPARENT);
//            }
//            else {
//                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
//            }
//        }

        bottomNavigationBar.selectTab(toIndex, false);
        Fragment from = FragmentFactory.getInstanceByIndex(preFragmentIndex), to = FragmentFactory.getInstanceByIndex(toIndex);
        if (from == null || to == null) {
            return;
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if (!to.isAdded()) {
            transaction.hide(from).add(R.id.main_fragment, to).commit();
        }
        else {
            transaction.hide(from).show(to).commit();
        }
        preFragmentIndex = toIndex;

//        int contentMarginTop = (toIndex == 0) ? 0 : statusBarHeight + CommonUtils.dip2px(this, 48);
//        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mainContent.getLayoutParams();
//        params.setMargins(0, contentMarginTop, 0, 0);
//        mainContent.setLayoutParams(params);
    }

    private void initToolbarAndStatusBar() {

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initBottomNavigationBar() {
        bottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white, "主页"))
                .addItem(new BottomNavigationItem(R.drawable.ic_share_white, "分享"))
                .addItem(new BottomNavigationItem(R.drawable.ic_wallet_white, "钱包"))
                .addItem(new BottomNavigationItem(R.drawable.ic_my_white, "我的"))
                .setActiveColor(R.color.colorPrimary)
                .setInActiveColor(R.color.bottom_bar_active)
                .setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(int position) {
                        switchFragment(position);
                    }

                    @Override
                    public void onTabUnselected(int position) {}

                    @Override
                    public void onTabReselected(int position) {}
                })
                .initialise();

        showDefaultFragment();
    }
}
