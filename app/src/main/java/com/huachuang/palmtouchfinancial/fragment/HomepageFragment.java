package com.huachuang.palmtouchfinancial.fragment;

import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.CreditCardApplyActivity;
import com.huachuang.palmtouchfinancial.activity.LoanApplyActivity;
import com.huachuang.palmtouchfinancial.activity.MainActivity;
import com.huachuang.palmtouchfinancial.activity.MainMallActivity;
import com.huachuang.palmtouchfinancial.activity.MobilePayActivity;
import com.huachuang.palmtouchfinancial.activity.WebViewActivity;
import com.huachuang.palmtouchfinancial.backend.UserManager;
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

    @Event(R.id.main_apply_for_credit_card)
    private void creditCardApplyCardClicked(View view) {
        if (!UserManager.getCurrentUser().getCertificationState()) {
            new MaterialDialog.Builder(this.getContext())
                    .content("请先进行实名认证")
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
        else {
            CreditCardApplyActivity.actionStart(this.getContext());
        }
    }

    @Event(R.id.main_apply_for_loan)
    private void loanApplyClicked(View view) {
        LoanApplyActivity.actionStart(this.getContext());
    }

    @Event(R.id.main_mobile_pay)
    private void mobilePaymentClicked(View view) {
        if (!UserManager.getCurrentUser().getCertificationState()) {
            showDialog("请先进行实名认证");
        }
        else if (!UserManager.getCurrentUser().getDebitCardState()) {
            showDialog("请提交相关民生银行结算卡信息");
        }
        else {
            switch (UserManager.getCurrentUser().getMobilePayState()) {
                case 0:
                    showDialog("工作人员审核资料开户中，请耐心等待");
                    break;
                case 1:
                    MobilePayActivity.actionStart(this.getContext());
                    break;
                case 2:
                    showDialog("身份证照片不清晰或信息有误，请检查后重新提交");
                    break;
                case 3:
                    showDialog("营业执照不清晰或信息有误，请检查后重新提交");
                    break;
                case 4:
                    showDialog("结算卡照片不清晰或信息有误，请检查后重新提交");
                    break;
                case 5:
                    showDialog("开通移动支付需民生银行结算卡，请重新提交民生银行结算卡");
            }
        }
    }

    @Event(R.id.main_share_app)
    private void shareAppClicked(View view) {
        ((MainActivity) getActivity()).switchFragment(1);
    }

    @Event(R.id.main_my_shopping_mall)
    private void myShoppingMallClicked(View view) {
        MainMallActivity.actionStart(getContext());
    }

    @Event(R.id.main_insurance)
    private void insuranceClicked(View view) {
        WebViewActivity.actionStart(getContext(), "http://baoxian.pingan.com/m/index.shtml", "平安保险");
    }

    @Event(R.id.main_etc)
    private void etcClicked(View view) {
        WebViewActivity.actionStart(getContext(), "http://light.weiche.me", "违章查询");
    }

    @Event(R.id.main_mobile_phone_recharge)
    private void mobilePhoneRechargeClicked(View view) {
        WebViewActivity.actionStart(getContext(), "http://chong.qq.com/promote/mobile/wx/index_21423.shtml", "手机充值");
    }

    @Event(R.id.main_movie_ticket)
    private void movieTicketClicked(View view) {
        WebViewActivity.actionStart(getContext(), "https://h5.m.taobao.com/app/movie/pages/index/index.html", "电影票");
    }

    @Event(R.id.main_fuel_card)
    private void fuelCardClicked(View view) {
        WebViewActivity.actionStart(getContext(), "http://m.sinopecsales.com/webmobile/html/webhome.jsp", "中国石化");
    }

    public void startAdCarousel() {
        adCarouselView.startAutoPlay();
    }

    public void stopAdCarousel() {
        adCarouselView.stopAutoPlay();
    }

    private void showDialog(String message) {
        new MaterialDialog.Builder(getContext())
                .content(message)
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
