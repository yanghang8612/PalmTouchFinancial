package com.huachuang.palmtouchfinancial.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.bean.BankCard;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.AddBankCardParams;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_add_bank_card)
public class AddBankCardActivity extends BaseActivity {

    private static final String TAG = AddBankCardActivity.class.getSimpleName();

    public static void actionStart(Activity activity, int requestCode) {
        Intent intent = new Intent(activity, AddBankCardActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }

    @ViewInject(R.id.add_bank_card_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.add_bank_card_owner_name_edit)
    private EditText ownerNameEdit;

    @ViewInject(R.id.add_bank_card_number_edit)
    private EditText numberEdit;

    @ViewInject(R.id.add_bank_card_bank_name_edit)
    private EditText bankNameEdit;

    @ViewInject(R.id.add_bank_card_type_edit)
    private EditText typeEdit;

    @ViewInject(R.id.add_bank_card_phone_number_edit)
    private EditText phoneNumberEdit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        numberEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String number = numberEdit.getText().toString();
                if (!hasFocus && CommonUtils.checkBankCard(number)) {
                    String[] result = CommonUtils.getCardType(AddBankCardActivity.this, number);
                    if (result != null) {
                        bankNameEdit.setText(result[0]);
                        typeEdit.setText(result[1]);
                    }
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            new MaterialDialog.Builder(this)
                    .content("确认放弃添加吗?")
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Event(R.id.add_bank_card_commit_button)
    private void commitButtonClicked(View view) {
        hideKeyboard();

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

        final String bankName = bankNameEdit.getText().toString();
        final String cardType = typeEdit.getText().toString();

        final String phoneNumber = phoneNumberEdit.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            phoneNumberEdit.setError("请输入手机号");
            return;
        }
        else if (!CommonUtils.validatePhone(phoneNumber)) {
            phoneNumberEdit.setError("请输入正确的手机号");
            return;
        }

        x.http().post(new AddBankCardParams(ownerName, number, cardType, bankName, phoneNumber), new NetCallbackAdapter(this) {
            @Override
            public void onSuccess(String result) {
                JSONObject resultJsonObject;
                try {
                    resultJsonObject = new JSONObject(result);
                    showToast(resultJsonObject.getString("Info"));
                    if (resultJsonObject.getBoolean("Status")) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                BankCard card = new BankCard();
                                card.setOwnerName(ownerName);
                                card.setCardNumber(number);
                                card.setBankName(bankName);
                                card.setCardType(cardType);
                                card.setPhoneNumber(phoneNumber);
                                Intent intent=new Intent();
                                intent.putExtra("card", card);
                                setResult(RESULT_OK, intent);
                                AddBankCardActivity.this.finish();
                            }
                        }, 1000);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
