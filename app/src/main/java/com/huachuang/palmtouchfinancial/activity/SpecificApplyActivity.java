package com.huachuang.palmtouchfinancial.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_specific_apply)
public class SpecificApplyActivity extends BaseActivity {

    public static String[] bankName = {
            "浦发银行信用卡申请",
            "中信银行信用卡申请",
            "浦发银行信用卡申请",
            "上海银行信用卡申请",
            "交通银行信用卡申请",
            "招商银行信用卡申请",
            "中国光大银行信用卡申请",
            "平安银行信用卡申请"};

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

    @ViewInject(R.id.apply_web_view)
    private WebView webView;

    @Override
    @TargetApi(19)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bankID = getIntent().getIntExtra("bank", 0);
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle(bankName[bankID]);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
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
                switch (bankID) {
                    case 0:
                        js += "$('#realname').val('杨行');";
                        js += "$('#indentificationId').val('411521199111210033');";
                        js += "$('#tel').val('18511838501');";
                        break;
                    case 1:
                        js += "$('#chaneseName').val('杨行');";
                        js += "$('#pingyin').val('YANG HANG');";
                        js += "$('#identity').val('411521199111210033');";
                        js += "$('#phoneNum').val('18511838501');";
                        break;
                    case 2:
                        js += "$('#cuName').val('杨行');";
                        js += "$('#cuIdentity').val('411521199111210033');";
                        js += "$('#cuMobilePhone').val('18511838501');";
                        break;
                    case 3:
                        js += "document.getElementById('TelephoneNo').value='18511838501';";
                        js += "$('#TelephoneNo').val('18511838501');";
                        js += "$('#username').val('杨行');";
                        js += "$('#usernameSpell').val('YANG HANG');";
                        js += "$('#IdNo').val('411521199111210033');";
                        break;
                    case 4:
                        break;
                    case 5:
                        js += "document.getElementById('ctl00_ContentPlaceHolder1_txbName').value='杨行';";
                        js += "document.getElementById('ctl00_ContentPlaceHolder1_txbCardId').value='411521199111210033';";
                        js += "document.getElementById('tbxMobile').value='18511838501';";
                        break;
                    case 6:
                        js += "$('#name').val('杨行');";
                        js += "$('#namepy').val('YANG HANG');";
                        js += "$('#id_no').val('411521199111210033');";
                        js += "$('#mobilephone').val('18511838501');";
                        break;
                    case 7:
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        js += "$('#name').val('杨行');";
                        js += "$('#pinyin').val('YANGHANG');";
                        js += "$('#idNo').val('411521199111210033');";
                        js += "$('#tel').val('18511838501');";
                        break;
                }
                view.evaluateJavascript(js, new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {

                    }
                });
                Log.d("yang", "caonima");
            }
        };
        webView.setWebViewClient(client);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(bankUrl[bankID]);
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
