package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.fragment.FragmentFactory;
import com.huachuang.palmtouchfinancial.fragment.HomepageFragment;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import de.hdodenhof.circleimageview.CircleImageView;

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

    @ViewInject(R.id.main_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.drawer_layout)
    private DrawerLayout drawer;

    @ViewInject(R.id.nav_view)
    private NavigationView navigationView;

    @ViewInject(R.id.main_content)
    private LinearLayout mainContent;

    @ViewInject(R.id.bottom_navigation_bar)
    private BottomNavigationBar bottomNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initToolbarAndStatusBar();
        initNavHeaderView();
        initBottomNavigationBar();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((HomepageFragment) FragmentFactory.getInstanceByIndex(0)).stopAdCarousel();
                ForceJumpActivity.actionStart(MainActivity.this);
            }
        }, 500);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshNavHeaderView();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            new MaterialDialog.Builder(this)
                    .content("确认退出吗?")
                    .contentColorRes(R.color.black)
                    .positiveText("确认")
                    .negativeText("取消")
                    .autoDismiss(false)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            MainActivity.super.onBackPressed();
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

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
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
                ChangePasswordActivity.actionStart(this);
                break;
            case R.id.nav_sign_out:
                new MaterialDialog.Builder(this)
                        .content("确认退出登录吗?")
                        .contentColorRes(R.color.black)
                        .positiveText("确认")
                        .negativeText("取消")
                        .autoDismiss(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                SharedPreferences defaultPref = getSharedPreferences(DEFAULT_PRE, MODE_PRIVATE);
                                SharedPreferences.Editor editor = defaultPref.edit();
                                editor.clear();
                                editor.commit();
                                LoginActivity.actionStart(MainActivity.this);
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

    private void initToolbarAndStatusBar() {
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
    }

    private void initNavHeaderView() {
        refreshNavHeaderView();
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
    }

    private void refreshNavHeaderView() {
        View navHeaderView = navigationView.getHeaderView(0);
        CircleImageView agentLevelView = (CircleImageView) navHeaderView.findViewById(R.id.nav_header_user_type_agent);
        if (UserManager.getCurrentUser().getUserType() == 0) {
            if (UserManager.getCurrentUser().isVip()) {
                navHeaderView.findViewById(R.id.nav_header_user_type_common).setVisibility(View.GONE);
                navHeaderView.findViewById(R.id.nav_header_user_type_vip).setVisibility(View.VISIBLE);
            }
        }
        else {
            navHeaderView.findViewById(R.id.nav_header_user_type_common).setVisibility(View.GONE);
            switch (UserManager.getCurrentUser().getUserType()) {
                case 1:
                    agentLevelView.setImageResource(R.drawable.ic_level_one);
                    break;
                case 2:
                    agentLevelView.setImageResource(R.drawable.ic_level_two);
                    break;
                case 3:
                    agentLevelView.setImageResource(R.drawable.ic_level_three);
                    break;
            }
            agentLevelView.setVisibility(View.VISIBLE);
        }

        CircleImageView headerImageView = (CircleImageView) navHeaderView.findViewById(R.id.nav_header_image_view);
        if (UserManager.getCurrentUser().getHeaderState()) {
            Glide.with(this)
                    .load(CommonUtils.getHeaderUrl())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(headerImageView);
        }

        TextView phoneNumberView = (TextView) navHeaderView.findViewById(R.id.nav_header_phone_number);
        phoneNumberView.setText(UserManager.getCurrentUser().getUserPhoneNumber());

        TextView nameView = (TextView) navHeaderView.findViewById(R.id.nav_header_name);
        if (UserManager.getCurrentUser().getCertificationState()) {
            nameView.setText(UserManager.getCertificationInfo().getUserName());
        }
        else {
            nameView.setText("<请实名认证>");
        }

        if (UserManager.getCurrentUser().getUserType() == 0) {
            navigationView.getMenu().getItem(4).setVisible(false);
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
