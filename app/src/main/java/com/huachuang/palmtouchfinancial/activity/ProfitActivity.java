package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.adapter.ProfitViewPagerAdapter;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_profit)
public class ProfitActivity extends BaseSwipeActivity {

    public static final String TAG = ProfitActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ProfitActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.profit_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.profit_tab_layout)
    TabLayout profitTabLayout;

    @ViewInject(R.id.profit_view_pager)
    ViewPager profitViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ProfitViewPagerAdapter adapter = new ProfitViewPagerAdapter(this, getResources().getStringArray(R.array.profit_tab));
        profitViewPager.setAdapter(adapter);
        profitTabLayout.setupWithViewPager(profitViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
