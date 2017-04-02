package com.huachuang.palmtouchfinancial.loader;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.huachuang.palmtouchfinancial.MyApplication;
import com.huachuang.palmtouchfinancial.R;
import com.imnjh.imagepicker.ImageLoader;

/**
 * Created by Asuka on 2017/4/2.
 */

public class HeaderImageLoader implements ImageLoader {

    private Context appContext;

    public HeaderImageLoader(Context appContext) {
        this.appContext = appContext;
    }

    @Override
    public void bindImage(ImageView imageView, Uri uri, int width, int height) {
        Glide.with(appContext).load(uri).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).override(width, height).dontAnimate().into(imageView);
    }

    @Override
    public void bindImage(ImageView imageView, Uri uri) {
        Glide.with(appContext).load(uri).placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher).dontAnimate().into(imageView);
    }

    @Override
    public ImageView createImageView(Context context) {
        ImageView imageView = new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return imageView;
    }

    @Override
    public ImageView createFakeImageView(Context context) {
        return new ImageView(context);
    }
}
