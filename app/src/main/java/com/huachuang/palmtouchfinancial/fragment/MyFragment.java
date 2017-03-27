package com.huachuang.palmtouchfinancial.fragment;

import android.view.View;

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

    }
}
