package com.huachuang.palmtouchfinancial.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.ApplyCreditCardParams;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_specific_apply)
public class SpecificApplyActivity extends BaseSwipeActivity {

    private static final String TAG = SpecificApplyActivity.class.getSimpleName();

    public static String[] bankName = {
            "兴业银行",
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

    private boolean finishedApply = false;
    private int bankID;
    private String userName;
    private String userPhoneNumber;
    private String userCompany;

    @ViewInject(R.id.specific_apply_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.specific_apply_title)
    private TextView title;

    @ViewInject(R.id.apply_web_view)
    private WebView webView;

    @SuppressLint("JavascriptInterface")
    @TargetApi(19)
    @Override
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webView.getSettings().setMixedContentMode(
                    WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
        }
        webView.addJavascriptInterface(new Object() {

            @JavascriptInterface
            public void fetchApplyInfo(String userName, String userPhoneNumber, String userCompany) {
                if (!userName.equals("undefined")) {
                    SpecificApplyActivity.this.userName = userName;
                }
                if (!userPhoneNumber.equals("undefined")) {
                    SpecificApplyActivity.this.userPhoneNumber = userPhoneNumber;
                }
                if (!userCompany.equals("undefined")) {
                    SpecificApplyActivity.this.userCompany = userCompany;
                }
            }

            @JavascriptInterface
            public void commitApplyInfo() {
                x.http().post(
                        new ApplyCreditCardParams(bankID, userName, userPhoneNumber, userCompany),
                        new NetCallbackAdapter(SpecificApplyActivity.this, false) {
                            @Override
                            public void onSuccess(String result) {
                                try {
                                    JSONObject resultJSONObject = new JSONObject(result);
                                    if (resultJSONObject.getBoolean("Status")) {
                                        Log.d(TAG, resultJSONObject.getString("Info"));
                                        finishedApply = true;
                                    }
                                    else {
                                        showToast(resultJSONObject.getString("Info"));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }

        }, "myObj");
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
                        js += "$('div#next').click(function(){myObj.fetchApplyInfo($('#realname').val(),$('#tel').val(),$('#workplace').val())});";
                        js += "$('a#next').click(function(){myObj.commitApplyInfo()});";
                        break;
                    case 1:
                        js += "$('#chaneseName').val('" + name + "');";
                        js += "$('#pingyin').val('" + spell + "');";
                        js += "$('#identity').val('" + identityCard + "');";
                        js += "$('#phoneNum').val('" + phoneNumber + "');";
                        js += "$('#pannalOne .next_button').click(function(){myObj.fetchApplyInfo($('#chaneseName').val(),$('#phoneNum').val(),$('#companyName').val())});";
                        js += "$('#pannalThree .next_button').click(function(){myObj.fetchApplyInfo($('#chaneseName').val(),$('#phoneNum').val(),$('#companyName').val())});";
                        js += "$('#pannalThree .next_button').click(function(){myObj.commitApplyInfo()});";
                        break;
                    case 2:
                        js += "$('#cuName').val('" + name + "');";
                        js += "$('#cuIdentity').val('" + identityCard + "');";
                        js += "$('#cuMobilePhone').val('" + phoneNumber + "');";
                        js += "$('a#next').click(function(){myObj.fetchApplyInfo($('#cuName').val(),$('#cuMobilePhone').val(),$('#cuUnit').val())});";
                        js += "$('a#btn_confirm_apply').click(function(){myObj.fetchApplyInfo($('#cuName').val(),$('#cuMobilePhone').val(),$('#cuUnit').val())});";
                        js += "$('a#submitinfo').click(function(){myObj.commitApplyInfo()});";
                        break;
                    case 3:
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //js += "document.getElementById('TelephoneNo').value='" + phoneNumber + "';";
                        js += "$('#TelephoneNo').val('" + phoneNumber + "');";
                        js += "$('#username').val('" + name + "');";
                        js += "$('#usernameSpell').val('" + spell + "');";
                        js += "$('#IdNo').val('" + identityCard + "');";
                        js += "$($('form[name=\"form1\"] button')[0]).on('click',function(){myObj.fetchApplyInfo($('#username').val(),$('#TelephoneNo').val(),$('#Uname').val())});";
                        js += "$($('form[name=\"form3\"] button')[3]).on('click',function(){myObj.commitApplyInfo()});";
                        break;
                    case 4:
                        js += "$('#pccc_applyName').val('" + name + "');";
                        js += "$('#pccc_certNo').val('" + identityCard + "');";
                        js += "$('#pccc_applyTel').val('" + phoneNumber + "');";
                        js += "$('div#next').click(function(){myObj.fetchApplyInfo($('#realname').val(),$('#tel').val(),$('#workplace').val())});";
                        js += "$('a#next').click(function(){myObj.commitApplyInfo()});";
                        break;
                    case 5:
                        js += "document.getElementById('ctl00_ContentPlaceHolder1_txbName').value='" + name + "';";
                        js += "document.getElementById('ctl00_ContentPlaceHolder1_txbCardId').value='" + identityCard + "';";
                        js += "document.getElementById('tbxMobile').value='" + phoneNumber + "';";
                        js += "$('div#next').click(function(){myObj.fetchApplyInfo($('#realname').val(),$('#tel').val(),$('#workplace').val())});";
                        js += "$('a#next').click(function(){myObj.commitApplyInfo()});";
                        break;
                    case 6:
                        js += "$('#name').val('" + name + "');";
                        js += "$('#namepy').val('" + spell + "');";
                        js += "$('#id_no').val('" + identityCard + "');";
                        js += "$('#mobilephone').val('" + phoneNumber + "');";
                        js += "$('div#next').click(function(){myObj.fetchApplyInfo($('#realname').val(),$('#tel').val(),$('#workplace').val())});";
                        js += "$('a#next').click(function(){myObj.commitApplyInfo()});";
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
                        js += "$('div#next').click(function(){myObj.fetchApplyInfo($('#realname').val(),$('#tel').val(),$('#workplace').val())});";
                        js += "$('a#next').click(function(){myObj.commitApplyInfo()});";
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
            if (finishedApply) {
                finish();
            }
            else {
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
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
