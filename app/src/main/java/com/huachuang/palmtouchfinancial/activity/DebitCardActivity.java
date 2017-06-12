package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.huachuang.palmtouchfinancial.backend.bean.UserDebitCard;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.CreateDebitCardParams;
import com.huachuang.palmtouchfinancial.backend.net.params.UpdateDebitCardParams;
import com.huachuang.palmtouchfinancial.backend.net.params.UploadDebitCardParams;
import com.huachuang.palmtouchfinancial.loader.HeaderImageLoader;
import com.huachuang.palmtouchfinancial.util.CommonUtils;
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

@ContentView(R.layout.activity_debit_card)
public class DebitCardActivity extends BaseSwipeActivity {

    private static final String TAG = DebitCardActivity.class.getSimpleName();

    public static final int REQUEST_CODE_DISTRICT = 0;
    public static final int REQUEST_CODE_FRONT_IMAGE = 1;
    public static final int REQUEST_CODE_BACK_IMAGE = 2;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DebitCardActivity.class);
        context.startActivity(intent);
    }

    private short currentFlipper = 0;
    private boolean debitCardFrontImageState = false;
    private boolean debitCardBackImageState = false;
    private String debitCardFrontImagePath = null;
    private String debitCardBackImagePath = null;

    @ViewInject(R.id.debit_card_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.debit_card_flipper)
    private ViewFlipper debitCardFlipper;

    @ViewInject(R.id.debit_card_button)
    private Button debitCardButton;

    @ViewInject(R.id.debit_card_owner_name)
    private TextView ownerNameView;

    @ViewInject(R.id.debit_card_number)
    private TextView numberView;

    @ViewInject(R.id.debit_card_bank)
    private TextView bankView;

    @ViewInject(R.id.debit_card_type)
    private TextView typeView;

    @ViewInject(R.id.debit_card_province)
    private TextView provinceView;

    @ViewInject(R.id.debit_card_front_image)
    private ImageView debitCardFrontImage;

    @ViewInject(R.id.debit_card_back_image)
    private ImageView debitCardBackImage;

    @ViewInject(R.id.debit_card_owner_name_edit)
    private EditText ownerNameEdit;

    @ViewInject(R.id.debit_card_number_edit)
    private EditText numberEdit;

    @ViewInject(R.id.debit_card_head_office_edit)
    private EditText headOfficeEdit;

    @ViewInject(R.id.debit_card_type_edit)
    private EditText typeEdit;

    @ViewInject(R.id.debit_card_branch_edit)
    private EditText branchEdit;

    @ViewInject(R.id.debit_card_province_edit)
    private EditText provinceEdit;

    @ViewInject(R.id.debit_card_front_image_picker)
    private ImageView debitCardFrontImagePicker;

    @ViewInject(R.id.debit_card_back_image_picker)
    private ImageView debitCardBackImagePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (!UserManager.getCurrentUser().getDebitCardState()) {
            debitCardFlipper.setDisplayedChild(1);
            debitCardButton.setText("保存结算卡信息");
            currentFlipper = 1;
        }
        else {
            ownerNameView.setText(UserManager.getDebitCardInfo().getOwnerName());
            numberView.setText(CommonUtils.mosaicBankCard(UserManager.getDebitCardInfo().getCardNumber()));
            bankView.setText(UserManager.getDebitCardInfo().getHeadOffice() + UserManager.getDebitCardInfo().getBranch());
            typeView.setText(UserManager.getDebitCardInfo().getCardType());
            provinceView.setText(UserManager.getDebitCardInfo().getProvince());
            refreshPreviewImage();
        }
        numberEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String number = numberEdit.getText().toString();
                if (!hasFocus && CommonUtils.checkBankCard(number)) {
                    String[] result = CommonUtils.getCardType(DebitCardActivity.this, number);
                    if (result != null) {
                        headOfficeEdit.setText(result[0]);
                        typeEdit.setText(result[1]);
                    }
                }
            }
        });
        provinceEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)  {
                    DistrictActivity.actionStart(DebitCardActivity.this, REQUEST_CODE_DISTRICT);
                }
            }
        });
        new MaterialDialog.Builder(DebitCardActivity.this)
                .content("提示：移动支付功能需使用民生银行结算卡")
                .contentColorRes(R.color.black)
                .positiveText("确认")
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                })
                .show();
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
                                dialog.dismiss();
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
                provinceEdit.setText(bundle.getString("district"));
            }
        }
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                List<ImageItem> images = (List<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
                switch (requestCode) {
                    case REQUEST_CODE_FRONT_IMAGE:
                        debitCardFrontImageState = true;
                        debitCardFrontImagePath = image.path;
                        debitCardFrontImagePicker.setImageBitmap(BitmapFactory.decodeFile(image.path));
                        break;
                    case REQUEST_CODE_BACK_IMAGE:
                        debitCardBackImageState = true;
                        debitCardBackImagePath = image.path;
                        debitCardBackImagePicker.setImageBitmap(BitmapFactory.decodeFile(image.path));
                        break;
                }
            }
        }
    }

    @Event(R.id.debit_card_front_image_picker)
    private void debitCardFrontImageClicked(View view) {
        startImagePicker(REQUEST_CODE_FRONT_IMAGE);
    }

    @Event(R.id.debit_card_back_image_picker)
    private void debitCardBackImageClicked(View view) {
        startImagePicker(REQUEST_CODE_BACK_IMAGE);
    }

    @Event(R.id.debit_card_button)
    private void debitCardButtonClicked(View view) {
        if (currentFlipper == 0) {
            ownerNameEdit.setText(UserManager.getDebitCardInfo().getOwnerName());
            numberEdit.setText(UserManager.getDebitCardInfo().getCardNumber());
            headOfficeEdit.setText(UserManager.getDebitCardInfo().getHeadOffice());
            typeEdit.setText(UserManager.getDebitCardInfo().getCardType());
            branchEdit.setText(UserManager.getDebitCardInfo().getBranch());
            provinceEdit.setText(UserManager.getDebitCardInfo().getProvince());
            debitCardFlipper.setInAnimation(this, R.anim.push_left_in);
            debitCardFlipper.setDisplayedChild(1);
            ((Button) view).setText("保存结算卡信息");
            currentFlipper = 1;
        }
        else {
            final String ownerName = ownerNameEdit.getText().toString();
            if (TextUtils.isEmpty(ownerName)) {
                ownerNameEdit.setError("请输入结算户主");
                return;
            }
            else if (!CommonUtils.validateChineseName(ownerName)) {
                ownerNameEdit.setError("请输入正确的中文名");
                return;
            }

            final String number = numberEdit.getText().toString();
            if (TextUtils.isEmpty(number)) {
                numberEdit.setError("请输入结算卡号");
                return;
            }
            else if (!CommonUtils.checkBankCard(number)) {
                numberEdit.setError("请输入正确的银行卡号");
                return;
            }

            final String headOffice = headOfficeEdit.getText().toString();
            final String cardType = typeEdit.getText().toString();
            final String branch = branchEdit.getText().toString();
            final String province = provinceEdit.getText().toString();

            RequestParams infoParams;
            if (UserManager.getCurrentUser().getDebitCardState()) {
                infoParams = new UpdateDebitCardParams(ownerName, number, cardType, headOffice, branch, province);
            }
            else {
                infoParams = new CreateDebitCardParams(ownerName, number, cardType, headOffice, branch, province);
                if (!debitCardFrontImageState || !debitCardBackImageState) {
                    showToast("请拍照结算卡正反面");
                }
            }
            x.http().post(infoParams, new NetCallbackAdapter(this) {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject resultJsonObject = new JSONObject(result);
                        if (resultJsonObject.getBoolean("Status")) {
                            UserManager.setDebitCardInfo(
                                    JSON.parseObject(resultJsonObject.getString("DebitCard"), UserDebitCard.class));
                            UserManager.getCurrentUser().setDebitCardState(true);

                            ownerNameView.setText(ownerName);
                            numberView.setText(CommonUtils.mosaicBankCard(number));
                            bankView.setText(headOffice + branch);
                            typeView.setText(cardType);
                            provinceView.setText(province);
                            refreshPreviewImage();

                            debitCardFlipper.setInAnimation(DebitCardActivity.this, R.anim.push_right_in);
                            debitCardFlipper.setDisplayedChild(0);
                            debitCardButton.setText("修改结算卡信息");
                            currentFlipper = 0;
                        }
                        showToast(resultJsonObject.getString("Info"));
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
            RequestParams imageParams = new UploadDebitCardParams(
                    debitCardFrontImagePath,
                    debitCardBackImagePath);
            x.http().post(imageParams, new NetCallbackAdapter(this) {
                @Override
                public void onSuccess(String result) {
                    debitCardFrontImagePath = null;
                    debitCardBackImagePath = null;
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
        imagePicker.setFocusWidth(1024);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(650);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1024);    //保存文件的宽度。单位像素
        imagePicker.setOutPutY(650);    //保存文件的高度。单位像素
        Intent intent = new Intent(this, ImageGridActivity.class);
        startActivityForResult(intent, code);
    }

    private void refreshPreviewImage() {
        Glide.with(this)
                .load(GlobalParams.SERVER_URL_HEAD + "/preview/" + UserManager.getUserPhoneNumber() + "/debit_card/front.jpg")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(debitCardFrontImage);
        Glide.with(this)
                .load(GlobalParams.SERVER_URL_HEAD + "/preview/" + UserManager.getUserPhoneNumber() + "/debit_card/back.jpg")
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(debitCardBackImage);
    }
}
