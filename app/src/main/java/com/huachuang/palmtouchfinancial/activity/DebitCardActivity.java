package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.alibaba.fastjson.JSON;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.bean.UserCertificationInfo;
import com.huachuang.palmtouchfinancial.backend.bean.UserDebitCard;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.CreateDebitCardParams;
import com.huachuang.palmtouchfinancial.backend.net.params.UpdateDebitCardParams;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_debit_card)
public class DebitCardActivity extends BaseActivity {

    private static final String TAG = DebitCardActivity.class.getSimpleName();

    public static final int REQUEST_CODE_DISTRICT = 0;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, DebitCardActivity.class);
        context.startActivity(intent);
    }

    private short currentFlipper = 0;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        if (!UserManager.getCurrentUser().isDebitCardState()) {
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
                provinceEdit.setText(bundle.getString("district"));
            }
        }
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

            RequestParams params;
            if (UserManager.getCurrentUser().isDebitCardState()) {
                params = new UpdateDebitCardParams(ownerName, number, cardType, headOffice, branch, province);
            }
            else {
                params = new CreateDebitCardParams(ownerName, number, cardType, headOffice, branch, province);
            }

            x.http().post(params, new NetCallbackAdapter(this) {
                @Override
                public void onSuccess(String result) {
                    JSONObject resultJsonObject;
                    try {
                        resultJsonObject = new JSONObject(result);
                        if (resultJsonObject.getBoolean("Status")) {
                            UserManager.setDebitCardInfo(
                                    JSON.parseObject(resultJsonObject.getString("DebitCard"), UserDebitCard.class));
                            UserManager.getCurrentUser().setDebitCardState(true);

                            ownerNameView.setText(ownerName);
                            numberView.setText(CommonUtils.mosaicBankCard(number));
                            bankView.setText(headOffice + branch);
                            typeView.setText(cardType);
                            provinceView.setText(province);

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
        }
    }
}
