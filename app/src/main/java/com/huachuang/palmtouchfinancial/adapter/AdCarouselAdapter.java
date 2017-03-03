package com.huachuang.palmtouchfinancial.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by Asuka on 2017/3/3.
 */

public class AdCarouselAdapter extends PagerAdapter {

    List<ImageView> ads;
    int count;

    public AdCarouselAdapter(List<ImageView> ads) {
        this.ads = ads;
        count = ads.size();
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (ads.get(position % count).getParent() == null) {
            container.addView(ads.get(position % count));
        }
        return ads.get(position % count);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(ads.get(position % count));
    }
}
