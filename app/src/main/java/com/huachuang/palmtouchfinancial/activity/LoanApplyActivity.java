package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.ViewFlipper;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.backend.net.NetCallbackAdapter;
import com.huachuang.palmtouchfinancial.backend.net.params.ApplyLoanParams;
import com.huachuang.palmtouchfinancial.util.CommonUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.Calendar;

@ContentView(R.layout.activity_loan_apply)
public class LoanApplyActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    private static String TAG = LoanApplyActivity.class.getSimpleName();

    public static final int REQUEST_CODE_HOUSE_ADDRESS = 0;
    public static final int REQUEST_CODE_BORROWER_ADDRESS = 1;

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, LoanApplyActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.loan_apply_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.loan_apply_flipper)
    private ViewFlipper loanApplyFlipper;

    @ViewInject(R.id.loan_apply_house_address_edit)
    private EditText loanApplyHouseAddressEdit;

    @ViewInject(R.id.loan_apply_house_property_card_edit)
    private EditText loanApplyHousePropertyCardEdit;

    @ViewInject(R.id.loan_apply_house_land_sources_spinner)
    private Spinner loanApplyHouseLandSourcesSpinner;

    @ViewInject(R.id.loan_apply_house_type_spinner)
    private Spinner loanApplyHouseTypeSpinner;

    @ViewInject(R.id.loan_apply_house_build_year_edit)
    private EditText loanApplyHouseBuildYearEdit;

    @ViewInject(R.id.loan_apply_house_build_area_edit)
    private EditText loanApplyHouseBuildAreaEdit;

    @ViewInject(R.id.loan_apply_house_owned_by_others_check_box)
    private CheckBox loanApplyHouseOwnedByOthersCheckBox;

    @ViewInject(R.id.loan_apply_house_is_mortgaged_check_box)
    private CheckBox loanApplyHouseIsMortgagedCheckBox;

    @ViewInject(R.id.loan_apply_house_borrower_is_owner_check_box)
    private CheckBox loanApplyHouseBorrowerIsOwnerCheckBox;

    @ViewInject(R.id.loan_apply_house_handing_time_edit)
    private EditText loanApplyHouseHandingTimeEdit;

    @ViewInject(R.id.loan_apply_borrower_name_edit)
    private EditText loanApplyBorrowerNameEdit;

    @ViewInject(R.id.loan_apply_borrower_phone_number_edit)
    private EditText loanApplyBorrowerPhoneNumberEdit;

    @ViewInject(R.id.loan_apply_borrower_amount_edit)
    private EditText loanApplyBorrowerAmountEdit;

    @ViewInject(R.id.loan_apply_borrower_marriage_spinner)
    private Spinner loanApplyBorrowerMarriageSpinner;

    @ViewInject(R.id.loan_apply_borrower_address_edit)
    private EditText loanApplyBorrowerAddressEdit;

    @ViewInject(R.id.loan_apply_borrower_detailed_address_edit)
    private EditText loanApplyBorrowerDetailedAddressEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        loanApplyFlipper.setInAnimation(this, R.anim.push_left_in);
        loanApplyHouseAddressEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DistrictActivity.actionStart(LoanApplyActivity.this, REQUEST_CODE_HOUSE_ADDRESS);
                }
            }
        });
        loanApplyHouseHandingTimeEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    Calendar now = Calendar.getInstance();
                    DatePickerDialog dpd = DatePickerDialog.newInstance(
                            LoanApplyActivity.this,
                            now.get(Calendar.YEAR),
                            now.get(Calendar.MONTH),
                            now.get(Calendar.DAY_OF_MONTH)
                    );
                    dpd.show(getFragmentManager(), "选择交房日期");
                }
            }
        });
        loanApplyBorrowerAddressEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DistrictActivity.actionStart(LoanApplyActivity.this, REQUEST_CODE_BORROWER_ADDRESS);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (loanApplyFlipper.getDisplayedChild() != 1) {
                new MaterialDialog.Builder(this)
                        .content("确认取消申请吗?")
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
            if (data != null) {
                Bundle bundle = data.getExtras();
                if (requestCode == REQUEST_CODE_HOUSE_ADDRESS) {
                    loanApplyHouseAddressEdit.setText(bundle.getString("district"));
                }
                if (requestCode == REQUEST_CODE_BORROWER_ADDRESS) {
                    loanApplyBorrowerAddressEdit.setText(bundle.getString("district"));
                }
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        loanApplyHouseHandingTimeEdit.setText(new StringBuilder()
                .append(year)
                .append("-")
                .append((monthOfYear + 1) < 10 ? "0"
                        + (monthOfYear + 1) : (monthOfYear + 1))
                .append("-")
                .append((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth));
    }

    @Event(R.id.loan_apply_finish_button)
    private void loanApplyFinishButton(View view) {
        hideKeyboard();

        String houseAddress = loanApplyHouseAddressEdit.getText().toString();
        if (TextUtils.isEmpty(houseAddress)) {
            loanApplyHouseAddressEdit.setError("请输入抵押房产地址");
            return;
        }

        String housePropertyCard = loanApplyHousePropertyCardEdit.getText().toString();
        if (TextUtils.isEmpty(housePropertyCard)) {
            loanApplyHousePropertyCardEdit.setError("请输入房产证号");
            return;
        }

        String buildYear = loanApplyHouseBuildYearEdit.getText().toString();
        if (TextUtils.isEmpty(buildYear)) {
            loanApplyHouseBuildYearEdit.setError("请输入建筑年代");
            return;
        }
        else if (!CommonUtils.validateYear(buildYear)) {
            loanApplyHouseBuildYearEdit.setError("请输入4位正确的年份");
            return;
        }

        String buildArea = loanApplyHouseBuildAreaEdit.getText().toString();
        if (TextUtils.isEmpty(buildArea)) {
            loanApplyHouseBuildAreaEdit.setError("请输入建筑面积");
            return;
        }
        else if (!CommonUtils.validateNumber(buildArea)) {
            loanApplyHouseBuildAreaEdit.setError("请输入正确的建筑面积");
        }

        String handingTime = loanApplyHouseHandingTimeEdit.getText().toString();
        if (TextUtils.isEmpty(handingTime)) {
            loanApplyHouseHandingTimeEdit.setError("请选择交房日期");
            return;
        }

        String borrowerName = loanApplyBorrowerNameEdit.getText().toString();
        if (TextUtils.isEmpty(borrowerName)) {
            loanApplyBorrowerNameEdit.setError("请输入借款人姓名");
            return;
        }
        else if (!CommonUtils.validateChineseName(borrowerName)) {
            loanApplyBorrowerNameEdit.setError("请输入正确的中文名");
        }

        String borrowerPhoneNumber = loanApplyBorrowerPhoneNumberEdit.getText().toString();
        if (TextUtils.isEmpty(borrowerPhoneNumber)) {
            loanApplyBorrowerPhoneNumberEdit.setError("请输入借款人手机号码");
            return;
        }
        else if (!CommonUtils.validatePhone(borrowerPhoneNumber)) {
            loanApplyBorrowerPhoneNumberEdit.setError("请输入正确的手机号码");
            return;
        }

        String borrowerAmount = loanApplyBorrowerAmountEdit.getText().toString();
        if (TextUtils.isEmpty(borrowerAmount)) {
            loanApplyBorrowerAmountEdit.setError("请输入借款金额");
            return;
        }
        else if (!CommonUtils.validateNumber(borrowerAmount)) {
            loanApplyBorrowerAmountEdit.setError("请输入正确的借款金额");
            return;
        }

        String borrowerAddress = loanApplyBorrowerAddressEdit.getText().toString();
        if (TextUtils.isEmpty(borrowerAddress)) {
            loanApplyBorrowerAddressEdit.setError("请选择住宅所在地");
            return;
        }

        String borrowerDetailedAddress = loanApplyBorrowerDetailedAddressEdit.getText().toString();
        if (TextUtils.isEmpty(borrowerDetailedAddress)) {
            loanApplyBorrowerDetailedAddressEdit.setError("请输入详细地址");
            return;
        }

        ApplyLoanParams params = new ApplyLoanParams(
                UserManager.getUserID(),
                loanApplyHouseAddressEdit.getText().toString(),
                loanApplyHousePropertyCardEdit.getText().toString(),
                (String) loanApplyHouseLandSourcesSpinner.getSelectedItem(),
                (String) loanApplyHouseTypeSpinner.getSelectedItem(),
                loanApplyHouseBuildYearEdit.getText().toString(),
                loanApplyHouseBuildAreaEdit.getText().toString(),
                loanApplyHouseOwnedByOthersCheckBox.isChecked(),
                loanApplyHouseIsMortgagedCheckBox.isChecked(),
                loanApplyHouseBorrowerIsOwnerCheckBox.isChecked(),
                loanApplyHouseHandingTimeEdit.getText().toString(),
                borrowerName,
                borrowerPhoneNumber,
                borrowerAmount,
                (String) loanApplyBorrowerMarriageSpinner.getSelectedItem(),
                borrowerAddress,
                borrowerDetailedAddress);

        x.http().post(params, new NetCallbackAdapter(this) {
            @Override
            public void onSuccess(String result) {

                try {
                    JSONObject resultJSONObject = new JSONObject(result);
                    if (resultJSONObject.getBoolean("Status")) {
                        Log.d(TAG, resultJSONObject.getString("Info"));
                        loanApplyFlipper.setDisplayedChild(1);
                    }
                    else {
                        showToast(resultJSONObject.getString("Info"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
