package com.huachuang.palmtouchfinancial.fragment;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.loader.AdImageLoader;
import com.youth.banner.Banner;

import org.xutils.view.annotation.ContentView;
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
        images.add(R.drawable.ad_one);
        images.add(R.drawable.ad_two);
        adCarouselView.setImages(images).setImageLoader(new AdImageLoader()).start();
    }
}
