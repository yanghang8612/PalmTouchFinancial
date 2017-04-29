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
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.util.CommonUtils;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import de.hdodenhof.circleimageview.CircleImageView;

@ContentView(R.layout.activity_my_qr_code)
public class MyQrCodeActivity extends BaseSwipeActivity {

    private static String TAG = MyQrCodeActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MyQrCodeActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.my_qr_code_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.header_image)
    private CircleImageView headerImage;

    @ViewInject(R.id.invitation_code)
    private TextView invitationCodeView;

    @ViewInject(R.id.recommend_code)
    private TextView recommendCodeView;

    @ViewInject(R.id.qr_code_image)
    private ImageView qrCodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        String qrCodeContent = "palmtouch/" + UserManager.getUserPhoneNumber() + "/";
        if (UserManager.getCurrentUser().getUserType() == 0) {
            invitationCodeView.setVisibility(View.GONE);
            recommendCodeView.setVisibility(View.VISIBLE);
            recommendCodeView.setText("推荐码:" + UserManager.getUserPhoneNumber());
        }
        else {
            qrCodeContent += UserManager.getCurrentUser().getInvitationCode();
            invitationCodeView.setText("邀请码:" + UserManager.getCurrentUser().getInvitationCode());
        }
        final String finalQrCodeContent = qrCodeContent;
        if (UserManager.getCurrentUser().getHeaderState()) {
            Glide.with(this)
                    .load(CommonUtils.getHeaderUrl())
                    .into(headerImage);
            Glide.with(this)
                    .load(CommonUtils.getHeaderUrl())
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                            qrCodeImage.setImageBitmap(CodeUtils.createImage(finalQrCodeContent, 640, 640, resource));
                        }
                    });
        }
        else {
            qrCodeImage.setImageBitmap(CodeUtils.createImage(finalQrCodeContent, 640, 640, BitmapFactory.decodeResource(getResources(), R.drawable.default_header_image)));
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
}
