package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.util.CommonUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Asuka on 2017/4/7.
 */

@ContentView(R.layout.activity_share_qr_code)
public class ShareQrCodeActivity extends BaseSwipeActivity {

    private static String TAG = MyQrCodeActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ShareQrCodeActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.share_qr_code_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.qr_code_image)
    private ImageView qrCodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        String qrCodeContent = GlobalParams.SERVER_URL_HEAD + "/register_step_one.html?recommenderID=" + UserManager.getUserPhoneNumber();
        qrCodeImage.setImageBitmap(CodeUtils.createImage(qrCodeContent, 640, 640, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher)));
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
