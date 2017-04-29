package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.adapter.ProfitViewPagerAdapter;
import com.huachuang.palmtouchfinancial.backend.bean.ProfitRecord;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.GetWalletBalanceRecords;
import com.huachuang.palmtouchfinancial.fragment.WalletFragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_profit)
public class ProfitActivity extends BaseActivity {

    private static final String TAG = ProfitActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ProfitActivity.class);
        context.startActivity(intent);
    }

    private ProfitViewPagerAdapter adapter;

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
        x.http().post(new GetWalletBalanceRecords(), new NetCallbackAdapter(this, false) {
            @Override
            public void onSuccess(String result) {
                JSONObject resultJsonObject;
                try {
                    resultJsonObject = new JSONObject(result);
                    if (resultJsonObject.getBoolean("Status")) {
                        adapter = new ProfitViewPagerAdapter(ProfitActivity.this,
                                getResources().getStringArray(R.array.profit_tab),
                                JSON.parseArray(resultJsonObject.getString("Records"), ProfitRecord.class));
                        profitViewPager.setAdapter(adapter);
                        profitTabLayout.setupWithViewPager(profitViewPager);
                        adapter.notifyDataSetChanged();
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
