package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_rate)
public class RateActivity extends BaseActivity {

    public static final String TAG = RateActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RateActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.rate_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.rate_1)
    private TextView rate1;

    @ViewInject(R.id.rate_2)
    private TextView rate2;

    @ViewInject(R.id.rate_3)
    private TextView rate3;

    @ViewInject(R.id.rate_4)
    private TextView rate4;

    @ViewInject(R.id.rate_5)
    private TextView rate5;

    @ViewInject(R.id.rate_6)
    private TextView rate6;

    @ViewInject(R.id.rate_7)
    private TextView rate7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (UserManager.getCurrentUser().getUserType() == 0) {
            if (UserManager.getCurrentUser().isVip()) {
                setRate("0.38%");
            }
            else {
                setRate("0.49%");
            }
        }
        else {
            setRate("0.30%");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setRate(String rate) {
        rate1.setText(rate);
        rate2.setText(rate);
        rate3.setText(rate);
        rate4.setText(rate);
        rate5.setText(rate);
        rate6.setText(rate);
        rate7.setText(rate);
    }
}
