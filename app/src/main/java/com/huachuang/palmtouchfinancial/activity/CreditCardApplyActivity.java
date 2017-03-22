package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_credit_card_apply)
public class CreditCardApplyActivity extends BaseActivity {


    public static void actionStart(Context context) {
        Intent intent = new Intent(context, CreditCardApplyActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
//        webView.getSettings().setJavaScriptEnabled(true);
//        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//        webView.getSettings().setSupportMultipleWindows(true);
//        webView.setWebViewClient(new WebViewClient());
//        webView.setWebChromeClient(new WebChromeClient());
//        webView.loadUrl("http://creditcard.ecitic.com/h5/shenqing/list.html?foot_s=0&sid=SJRSYXQ4");

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

    }

    @Event(R.id.credit_card_apply_zhongxin)
    private void zhongxinClicked(View view) {

    }

    @Event(R.id.credit_card_apply_pufa)
    private void pufaClicked(View view) {

    }

    @Event(R.id.credit_card_apply_shanghai)
    private void shanghaiClicked(View view) {

    }

    @Event(R.id.credit_card_apply_jiaotong)
    private void jiaotongClicked(View view) {

    }

    @Event(R.id.credit_card_apply_zhaoshang)
    private void zhaoshangClicked(View view) {

    }

    @Event(R.id.credit_card_apply_guangda)
    private void guangdaClicked(View view) {

    }

    @Event(R.id.credit_card_apply_pingan)
    private void pinganClicked(View view) {

    }
}
