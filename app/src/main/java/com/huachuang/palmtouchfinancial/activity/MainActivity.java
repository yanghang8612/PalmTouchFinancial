package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.GlobalVariable;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.fragment.FragmentFactory;
import com.huachuang.palmtouchfinancial.fragment.HomepageFragment;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int defaultFragmentIndex = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    private int preFragmentIndex = 0;
    private int statusBarHeight = 0;

    @ViewInject(R.id.drawer_layout)
    private DrawerLayout drawer;

    @ViewInject(R.id.nav_view)
    private NavigationView navigationView;

    @ViewInject(R.id.toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.main_content)
    private LinearLayout mainContent;

    @ViewInject(R.id.bottom_navigation_bar)
    private BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GlobalVariable.api = WXAPIFactory.createWXAPI(this, GlobalParams.WECHAT_APP_ID ,true);
        GlobalVariable.api.registerApp(GlobalParams.WECHAT_APP_ID);

        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }

        int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN;
        getWindow().getDecorView().setSystemUiVisibility(option);

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

        bottomNavigationBar
                .setMode(BottomNavigationBar.MODE_FIXED)
                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                .addItem(new BottomNavigationItem(R.drawable.ic_home_white, "主页"))
                .addItem(new BottomNavigationItem(R.drawable.ic_share_white, "分享"))
                .addItem(new BottomNavigationItem(R.drawable.ic_wallet_white, "钱包"))
                .addItem(new BottomNavigationItem(R.drawable.ic_my_white, "我的"))
                .setActiveColor(R.color.bottom_bar_active)
                .setInActiveColor(R.color.bottom_bar_inactive)
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

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((HomepageFragment) FragmentFactory.getInstanceByIndex(0)).stopAdCarousel();
                ForceJumpActivity.actionStart(MainActivity.this);
            }
        }, 2000);
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

    private void showDefaultFragment() {
        Fragment defaultFragment = FragmentFactory.getInstanceByIndex(defaultFragmentIndex);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_fragment, defaultFragment);
        transaction.commit();
    }

    public void switchFragment(int toIndex) {
        if (toIndex == 0) {
            toolbar.setBackgroundColor(Color.TRANSPARENT);
        }
        else {
            toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
        if (Build.VERSION.SDK_INT >= 21) {
            if (toIndex == 0) {
                getWindow().setStatusBarColor(Color.TRANSPARENT);
            }
            else {
                getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
            }
        }

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
}
