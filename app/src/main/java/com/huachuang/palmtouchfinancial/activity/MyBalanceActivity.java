package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_my_balance)
public class MyBalanceActivity extends BaseSwipeActivity {

    private static final String TAG = MyBalanceActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MyBalanceActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.my_balance_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.balance_withdraw_record)
    private RecyclerView withdrawRecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        withdrawRecord.setLayoutManager(new LinearLayoutManager(this));
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
