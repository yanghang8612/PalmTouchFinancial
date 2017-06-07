package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_credit_card_apply)
public class CreditCardApplyActivity extends BaseSwipeActivity {

    private static final String TAG = CreditCardApplyActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CreditCardApplyActivity.class);
        context.startActivity(intent);
    }

    private boolean applyForSelf;

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
        confirmApplyForSelfAndJump(0);
    }

    @Event(R.id.credit_card_apply_zhongxin)
    private void zhongxinClicked(View view) {
        confirmApplyForSelfAndJump(1);
    }

    @Event(R.id.credit_card_apply_pufa)
    private void pufaClicked(View view) {
        confirmApplyForSelfAndJump(2);
    }

    @Event(R.id.credit_card_apply_shanghai)
    private void shanghaiClicked(View view) {
        confirmApplyForSelfAndJump(3);
    }

    @Event(R.id.credit_card_apply_jiaotong)
    private void jiaotongClicked(View view) {
        confirmApplyForSelfAndJump(4);
    }

    @Event(R.id.credit_card_apply_zhaoshang)
    private void zhaoshangClicked(View view) {
        confirmApplyForSelfAndJump(5);
    }

    @Event(R.id.credit_card_apply_guangda)
    private void guangdaClicked(View view) {
        confirmApplyForSelfAndJump(6);
    }

    @Event(R.id.credit_card_apply_pingan)
    private void pinganClicked(View view) {
        confirmApplyForSelfAndJump(7);
    }

    @Event(R.id.credit_card_apply_minsheng)
    private void minshengClicked(View view) {
        confirmApplyForSelfAndJump(8);
    }

    private void confirmApplyForSelfAndJump(final int bandID) {
        new MaterialDialog.Builder(this)
                .content("选择申请对象，若为本人，则开启填单助手")
                .contentColorRes(R.color.black)
                .positiveText("本人")
                .negativeText("他人")
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SpecificApplyActivity.actionStart(CreditCardApplyActivity.this, bandID, true);
                        dialog.dismiss();
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SpecificApplyActivity.actionStart(CreditCardApplyActivity.this, bandID, false);
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
