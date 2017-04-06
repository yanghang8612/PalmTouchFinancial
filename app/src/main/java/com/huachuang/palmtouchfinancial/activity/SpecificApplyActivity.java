package com.huachuang.palmtouchfinancial.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_specific_apply)
public class SpecificApplyActivity extends BaseSwipeActivity {

    public static final String TAG = SpecificApplyActivity.class.getSimpleName();

    public static String[] bankName = {
            "浦发银行",
            "中信银行",
            "浦发银行",
            "上海银行",
            "交通银行",
            "招商银行",
            "中国光大银行",
            "平安银行"};

    public static String[] bankUrl = {
            "https://wm.cib.com.cn/application/cardapp/Fast/TwoBar/view?id=9bedfe6b25ca47dcae22ad30931e680e&from=timeline&isappinstalled=0",
            "http://creditcard.ecitic.com/h5/shenqing/list.html?foot_s=0&sid=SJRSYXQ4",
            "https://ecentre.spdbccc.com.cn/creditcard/indexActivity.htm?data=P1127187",
            "https://mbank.bankofshanghai.com/pweixin/static/index.html?_TransactionId=CreditCardApply&_CardType=0300001720&YLLink=620019",
            "https://creditcardapp.bankcomm.com/applynew/front/apply/track/record.html?trackCode=A101208503136",
            "https://ccclub.cmbchina.com/mca/MPreContract.aspx?cardSel=uc&swbrief=Y&WT.mc_id=N3700MM2061I392500ZH",
            "https://xyk.cebbank.com/cebmms/apply/ps/card-index.htm?req_card_id=3341&pro_code=FHTG023556SJ20RONG",
            "https://c.pingan.com/apply/mobile/apply/index.html"};

    public static void actionStart(Context context, int bank) {
        Intent intent = new Intent(context, SpecificApplyActivity.class);
        intent.putExtra("bank", bank);
        context.startActivity(intent);
    }

    private int bankID;

    @ViewInject(R.id.specific_apply_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.specific_apply_title)
    private TextView title;

    @ViewInject(R.id.apply_web_view)
    private WebView webView;

    @Override
    @TargetApi(19)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        bankID = getIntent().getIntExtra("bank", 0);
        title.setText(bankName[bankID]);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.getSettings().setBlockNetworkImage(false);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setAllowContentAccess(true);
        WebViewClient client = new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                String js = "";
                String name = UserManager.getCertificationInfo().getUserName();
                String spell = UserManager.getCertificationInfo().getUserSpell();
                String identityCard = UserManager.getCertificationInfo().getUserIdentityCard();
                String phoneNumber = UserManager.getUserPhoneNumber();
                switch (bankID) {
                    case 0:
                        js += "$('#realname').val('" + name + "');";
                        js += "$('#indentificationId').val('" + identityCard + "');";
                        js += "$('#tel').val('" + phoneNumber + "');";
                        break;
                    case 1:
                        js += "$('#chaneseName').val('" + name + "');";
                        js += "$('#pingyin').val('" + spell + "');";
                        js += "$('#identity').val('" + identityCard + "');";
                        js += "$('#phoneNum').val('" + phoneNumber + "');";
                        break;
                    case 2:
                        js += "$('#cuName').val('" + name + "');";
                        js += "$('#cuIdentity').val('" + identityCard + "');";
                        js += "$('#cuMobilePhone').val('" + phoneNumber + "');";
                        break;
                    case 3:
                        js += "document.getElementById('TelephoneNo').value='" + phoneNumber + "';";
                        js += "$('#TelephoneNo').val('" + phoneNumber + "');";
                        js += "$('#username').val('" + name + "');";
                        js += "$('#usernameSpell').val('" + spell + "');";
                        js += "$('#IdNo').val('" + identityCard + "');";
                        break;
                    case 4:
                        js += "$('#pccc_applyName').val('" + name + "');";
                        js += "$('#pccc_certNo').val('" + identityCard + "');";
                        js += "$('#pccc_applyTel').val('" + phoneNumber + "');";
                        break;
                    case 5:
                        js += "document.getElementById('ctl00_ContentPlaceHolder1_txbName').value='" + name + "';";
                        js += "document.getElementById('ctl00_ContentPlaceHolder1_txbCardId').value='" + identityCard + "';";
                        js += "document.getElementById('tbxMobile').value='" + phoneNumber + "';";
                        break;
                    case 6:
                        js += "$('#name').val('" + name + "');";
                        js += "$('#namepy').val('" + spell + "');";
                        js += "$('#id_no').val('" + identityCard + "');";
                        js += "$('#mobilephone').val('" + phoneNumber + "');";
                        break;
                    case 7:
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        js += "$('#name').val('" + name + "');";
                        js += "$('#pinyin').val('" + spell + "');";
                        js += "$('#idNo').val('" + identityCard + "');";
                        js += "$('#tel').val('" + phoneNumber + "');";
                        break;
                }
                view.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }


                });
            }
        };
        webView.setWebViewClient(client);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(bankUrl[bankID]);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            new MaterialDialog.Builder(this)
                    .content("确认放弃申请吗?")
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
