package com.huachuang.palmtouchfinancial.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.AboutUsActivity;
import com.huachuang.palmtouchfinancial.activity.MainActivity;
import com.huachuang.palmtouchfinancial.activity.UserAgreementActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

/**
 * Created by Asuka on 2017/3/2.
 */

@ContentView(R.layout.fragment_my)
public class MyFragment extends BaseFragment {

    @Override
    protected void initFragment() {

    }

    @Event(R.id.setting_share_app)
    private void shareAppClicked(View view) {
        ((MainActivity) getActivity()).switchFragment(1);
    }

    @Event(R.id.setting_check_update)
    private void checkUpdateClicked(View view) {
        showToast("已是最新版本");
    }

    @Event(R.id.setting_user_agreement)
    private void userAgreementClicked(View view) {
        UserAgreementActivity.actionStart(getContext());
    }

    @Event(R.id.setting_about_us)
    private void aboutUsClicked(View view) {
        AboutUsActivity.actionStart(getContext());
    }

    @Event(R.id.setting_hotline)
    private void hotlineClicked(View view) {
        new MaterialDialog.Builder(this.getContext())
                .content("客服电话 400-810-9910")
                .contentColorRes(R.color.black)
                .positiveText("呼叫")
                .negativeText("取消")
                .autoDismiss(false)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "400 810 9910"));
                        startActivity(intent);
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
}
