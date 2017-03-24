package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huachuang.palmtouchfinancial.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_specific_apply)
public class SpecificApplyActivity extends BaseActivity {

    public static void actionStart(Context context, String url) {
        Intent intent = new Intent(context, SpecificApplyActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @ViewInject(R.id.apply_web_view)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(getIntent().getStringExtra("url"));
    }
}
