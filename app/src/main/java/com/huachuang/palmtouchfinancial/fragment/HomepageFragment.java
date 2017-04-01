package com.huachuang.palmtouchfinancial.fragment;

import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.CreditCardApplyActivity;
import com.huachuang.palmtouchfinancial.activity.MainActivity;
import com.huachuang.palmtouchfinancial.activity.MainMallActivity;
import com.huachuang.palmtouchfinancial.activity.WebViewActivity;
import com.huachuang.palmtouchfinancial.loader.AdImageLoader;
import com.youth.banner.Banner;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asuka on 2017/3/2.
 */

@ContentView(R.layout.fragment_homepage)
public class HomepageFragment extends BaseFragment {

    @ViewInject(R.id.ad_carousel_view)
    private Banner adCarouselView;

    @Override
    protected void initFragment() {
        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.ad_1);
        images.add(R.drawable.ad_2);
        images.add(R.drawable.ad_3);
        images.add(R.drawable.ad_4);
        images.add(R.drawable.ad_5);
        adCarouselView.setImages(images).setImageLoader(new AdImageLoader()).setDelayTime(3000).start();
    }

    @Event(value = R.id.main_apply_for_credit_card)
    private void creditCardApplyCardClicked(View view) {
        CreditCardApplyActivity.actionStart(this.getContext());
    }

    @Event(value = R.id.main_apply_for_loan)
    private void loanApplyClicked(View view) {
        showDefaultDialog();
    }

    @Event(value = R.id.main_mobile_payment)
    private void mobilePaymentClicked(View view) {
        showDefaultDialog();
    }

    @Event(value = R.id.main_share_app)
    private void shareAppClicked(View view) {
        ((MainActivity) getActivity()).switchFragment(1);
    }

    @Event(value = R.id.main_my_shopping_mall)
    private void myShoppingMallClicked(View view) {
        MainMallActivity.actionStart(getContext());
    }

    @Event(value = R.id.main_insurance)
    private void insuranceClicked(View view) {
        WebViewActivity.actionStart(getContext(), "http://baoxian.pingan.com/m/index.shtml", "平安保险");
    }

    @Event(value = R.id.main_etc)
    private void etcClicked(View view) {
        WebViewActivity.actionStart(getContext(), "http://light.weiche.me", "违章查询");
    }

    @Event(value = R.id.main_mobile_phone_recharge)
    private void mobilePhoneRechargeClicked(View view) {
        WebViewActivity.actionStart(getContext(), "http://chong.qq.com/promote/mobile/wx/index_21423.shtml", "手机充值");
    }

    @Event(value = R.id.main_movie_ticket)
    private void movieTicketClicked(View view) {
        WebViewActivity.actionStart(getContext(), "https://h5.m.taobao.com/app/movie/pages/index/index.html", "电影票");
    }

    @Event(value = R.id.main_fuel_card)
    private void fuelCardClicked(View view) {
        showDefaultDialog();
    }

    public void startAdCarousel() {
        adCarouselView.startAutoPlay();
    }

    public void stopAdCarousel() {
        adCarouselView.stopAutoPlay();
    }

    private void showDefaultDialog() {
        new MaterialDialog.Builder(getContext())
                .content("敬请期待")
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
}
