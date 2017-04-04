package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.UploadHeaderParams;
import com.huachuang.palmtouchfinancial.loader.HeaderImageLoader;
import com.huachuang.palmtouchfinancial.util.CommonUtils;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

@ContentView(R.layout.activity_personal_info)
public class PersonalInfoActivity extends BaseActivity {

    public static final String TAG = PersonalInfoActivity.class.getSimpleName();

    public static int REQUEST_CODE_IMAGE = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, PersonalInfoActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.personal_info_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.person_header_image)
    private CircleImageView headerImageView;

    @ViewInject(R.id.person_phone_number)
    private TextView phoneNumberView;

    @ViewInject(R.id.person_user_type)
    private TextView userTypeView;

    @ViewInject(R.id.person_register_time)
    private TextView registerTimeView;

    @ViewInject(R.id.person_last_login_time)
    private TextView lastLoginTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        Glide.with(this).load(CommonUtils.getHeaderUrl()).into(headerImageView);
        phoneNumberView.setText(UserManager.getUserPhoneNumber());
        if (UserManager.getCurrentUser().getUserType() == 0) {
            if (UserManager.getCurrentUser().isVip()) {
                userTypeView.setText("VIP用户");
            }
            else {
                userTypeView.setText("普通用户");
            }
        }
        else if (UserManager.getCurrentUser().getUserType() == 1) {
            userTypeView.setText("一级代理商");
        }
        else if (UserManager.getCurrentUser().getUserType() == 2) {
            userTypeView.setText("二级代理商");
        }
        else if (UserManager.getCurrentUser().getUserType() == 3) {
            userTypeView.setText("三级代理商");
        }
        registerTimeView.setText(new SimpleDateFormat("yyyy-MM-dd").format(UserManager.getCurrentUser().getRegisterTime()));
        lastLoginTime.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(UserManager.getCurrentUser().getLastLoginTime()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Event(R.id.person_header)
    private void personHeaderClicked(View view) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new HeaderImageLoader());   //设置图片加载器
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(320);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(320);//保存文件的高度。单位像素
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, REQUEST_CODE_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REQUEST_CODE_IMAGE) {
                List<ImageItem> images = (List<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
                x.http().post(new UploadHeaderParams(new File(image.path)), new NetCallbackAdapter() {
                    @Override
                    public void onSuccess(String result) {
                        if (result.equals("true")) {
                            Toast.makeText(PersonalInfoActivity.this, "设置头像成功", Toast.LENGTH_SHORT).show();
                            Glide.with(PersonalInfoActivity.this).load(CommonUtils.getHeaderUrl()).into(headerImageView);
                        }
                        else {
                            Toast.makeText(PersonalInfoActivity.this, "设置头像失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "未选择头像", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
