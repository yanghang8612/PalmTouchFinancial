package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_debit_card)
public class DebitCardActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DebitCardActivity.class);
        context.startActivity(intent);
    }

    private short currentFlipper = 0;

    @ViewInject(R.id.debit_card_flipper)
    private ViewFlipper debitCardFlipper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (currentFlipper == 1) {
                new MaterialDialog.Builder(this)
                        .content("确认不保存退出吗?")
                        .contentColorRes(R.color.black)
                        .positiveText("确认")
                        .negativeText("取消")
                        .autoDismiss(false)
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
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
            }
            else {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Event(R.id.debit_card_button)
    private void debitCardButtonClicked(View view) {
        if (currentFlipper == 0) {
            debitCardFlipper.setInAnimation(this, R.anim.push_left_in);
            debitCardFlipper.setDisplayedChild(1);
            ((Button) view).setText("保存结算卡信息");
            currentFlipper = 1;
        }
        else {
            debitCardFlipper.setInAnimation(this, R.anim.push_right_in);
            debitCardFlipper.setDisplayedChild(0);
            ((Button) view).setText("修改结算卡信息");
            currentFlipper = 0;
        }
    }
}
