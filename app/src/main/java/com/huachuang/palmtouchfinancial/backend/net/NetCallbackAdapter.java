package com.huachuang.palmtouchfinancial.backend.net;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import org.xutils.common.Callback;

/**
 * Created by Asuka on 2017/3/30.
 */

public abstract class NetCallbackAdapter implements Callback.ProgressCallback<String> {

    //private SpinKitView progressBar;
    private Context context;
    private ProgressDialog progressBar;
    private boolean dismissWhenFinished;

    protected NetCallbackAdapter(Context context) {
        this.context = context;
        this.progressBar = new ProgressDialog(context);
        this.dismissWhenFinished = true;
        progressBar.setMessage("加载中...");
        progressBar.setCancelable(false);
    }

    protected NetCallbackAdapter(Context context, ProgressDialog progressBar) {
        this(context, progressBar, true);
    }

    protected NetCallbackAdapter(Context context, ProgressDialog progressBar,  boolean dismissWhenFinished) {
        this.context = context;
        this.progressBar = progressBar;
        this.dismissWhenFinished = dismissWhenFinished;
    }

    @Override
    public void onWaiting() {

    }

    @Override
    public void onStarted() {
        if (!progressBar.isShowing()) {
            progressBar.show();
        }
    }

    @Override
    public void onLoading(long total, long current, boolean isDownloading) {

    }

    @Override
    public abstract void onSuccess(String result);

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        Toast.makeText(context, "网络连接超时", Toast.LENGTH_SHORT).show();
        ex.printStackTrace();
    }

    @Override
    public void onCancelled(CancelledException cex) {
        cex.printStackTrace();
    }

    @Override
    public void onFinished() {
        if (progressBar.isShowing() && dismissWhenFinished) {
            progressBar.dismiss();
        }
    }
}
