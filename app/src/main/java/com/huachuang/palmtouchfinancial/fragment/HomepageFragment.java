package com.huachuang.palmtouchfinancial.fragment;

import android.view.View;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.activity.CreditCardApplyActivity;
import com.huachuang.palmtouchfinancial.activity.MainActivity;
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

    @Event(value = R.id.main_share_app)
    private void shareAppClicked(View view) {
        ((MainActivity) getActivity()).switchFragment(1);
    }

    @Event(value = R.id.main_movie_ticket)
    private void movieTicketClicked(View view) {
        WebViewActivity.actionStart(getContext(), "https://h5.m.taobao.com/app/movie/pages/index/index.html");
    }

    public void startAdCarousel() {
        adCarouselView.startAutoPlay();
    }

    public void stopAdCarousel() {
        adCarouselView.stopAutoPlay();
    }
}
