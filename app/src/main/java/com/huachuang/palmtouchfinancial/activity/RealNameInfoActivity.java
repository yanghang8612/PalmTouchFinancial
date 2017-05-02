package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.bean.UserCertificationInfo;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.CreateCertificationInfoParams;
import com.huachuang.palmtouchfinancial.backend.net.params.UpdateCertificationInfoParams;
import com.huachuang.palmtouchfinancial.backend.net.params.UploadIdentifyCardParams;
import com.huachuang.palmtouchfinancial.loader.HeaderImageLoader;
import com.huachuang.palmtouchfinancial.util.CommonUtils;
import com.huachuang.palmtouchfinancial.util.IDCard;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

@ContentView(R.layout.activity_real_name_info)
public class RealNameInfoActivity extends BaseSwipeActivity {

    private static final String TAG = RealNameInfoActivity.class.getSimpleName();

    public static final int REQUEST_CODE_DISTRICT = 0;
    public static final int REQUEST_CODE_FRONT_IMAGE = 1;
    public static final int REQUEST_CODE_BACK_IMAGE = 2;
    public static final int REQUEST_CODE_HANDING_IMAGE = 3;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RealNameInfoActivity.class);
        context.startActivity(intent);
    }

    private short currentFlipper = 0;
    private boolean identifyCardFrontImageState = false;
    private boolean identifyCardBackImageState = false;
    private boolean identifyCardHandingImageState = false;
    private String identifyCardFrontImagePath = null;
    private String identifyCardBackImagePath = null;
    private String identifyCardHandingImagePath = null;

    @ViewInject(R.id.real_name_info_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.real_name_info_flipper)
    private ViewFlipper realNameInfoFlipper;

    @ViewInject(R.id.real_name_info_button)
    private Button realNameInfoButton;

    @ViewInject(R.id.real_name_info_name_view)
    private TextView nameView;

    @ViewInject(R.id.real_name_info_spell_view)
    private TextView spellView;

    @ViewInject(R.id.real_name_info_sex_view)
    private TextView sexView;

    @ViewInject(R.id.real_name_info_identify_card_view)
    private TextView identityCardView;

    @ViewInject(R.id.real_name_info_address_view)
    private TextView addressView;

    @ViewInject(R.id.identify_card_front_image)
    private ImageView identifyCardFrontImage;

    @ViewInject(R.id.identify_card_back_image)
    private ImageView identifyCardBackImage;

    @ViewInject(R.id.identify_card_handing_image)
    private ImageView identifyCardHandingImage;

    @ViewInject(R.id.real_name_info_name_edit)
    private EditText nameEdit;

    @ViewInject(R.id.real_name_info_spell_edit)
    private EditText spellEdit;

    @ViewInject(R.id.real_name_info_sex_spinner)
    private Spinner sexSpinner;

    @ViewInject(R.id.real_name_info_identify_card_edit)
    private EditText identityCardEdit;

    @ViewInject(R.id.real_name_info_address_edit)
    private TextView addressEdit;

    @ViewInject(R.id.identify_card_front_image_picker)
    private ImageView identifyCardFrontImagePicker;

    @ViewInject(R.id.identify_card_back_image_picker)
    private ImageView identifyCardBackImagePicker;

    @ViewInject(R.id.identify_card_handing_image_picker)
    private ImageView identifyCardHandingImagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (!UserManager.getCurrentUser().getCertificationState()) {
            realNameInfoFlipper.setDisplayedChild(1);
            realNameInfoButton.setText("保存认证信息");
            currentFlipper = 1;
        }
        else {
            nameView.setText(UserManager.getCertificationInfo().getUserName());
            spellView.setText(UserManager.getCertificationInfo().getUserSpell());
            sexView.setText((UserManager.getCertificationInfo().getUserSex() == '0') ? "男" : "女");
            identityCardView.setText(CommonUtils.mosaicIdentityCard(UserManager.getCertificationInfo().getUserIdentityCard()));
            addressView.setText(UserManager.getCertificationInfo().getUserAddress());
            refreshPreviewImage();
        }
        addressEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)  {
                    DistrictActivity.actionStart(RealNameInfoActivity.this, REQUEST_CODE_DISTRICT);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (currentFlipper == 1) {
                new MaterialDialog.Builder(this)
                        .content("确认不保存退出吗?")
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
            else {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null && requestCode == REQUEST_CODE_DISTRICT) {
                Bundle bundle = data.getExtras();
                addressEdit.setText(bundle.getString("district"));
            }
        }
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                List<ImageItem> images = (List<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
                switch (requestCode) {
                    case REQUEST_CODE_FRONT_IMAGE:
                        identifyCardFrontImageState = true;
                        identifyCardFrontImagePath = image.path;
                        identifyCardFrontImagePicker.setImageBitmap(BitmapFactory.decodeFile(image.path));
                        break;
                    case REQUEST_CODE_BACK_IMAGE:
                        identifyCardBackImageState = true;
                        identifyCardBackImagePath = image.path;
                        identifyCardBackImagePicker.setImageBitmap(BitmapFactory.decodeFile(image.path));
                        break;
                    case REQUEST_CODE_HANDING_IMAGE:
                        identifyCardHandingImageState = true;
                        identifyCardHandingImagePath = image.path;
                        identifyCardHandingImagePicker.setImageBitmap(BitmapFactory.decodeFile(image.path));
                        break;
                }
            }
        }
    }

    @Event(R.id.identify_card_front_image_picker)
    private void identifyCardFrontImageClicked(View view) {
        startImagePicker(REQUEST_CODE_FRONT_IMAGE);
    }

    @Event(R.id.identify_card_back_image_picker)
    private void identifyCardBackImageClicked(View view) {
        startImagePicker(REQUEST_CODE_BACK_IMAGE);
    }

    @Event(R.id.identify_card_handing_image_picker)
    private void identifyCardHandingImageClicked(View view) {
        startImagePicker(REQUEST_CODE_HANDING_IMAGE);
    }

    @Event(R.id.real_name_info_button)
    private void realNameInfoButtonClicked(View view) {
        if (currentFlipper == 0) {
            nameEdit.setText(UserManager.getCertificationInfo().getUserName());
            spellEdit.setText(UserManager.getCertificationInfo().getUserSpell());
            sexSpinner.setSelection(UserManager.getCertificationInfo().getUserSex() - '0');
            identityCardEdit.setText(UserManager.getCertificationInfo().getUserIdentityCard());
            addressEdit.setText(UserManager.getCertificationInfo().getUserAddress());
            realNameInfoFlipper.setInAnimation(this, R.anim.push_left_in);
            realNameInfoFlipper.setDisplayedChild(1);
            realNameInfoButton.setText("保存认证信息");
            currentFlipper = 1;
        }
        else {
            final String name = nameEdit.getText().toString();
            if (TextUtils.isEmpty(name)) {
                nameEdit.setError("请输入姓名");
                return;
            }
            else if (!CommonUtils.validateChineseName(name)) {
                nameEdit.setError("请输入正确的中文名");
                return;
            }

            final String spell = spellEdit.getText().toString().toUpperCase();
            if (TextUtils.isEmpty(spell)) {
                spellEdit.setError("请输入姓名的拼音（大写）");
                return;
            }

            char sex = (sexSpinner.getSelectedItemId() == 0) ? '0' : '1';

            final String identityCard = identityCardEdit.getText().toString();
            if (TextUtils.isEmpty(identityCard)) {
                identityCardEdit.setError("请输入身份证号");
                return;
            }
            else if (!new IDCard().verify(identityCard)) {
                identityCardEdit.setError("请输入正确的身份证号");
                return;
            }

            final String address = addressEdit.getText().toString();
            if (TextUtils.isEmpty(address)) {
                addressEdit.setError("请输入详细地址");
                return;
            }

            RequestParams infoParams;
            if (UserManager.getCurrentUser().getCertificationState()) {
                infoParams = new UpdateCertificationInfoParams(name, spell, sex, identityCard, address);
            }
            else {
                infoParams = new CreateCertificationInfoParams(name, spell, sex, identityCard, address);
                if (!identifyCardFrontImageState || !identifyCardBackImageState || !identifyCardHandingImageState) {
                    showToast("请拍照身份证正反面及手持证件照");
                    return;
                }
            }
            x.http().post(infoParams, new NetCallbackAdapter(this) {
                @Override
                public void onSuccess(String result) {
                    JSONObject resultJsonObject;
                    try {
                        resultJsonObject = new JSONObject(result);
                        if (resultJsonObject.getBoolean("Status")) {
                            UserManager.setCertificationInfo(
                                    JSON.parseObject(resultJsonObject.getString("CertificationInfo"), UserCertificationInfo.class));
                            UserManager.getCurrentUser().setCertificationState(true);

                            nameView.setText(name);
                            spellView.setText(spell);
                            sexView.setText((sexSpinner.getSelectedItemId() == 0) ? "男" : "女");
                            identityCardView.setText(CommonUtils.mosaicIdentityCard(identityCard));
                            addressView.setText(address);
                            refreshPreviewImage();

                            realNameInfoFlipper.setInAnimation(RealNameInfoActivity.this, R.anim.push_right_in);
                            realNameInfoFlipper.setDisplayedChild(0);
                            realNameInfoButton.setText("修改认证信息");
                            currentFlipper = 0;
                        }
                        showToast(resultJsonObject.getString("Info"));
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            RequestParams imageParams = new UploadIdentifyCardParams(
                    identifyCardFrontImagePath,
                    identifyCardBackImagePath,
                    identifyCardHandingImagePath
            );
            x.http().post(imageParams, new NetCallbackAdapter(this, false) {
                @Override
                public void onSuccess(String result) {
                    identifyCardFrontImagePath = null;
                    identifyCardBackImagePath = null;
                    identifyCardHandingImagePath = null;
                }
            });
        }
    }

    private void startImagePicker(int code) {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new HeaderImageLoader());   //设置图片加载器
        imagePicker.setMultiMode(false);
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1024);    //保存文件的宽度。单位像素
        imagePicker.setOutPutY(1024);    //保存文件的高度。单位像素
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, code);
    }

    private void refreshPreviewImage() {
        Glide.with(this)
                .load(GlobalParams.SERVER_URL_HEAD + "/preview/" + UserManager.getUserPhoneNumber() + "/identify_card/front.jpg")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(identifyCardFrontImage);
        Glide.with(this)
                .load(GlobalParams.SERVER_URL_HEAD + "/preview/" + UserManager.getUserPhoneNumber() + "/identify_card/back.jpg")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(identifyCardBackImage);
        Glide.with(this)
                .load(GlobalParams.SERVER_URL_HEAD + "/preview/" + UserManager.getUserPhoneNumber() + "/identify_card/handing.jpg")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(identifyCardHandingImage);
    }
}
