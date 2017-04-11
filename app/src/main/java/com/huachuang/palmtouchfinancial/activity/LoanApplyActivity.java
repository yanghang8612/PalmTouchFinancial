package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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
import com.huachuang.palmtouchfinancial.util.CommonUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

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

    @ViewInject(R.id.loan_apply_scrollview)
    private ScrollView scrollView;

    @ViewInject(R.id.loan_apply_flipper)
    private ViewFlipper loadApplyFlipper;

    @ViewInject(R.id.loan_apply_house_address_edit)
    private EditText loadApplyHouseAddressEdit;

    @ViewInject(R.id.loan_apply_house_property_card_edit)
    private EditText loadApplyHousePropertyCardEdit;

    @ViewInject(R.id.loan_apply_house_land_sources_spinner)
    private Spinner loadApplyHouseLandSourcesSpinner;

    @ViewInject(R.id.loan_apply_house_type_spinner)
    private EditText loadApplyHouseTypeSpinner;

    @ViewInject(R.id.loan_apply_house_build_year_edit)
    private EditText loadApplyHouseBuildYearEdit;

    @ViewInject(R.id.loan_apply_house_build_area_edit)
    private EditText loadApplyHouseBuildAreaEdit;

    @ViewInject(R.id.loan_apply_house_owned_by_others_check_box)
    private CheckBox loadApplyHouseOwnedByOthersCheckBox;

    @ViewInject(R.id.loan_apply_house_is_mortgaged_check_box)
    private CheckBox loadApplyHouseIsMortgagedCheckBox;

    @ViewInject(R.id.loan_apply_house_borrower_is_owner_check_box)
    private CheckBox loadApplyHouseBorrowerIsOwnerCheckBox;

    @ViewInject(R.id.loan_apply_house_handing_time_edit)
    private EditText loadApplyHouseHandingTimeEdit;

    @ViewInject(R.id.loan_apply_borrower_name_edit)
    private EditText loadApplyBorrowerNameEdit;

    @ViewInject(R.id.loan_apply_borrower_phone_number_edit)
    private EditText loadApplyBorrowerPhoneNumberEdit;

    @ViewInject(R.id.loan_apply_borrower_amount_edit)
    private EditText loadApplyBorrowerAmountEdit;

    @ViewInject(R.id.loan_apply_borrower_marriage_spinner)
    private Spinner loadApplyBorrowerMarriageSpinner;

    @ViewInject(R.id.loan_apply_borrower_address_edit)
    private EditText loadApplyBorrowerAddressEdit;

    @ViewInject(R.id.loan_apply_borrower_detailed_address_edit)
    private EditText loadApplyBorrowerDetailedAddressEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        loadApplyFlipper.setInAnimation(this, R.anim.push_left_in);
        loadApplyHouseAddressEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DistrictActivity.actionStart(LoanApplyActivity.this, REQUEST_CODE_HOUSE_ADDRESS);
                }
            }
        });
        loadApplyHouseHandingTimeEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
        loadApplyBorrowerAddressEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
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
            if (loadApplyFlipper.getDisplayedChild() != 2) {
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
                    loadApplyHouseAddressEdit.setText(bundle.getString("district"));
                }
                if (requestCode == REQUEST_CODE_BORROWER_ADDRESS) {
                    loadApplyBorrowerAddressEdit.setText(bundle.getString("district"));
                }
            }
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        loadApplyHouseHandingTimeEdit.setText(new StringBuilder()
                .append(year)
                .append("-")
                .append((monthOfYear + 1) < 10 ? "0"
                        + (monthOfYear + 1) : (monthOfYear + 1))
                .append("-")
                .append((dayOfMonth < 10) ? "0" + dayOfMonth : dayOfMonth));
    }

    @Event(R.id.loan_apply_step_one_button)
    private void loadApplyStepOneButton(View view) {
        hideKeyboard();

        String houseAddress = loadApplyHouseAddressEdit.getText().toString();
        if (TextUtils.isEmpty(houseAddress)) {
            loadApplyHouseAddressEdit.setError("请输入抵押房产地址");
            return;
        }

        String housePropertyCard = loadApplyHousePropertyCardEdit.getText().toString();
        if (TextUtils.isEmpty(housePropertyCard)) {
            loadApplyHousePropertyCardEdit.setError("请输入借款人手机号");
            return;
        }

        String buildYear = loadApplyHouseBuildYearEdit.getText().toString();
        if (TextUtils.isEmpty(buildYear)) {
            loadApplyHouseBuildYearEdit.setError("请输入建筑年代");
            return;
        }

        String buildArea = loadApplyHouseBuildAreaEdit.getText().toString();
        if (TextUtils.isEmpty(buildArea)) {
            loadApplyHouseBuildAreaEdit.setError("请输入建筑面积");
            return;
        }

        String handingTime = loadApplyHouseHandingTimeEdit.getText().toString();
        if (TextUtils.isEmpty(handingTime)) {
            loadApplyHouseHandingTimeEdit.setError("请选择交房日期");
            return;
        }

        scrollView.scrollTo(0, 0);
        loadApplyFlipper.setDisplayedChild(1);
    }

    @Event(R.id.loan_apply_step_two_button)
    private void loadApplyStepTwoButton(View view) {
        hideKeyboard();

        String borrowerName = loadApplyBorrowerNameEdit.getText().toString();
        if (TextUtils.isEmpty(borrowerName)) {
            loadApplyBorrowerNameEdit.setError("请输入借款人姓名");
            return;
        }

        String borrowerPhoneNumber = loadApplyBorrowerPhoneNumberEdit.getText().toString();
        if (TextUtils.isEmpty(borrowerPhoneNumber)) {
            loadApplyBorrowerPhoneNumberEdit.setError("请输入借款人姓名");
            return;
        }
        else if (!CommonUtils.validatePhone(borrowerPhoneNumber)) {
            loadApplyBorrowerPhoneNumberEdit.setError("请输入正确的手机号码");
            return;
        }

        String borrowerAmount = loadApplyBorrowerAmountEdit.getText().toString();
        if (TextUtils.isEmpty(borrowerAmount)) {
            loadApplyBorrowerAmountEdit.setError("请输入借款金额");
            return;
        }

        String borrowerAddress = loadApplyBorrowerAddressEdit.getText().toString();
        if (TextUtils.isEmpty(borrowerAddress)) {
            loadApplyBorrowerAddressEdit.setError("请输入住宅所在地");
            return;
        }

        String borrowerDetailedAddress = loadApplyBorrowerDetailedAddressEdit.getText().toString();
        if (TextUtils.isEmpty(borrowerDetailedAddress)) {
            loadApplyBorrowerDetailedAddressEdit.setError("请输入详细地址");
            return;
        }

        scrollView.scrollTo(0, 0);
        loadApplyFlipper.setDisplayedChild(2);
    }
}
