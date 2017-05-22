package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.flipboard.bottomsheet.BottomSheetLayout;
import com.flipboard.bottomsheet.commons.MenuSheetView;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.loader.HeaderImageLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.lzy.imagepicker.view.CropImageView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.List;

@ContentView(R.layout.activity_upload_license)
public class UploadLicenseActivity extends BaseActivity {

    private static final String TAG = RegisterVIPActivity.class.getSimpleName();

    public static final int REQUEST_CODE_FRONT_IMAGE = 0;
    public static final int REQUEST_CODE_BACK_IMAGE = 1;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, UploadLicenseActivity.class);
        context.startActivity(intent);
    }

    private boolean licenseFrontImageState = false;
    private boolean licenseBackImageState = false;
    private String licenseFrontImagePath = null;
    private String licenseBackImagePath = null;

    @ViewInject(R.id.upload_license_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.upload_license_bottomsheet)
    private BottomSheetLayout bottomSheet;

    @ViewInject(R.id.license_front_image_picker)
    private ImageView licenseFrontImagePicker;

    @ViewInject(R.id.license_back_image_picker)
    private ImageView licenseBackImagePicker;

    @ViewInject(R.id.pay_way_button)
    private Button payWayButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        payWayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!licenseFrontImageState || !licenseBackImageState) {
                    showToast("请拍照营业执照正反面");
                }
                MenuSheetView menuSheetView =
                        new MenuSheetView(UploadLicenseActivity.this, MenuSheetView.MenuType.LIST, "请选择支付方式", new MenuSheetView.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                if (bottomSheet.isSheetShowing()) {
                                    bottomSheet.dismissSheet();
                                }
                                return true;
                            }
                        });
                menuSheetView.inflateMenu(R.menu.pay_way);
                bottomSheet.showWithSheetView(menuSheetView);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null) {
                List<ImageItem> images = (List<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                ImageItem image = images.get(0);
                switch (requestCode) {
                    case REQUEST_CODE_FRONT_IMAGE:
                        licenseFrontImageState = true;
                        licenseFrontImagePath = image.path;
                        licenseFrontImagePicker.setImageBitmap(BitmapFactory.decodeFile(image.path));
                        break;
                    case REQUEST_CODE_BACK_IMAGE:
                        licenseBackImageState = true;
                        licenseBackImagePath = image.path;
                        licenseBackImagePicker.setImageBitmap(BitmapFactory.decodeFile(image.path));
                        break;
                }
            }
        }
    }

    @Event(R.id.license_front_image_picker)
    private void debitCardFrontImageClicked(View view) {
        startImagePicker(REQUEST_CODE_FRONT_IMAGE);
    }

    @Event(R.id.license_back_image_picker)
    private void debitCardBackImageClicked(View view) {
        startImagePicker(REQUEST_CODE_BACK_IMAGE);
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
}
