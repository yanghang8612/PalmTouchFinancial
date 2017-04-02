package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_credit_card_apply)
public class CreditCardApplyActivity extends BaseActivity {

    public static final String TAG = CreditCardApplyActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CreditCardApplyActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.credit_card_apply_toolbar)
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Event(R.id.credit_card_apply_xingye)
    private void xingyeClicked(View view) {
        SpecificApplyActivity.actionStart(this, 0);
        finish();
    }

    @Event(R.id.credit_card_apply_zhongxin)
    private void zhongxinClicked(View view) {
        SpecificApplyActivity.actionStart(this, 1);
        finish();
    }

    @Event(R.id.credit_card_apply_pufa)
    private void pufaClicked(View view) {
        SpecificApplyActivity.actionStart(this, 2);
        finish();
    }

    @Event(R.id.credit_card_apply_shanghai)
    private void shanghaiClicked(View view) {
        SpecificApplyActivity.actionStart(this, 3);
        finish();
    }

    @Event(R.id.credit_card_apply_jiaotong)
    private void jiaotongClicked(View view) {
        SpecificApplyActivity.actionStart(this, 4);
        finish();
    }

    @Event(R.id.credit_card_apply_zhaoshang)
    private void zhaoshangClicked(View view) {
        SpecificApplyActivity.actionStart(this, 5);
        finish();
    }

    @Event(R.id.credit_card_apply_guangda)
    private void guangdaClicked(View view) {
        SpecificApplyActivity.actionStart(this, 6);
        finish();
    }

    @Event(R.id.credit_card_apply_pingan)
    private void pinganClicked(View view) {
        SpecificApplyActivity.actionStart(this, 7);
        finish();
    }
}
