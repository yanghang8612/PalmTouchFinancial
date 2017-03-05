package com.huachuang.palmtouchfinancial.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
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
        position %= count;
        ViewParent parent = ads.get(position).getParent();
        if (parent != null) {
            ((ViewGroup) parent).removeView(ads.get(position));
        }
        container.addView(ads.get(position));
        return ads.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {}
}
