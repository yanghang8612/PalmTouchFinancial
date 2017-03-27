package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.GlobalVariable;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.fragment.FragmentFactory;
import com.huachuang.palmtouchfinancial.util.CommonUtils;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.uuch.adlibrary.AdConstant;
import com.uuch.adlibrary.AdManager;
import com.uuch.adlibrary.bean.AdInfo;
import com.uuch.adlibrary.transformer.ZoomOutPageTransformer;
import com.youth.banner.transformer.DepthPageTransformer;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import it.sephiroth.android.library.bottomnavigation.BottomNavigation;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";
    private static final int defaultFragmentIndex = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private int preFragmentIndex = 0;
    private int statusBarHeight = 0;
    private List<AdInfo> adList = new ArrayList<>();
    private AdManager adManager = new AdManager(MainActivity.this, adList);
    private ViewPager.PageTransformer transformer = new ZoomOutPageTransformer();

    @ViewInject(R.id.drawer_layout)
    private DrawerLayout drawer;

    @ViewInject(R.id.nav_view)
    private NavigationView navigationView;

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.main_content)
    private LinearLayout mainContent;

    @ViewInject(R.id.bottom_navigation_bar)
    private BottomNavigation bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalVariable.api = WXAPIFactory.createWXAPI(this, GlobalParams.APP_ID ,true);
        GlobalVariable.api.registerApp(GlobalParams.APP_ID);

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        if (Build.VERSION.SDK_INT >= 21) {
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
            getWindow().getDecorView().setSystemUiVisibility(option);
        }

        AppBarLayout.LayoutParams toolbarParams = (AppBarLayout.LayoutParams) toolbar.getLayoutParams();
        toolbarParams.setMargins(0, statusBarHeight, 0, 0);
        toolbar.setLayoutParams(toolbarParams);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.getHeaderView(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                PersonalInfoActivity.actionStart(MainActivity.this);
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationBar.setOnMenuItemClickListener(new BottomNavigation.OnMenuItemSelectionListener() {
            @Override
            public void onMenuItemSelect(@IdRes int id, int index, boolean b) {
                switchFragment(index);
            }

            @Override
            public void onMenuItemReselect(@IdRes int id, int index, boolean b) {

            }
        });

        initAdList();
        adManager.setOverScreen(true)
                .setPageTransformer(transformer)
                .setBounciness(0)
                .setSpeed(5);
        adManager.showAdDialog(AdConstant.ANIM_DOWN_TO_UP);

        showDefaultFragment();

        System.out.println(Arrays.toString(CommonUtils.getCardType(this, "6222020200079068785")));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_real_name_information:
                RealNameInfoActivity.actionStart(this);
                break;
            case R.id.nav_change_debit_card:
                DebitCardActivity.actionStart(this);
                break;
            case R.id.nav_my_rate:
                RateActivity.actionStart(this);
                break;
            case R.id.nav_profit_share:
                ProfitActivity.actionStart(this);
                break;
            case R.id.nav_agent_manager:
                AgentManagerActivity.actionStart(this);
                break;
            case R.id.nav_change_password:
                break;
            case R.id.nav_sign_out:
                break;
            default:
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void initAdList() {
        AdInfo adInfo;
        adInfo = new AdInfo();
        adInfo.setActivityImg("https://raw.githubusercontent.com/yipianfengye/android-adDialog/master/images/testImage1.png");
        adList.add(adInfo);

        adInfo = new AdInfo();
        adInfo.setActivityImg("https://raw.githubusercontent.com/yipianfengye/android-adDialog/master/images/testImage2.png");
        adList.add(adInfo);
    }

    private void showDefaultFragment() {
        Fragment defaultFragment = FragmentFactory.getInstanceByIndex(defaultFragmentIndex);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment, defaultFragment);
        transaction.commit();
    }

    public void switchFragment(int toIndex) {
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

        int contentMarginTop = (toIndex == 0) ? 0 : statusBarHeight + CommonUtils.dip2px(this, 48);
        DrawerLayout.LayoutParams params = (DrawerLayout.LayoutParams) mainContent.getLayoutParams();
        params.setMargins(0, contentMarginTop, 0, 0);
        mainContent.setLayoutParams(params);
        if (Build.VERSION.SDK_INT >= 21) {
            if (toIndex == 0) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
                toolbar.setBackgroundColor(Color.TRANSPARENT);
            }
            else {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        }
    }
}
