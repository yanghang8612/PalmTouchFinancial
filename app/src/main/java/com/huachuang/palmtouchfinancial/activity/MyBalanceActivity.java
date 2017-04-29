package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.GetUserWallet;
import com.huachuang.palmtouchfinancial.fragment.WalletFragment;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_my_balance)
public class MyBalanceActivity extends BaseSwipeActivity {

    private static final String TAG = MyBalanceActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MyBalanceActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.my_balance_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.balance_amount)
    private TextView balanceAmount;

    @ViewInject(R.id.balance_withdraw_record_list)
    private RecyclerView withdrawRecords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initBalance();
        withdrawRecords.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initBalance() {
        x.http().post(new GetUserWallet(), new NetCallbackAdapter(this, false) {
            @Override
            public void onSuccess(String result) {
                JSONObject resultJsonObject;
                try {
                    resultJsonObject = new JSONObject(result);
                    if (resultJsonObject.getBoolean("Status")) {
                        balanceAmount.setText("￥ " + resultJsonObject.getString("Balance"));
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
}
