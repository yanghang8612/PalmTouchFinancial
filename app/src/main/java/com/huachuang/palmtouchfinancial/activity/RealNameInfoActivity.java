package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.bean.UserCertificationInfo;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.CreateCertificationInfoParams;
import com.huachuang.palmtouchfinancial.backend.net.params.UpdateCertificationInfoParams;
import com.huachuang.palmtouchfinancial.util.CommonUtils;
import com.huachuang.palmtouchfinancial.util.IDCard;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_real_name_info)
public class RealNameInfoActivity extends BaseActivity {

    public static final String TAG = RealNameInfoActivity.class.getSimpleName();

    public static final int REQUEST_CODE_DISTRICT = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, RealNameInfoActivity.class);
        context.startActivity(intent);
    }

    private short currentFlipper = 0;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (!UserManager.getCurrentUser().isCertificationState()) {
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

            final String spell = spellEdit.getText().toString();
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

            RequestParams params;
            if (UserManager.getCurrentUser().isCertificationState()) {
                params = new UpdateCertificationInfoParams(name, spell, sex, identityCard, address);
            }
            else {
                params = new CreateCertificationInfoParams(name, spell, sex, identityCard, address);
            }
            x.http().post(params, new NetCallbackAdapter(this) {
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
        }
    }
}
