package com.huachuang.palmtouchfinancial.fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.AgentManagerActivity;
import com.huachuang.palmtouchfinancial.activity.ChangePasswordActivity;
import com.huachuang.palmtouchfinancial.activity.DebitCardActivity;
import com.huachuang.palmtouchfinancial.activity.LoginActivity;
import com.huachuang.palmtouchfinancial.activity.PersonalInfoActivity;
import com.huachuang.palmtouchfinancial.activity.ProfitActivity;
import com.huachuang.palmtouchfinancial.activity.RateActivity;
import com.huachuang.palmtouchfinancial.activity.RealNameInfoActivity;
import com.huachuang.palmtouchfinancial.activity.RegisterVIPActivity;
import com.huachuang.palmtouchfinancial.activity.SettingActivity;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Asuka on 2017/3/2.
 */

@ContentView(R.layout.fragment_my)
public class MyFragment extends BaseFragment {

    @ViewInject(R.id.my_fragment_header_image_view)
    private CircleImageView headerImageView;

    @ViewInject(R.id.my_fragment_header_phone_number)
    private TextView phoneNumberView;

    @ViewInject(R.id.my_fragment_header_user_type_common)
    private CircleImageView commonUserView;

    @ViewInject(R.id.my_fragment_header_user_type_vip)
    private CircleImageView vipUserView;

    @ViewInject(R.id.my_fragment_header_user_type_agent)
    private CircleImageView agentLevelView;

    @ViewInject(R.id.my_fragment_header_name)
    private TextView nameView;

    @ViewInject(R.id.my_vip_register)
    private RelativeLayout vipRegisterLayout;

    @ViewInject(R.id.my_vip_register_divider)
    private LinearLayout vipRegisterDivider;

    @ViewInject(R.id.my_agent_manager)
    private RelativeLayout agentManagerLayout;

    @ViewInject(R.id.my_agent_manager_divider)
    private LinearLayout agentManagerDivider;

    @Override
    protected void initFragment() {
        refreshNavHeaderView();
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshNavHeaderView();
    }

    @Event(R.id.my_fragment_header)
    private void myFragmentHeaderClicked(View view) {
        PersonalInfoActivity.actionStart(getContext());
    }

    @Event(R.id.my_vip_register)
    private void myVipRegisterClicked(View view) {
        RegisterVIPActivity.actionStart(getContext());
    }

    @Event(R.id.my_real_name_information)
    private void myRealNameInformationClicked(View view) {
        RealNameInfoActivity.actionStart(getContext());
    }

    @Event(R.id.my_change_debit_card)
    private void myChangeDebitCardClicked(View view) {
        DebitCardActivity.actionStart(getContext());
    }

    @Event(R.id.my_rate)
    private void myRateClicked(View view) {
        RateActivity.actionStart(getContext());
    }

    @Event(R.id.my_profit_share)
    private void myProfitShareClicked(View view) {
        ProfitActivity.actionStart(getContext());
    }

    @Event(R.id.my_agent_manager)
    private void myAgentManagerClicked(View view) {
        AgentManagerActivity.actionStart(getContext());
    }

    @Event(R.id.my_setting)
    private void mySettingClicked(View view) {
        SettingActivity.actionStart(getContext());
    }

    @Event(R.id.my_change_password)
    private void myChangePasswordClicked(View view) {
        ChangePasswordActivity.actionStart(getContext());
    }

    @Event(R.id.my_sign_out)
    private void mySignOutClicked(View view) {
        new MaterialDialog.Builder(getContext())
                .content("确认退出登录吗?")
                .contentColorRes(R.color.black)
                .positiveText("确认")
                .negativeText("取消")
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                        SharedPreferences defaultPref = getActivity().getSharedPreferences(DEFAULT_PRE, Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = defaultPref.edit();
                        editor.remove("phoneNumber");
                        editor.remove("password");
                        editor.apply();
                        LoginActivity.actionStart(getContext());
                        getActivity().finish();
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

    private void refreshNavHeaderView() {
        if (UserManager.getCurrentUser().getUserType() == 0) {
            if (UserManager.getCurrentUser().isVip()) {
                commonUserView.setVisibility(View.GONE);
                vipUserView.setVisibility(View.VISIBLE);
            }
        }
        else {
            commonUserView.setVisibility(View.GONE);
            switch (UserManager.getCurrentUser().getUserType()) {
                case 1:
                    agentLevelView.setImageResource(R.drawable.ic_level_one);
                    break;
                case 2:
                    agentLevelView.setImageResource(R.drawable.ic_level_two);
                    break;
                case 3:
                    agentLevelView.setImageResource(R.drawable.ic_level_three);
                    break;
            }
            agentLevelView.setVisibility(View.VISIBLE);
        }

        if (UserManager.getCurrentUser().getHeaderState()) {
            Glide.with(this)
                    .load(CommonUtils.getHeaderUrl())
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(headerImageView);
        }

        phoneNumberView.setText(UserManager.getCurrentUser().getUserPhoneNumber());

        if (UserManager.getCurrentUser().getCertificationState()) {
            nameView.setText(UserManager.getCertificationInfo().getUserName());
        }
        else {
            nameView.setText("<请实名认证>");
        }

        if (UserManager.getCurrentUser().getUserType() == 0) {
            agentManagerLayout.setVisibility(View.GONE);
            agentManagerDivider.setVisibility(View.GONE);
            if (!UserManager.getCurrentUser().isVip()) {
                vipRegisterLayout.setVisibility(View.VISIBLE);
                vipRegisterDivider.setVisibility(View.VISIBLE);
            }
        }
    }
}
