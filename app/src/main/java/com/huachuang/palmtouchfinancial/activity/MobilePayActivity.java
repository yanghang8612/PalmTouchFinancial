package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_mobile_pay)
public class MobilePayActivity extends BaseActivity {

    private static final String TAG = MobilePayActivity.class.getSimpleName();

    public static final int REQUEST_CODE_PAY = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MobilePayActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.mobile_pay_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.mobile_pay_bottomsheet)
    private BottomSheetLayout bottomSheet;

    @ViewInject(R.id.mobile_pay_rate)
    private TextView payRate;

    @ViewInject(R.id.mobile_pay_tips)
    private TextView payTips;

    @ViewInject(R.id.mobile_pay_amount)
    private EditText payAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (UserManager.getCurrentUser().getUserType() == 0) {
            if (UserManager.getCurrentUser().isVip()) {
                payRate.setText("0.38%");
            }
            else {
                payRate.setText("0.49%");
                payTips.setVisibility(View.VISIBLE);
            }
        }
        else {
            payRate.setText("0.30%");
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

    @Event(R.id.mobile_pay_one)
    private void oneClicked(View view) {
        addNumber("1");
    }

    @Event(R.id.mobile_pay_two)
    private void twoClicked(View view) {
        addNumber("2");
    }

    @Event(R.id.mobile_pay_three)
    private void threeClicked(View view) {
        addNumber("3");
    }

    @Event(R.id.mobile_pay_four)
    private void fourClicked(View view) {
        addNumber("4");
    }

    @Event(R.id.mobile_pay_five)
    private void fiveClicked(View view) {
        addNumber("5");
    }

    @Event(R.id.mobile_pay_six)
    private void sixClicked(View view) {
        addNumber("6");
    }

    @Event(R.id.mobile_pay_seven)
    private void sevenClicked(View view) {
        addNumber("7");
    }

    @Event(R.id.mobile_pay_eight)
    private void eightClicked(View view) {
        addNumber("8");
    }

    @Event(R.id.mobile_pay_nine)
    private void nineClicked(View view) {
        addNumber("9");
    }

    @Event(R.id.mobile_pay_zero)
    private void zeroClicked(View view) {
        addNumber("0");
    }

    @Event(R.id.mobile_pay_double_zero)
    private void doubleZeroClicked(View view) {
        addNumber("00");
    }

    @Event(R.id.mobile_pay_dot)
    private void dotClicked(View view) {
        addNumber(".");
    }

    private void addNumber(String num) {
        String curAmount = payAmount.getText().toString().substring(1);
        if (curAmount.equals("0") && !num.equals(".")) {
            curAmount = "";
        }
        curAmount += num;
        String regExp = "^([1-9]\\d*|0)(\\.[0-9]{0,2})?$";
        if (curAmount.matches(regExp)) {
            payAmount.setText("￥" + curAmount);
        }
    }

    @Event(R.id.mobile_pay_backspace)
    private void backspaceClicked(View view) {
        String curAmount = payAmount.getText().toString().substring(1);
        if (curAmount.length() == 1 && !curAmount.equals("0")) {
            payAmount.setText("￥0");
        }
        if (curAmount.length() > 1) {
            payAmount.setText("￥" + curAmount.substring(0, curAmount.length() - 1));
        }
    }

    @Event(R.id.mobile_pay_clear)
    private void clearClicked(View view) {
        payAmount.setText("￥0");
    }

    @Event(R.id.mobile_pay_button)
    private void payButtonClicked(View view) {
        String amountStr = payAmount.getText().toString().substring(1);
        final double amount = Double.valueOf(amountStr);
        if (amount == 0.0) {
            new MaterialDialog.Builder(this)
                    .content("请输入正确的收款金额")
                    .contentColorRes(R.color.black)
                    .positiveText("确认")
                    .autoDismiss(false)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        }
        else {
            MenuSheetView menuSheetView =
                    new MenuSheetView(this, MenuSheetView.MenuType.LIST, "请选择支付方式", new MenuSheetView.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.wechatpay) {
                                PayQrCodeActivity.actionStart(MobilePayActivity.this, REQUEST_CODE_PAY, 0, (int) amount * 100, "商户收款", false);
                            }
                            else {
                                PayQrCodeActivity.actionStart(MobilePayActivity.this, REQUEST_CODE_PAY, 1, (int) amount * 100, "商户收款", false);
                            }
                            if (bottomSheet.isSheetShowing()) {
                                bottomSheet.dismissSheet();
                            }
                            return true;
                        }
                    });
            menuSheetView.inflateMenu(R.menu.pay_way);
            bottomSheet.showWithSheetView(menuSheetView);
        }
    }
}
