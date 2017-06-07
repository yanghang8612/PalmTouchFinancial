package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.UploadPaymentOrderParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_register_vip)
public class RegisterVIPActivity extends BaseSwipeActivity {

    private static final String TAG = RegisterVIPActivity.class.getSimpleName();

    public static final int REQUEST_CODE_PAY = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RegisterVIPActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.register_vip_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.register_vip_bottomsheet)
    private BottomSheetLayout bottomSheet;

    @ViewInject(R.id.register_vip_user_phone_number)
    private TextView userPhoneNumberView;

    @ViewInject(R.id.pay_way_button)
    private Button payWayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        userPhoneNumberView.setText(UserManager.getUserPhoneNumber());
        if (UserManager.getCurrentUser().isVip()) {
            payWayButton.setEnabled(false);
            payWayButton.setText("已购买会员");
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null && requestCode == REQUEST_CODE_PAY) {
                Bundle bundle = data.getExtras();
                if (bundle.getBoolean("pay_state")) {
                    UserManager.getCurrentUser().setVip(true);
                    payWayButton.setEnabled(false);
                    payWayButton.setText("已购买会员");
                    RequestParams params = new UploadPaymentOrderParams(
                            bundle.getString("transaction_no"),
                            (byte) 0,
                            100.0,
                            (byte) 1,
                            "");
                    x.http().post(params, new NetCallbackAdapter(this, false) {
                        @Override
                        public void onSuccess(String result) {
                        }
                    });
                }
            }
        }
    }

    @Event(R.id.pay_way_button)
    private void payWayButtonClicked(View view) {
        MenuSheetView menuSheetView =
                new MenuSheetView(this, MenuSheetView.MenuType.LIST, "请选择支付方式", new MenuSheetView.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.wechatpay) {
                            PayQrCodeActivity.actionStart(RegisterVIPActivity.this, REQUEST_CODE_PAY, 0, GlobalParams.VIP_FEE, "掌触VIP", true);
                        }
                        else {
                            PayQrCodeActivity.actionStart(RegisterVIPActivity.this, REQUEST_CODE_PAY, 1, GlobalParams.VIP_FEE, "掌触VIP", true);
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
