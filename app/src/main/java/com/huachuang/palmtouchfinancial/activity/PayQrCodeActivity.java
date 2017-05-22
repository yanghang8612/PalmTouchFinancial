package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.APayStateCheckParams;
import com.huachuang.palmtouchfinancial.backend.net.params.AQrCodePayParams;
import com.huachuang.palmtouchfinancial.backend.net.params.WPayStateCheckParams;
import com.huachuang.palmtouchfinancial.backend.net.params.WQrCodePayParams;
import com.huachuang.palmtouchfinancial.util.CommonUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.TimerTask;

@ContentView(R.layout.activity_pay_qr_code)
public class PayQrCodeActivity extends BaseActivity {

    private static final String TAG = RegisterVIPActivity.class.getSimpleName();

    public static void actionStart(Context context, char payWay, int payAmount) {
        Intent intent = new Intent(context, PayQrCodeActivity.class);
        intent.putExtra("way", payWay);
        intent.putExtra("amount", payAmount);
        context.startActivity(intent);
    }

    private String transactionNo = CommonUtils.generateTransactionNo();
    private boolean transactionState = false;
    private char payWay;
    private int payAmount;
    private String qrCodeUrl;

    @ViewInject(R.id.pay_qr_code_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.pay_qr_code_content)
    private LinearLayout content;

    @ViewInject(R.id.pay_qr_code_image)
    private ImageView payQrCodeImage;

    @ViewInject(R.id.pay_qr_code_way)
    private TextView payQrCodeWay;

    @ViewInject(R.id.pay_qr_code_amount)
    private TextView payQrCodeAmount;

    @ViewInject(R.id.pay_qr_code_success)
    private LinearLayout success;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        payWay = getIntent().getCharExtra("way", '0');
        payAmount = getIntent().getIntExtra("amount", 0);
        payQrCodeAmount.setText(String.valueOf(payAmount / 100) + "." + (payAmount % 100 < 10 ? "0" + String.valueOf(payAmount % 100) : String.valueOf(payAmount % 100)));
        if (payWay == '0') {
            payQrCodeWay.setText("微信");
            RequestParams params = new WQrCodePayParams("掌触金控VIP", transactionNo, payAmount);
            x.http().post(params, new NetCallbackAdapter(this, false) {
                @Override
                public void onSuccess(String result) {
                    try {
                        Log.d(TAG, result);
                        JSONObject resultJsonObject = new JSONObject(result);
                        JSONObject contentObject = resultJsonObject.getJSONObject("content");
                        qrCodeUrl = contentObject.getString("code_url");
                        payQrCodeImage.setImageBitmap(CodeUtils.createImage(qrCodeUrl, 640, 640, BitmapFactory.decodeResource(getResources(), R.drawable.ic_wechatpay)));
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        else if (payWay == '1') {
            payQrCodeWay.setText("支付宝");
            RequestParams params = new AQrCodePayParams(transactionNo, payAmount, "掌触金控VIP");
            x.http().post(params, new NetCallbackAdapter(this, false) {
                @Override
                public void onSuccess(String result) {
                    try {
                        Log.d(TAG, result);
                        JSONObject resultJsonObject = new JSONObject(result);
                        JSONObject contentObject = resultJsonObject.getJSONObject("content");
                        qrCodeUrl = contentObject.getString("qr_code");
                        payQrCodeImage.setImageBitmap(CodeUtils.createImage(qrCodeUrl, 640, 640, BitmapFactory.decodeResource(getResources(), R.drawable.ic_alipay)));
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            new MaterialDialog.Builder(this)
                    .content("确认取消支付吗?")
                    .contentColorRes(R.color.black)
                    .positiveText("确认取消")
                    .negativeText("继续支付")
                    .autoDismiss(false)
                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            dialog.dismiss();
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
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class CheckTransactionStateTask implements Runnable {

        @Override
        public void run() {
            if (payWay == '0') {
                RequestParams params = new WPayStateCheckParams(transactionNo);
                x.http().post(params, new NetCallbackAdapter(PayQrCodeActivity.this, false) {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            Log.d(TAG, result);
                            JSONObject resultJsonObject = new JSONObject(result);
                            JSONObject contentObject = resultJsonObject.getJSONObject("content");
                            String state = contentObject.getString("trade_state");
                            if (state.equals("SUCCESS")) {
                                content.setVisibility(View.GONE);
                                success.setVisibility(View.VISIBLE);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            else if (payWay == '1') {
                RequestParams params = new APayStateCheckParams(transactionNo);
                x.http().post(params, new NetCallbackAdapter(PayQrCodeActivity.this, false) {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            Log.d(TAG, result);
                            JSONObject resultJsonObject = new JSONObject(result);
                            JSONObject contentObject = resultJsonObject.getJSONObject("content");
                            String state = contentObject.getString("trade_status");
                            if (state.equals("SUCCESS")) {
                                content.setVisibility(View.GONE);
                                success.setVisibility(View.VISIBLE);
                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }
}