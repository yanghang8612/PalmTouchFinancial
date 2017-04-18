package com.huachuang.palmtouchfinancial.loader;

import android.app.Activity;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.huachuang.palmtouchfinancial.R;
import com.lzy.imagepicker.loader.ImageLoader;

import java.io.File;

/**
 * Created by Asuka on 2017/4/2.
 */

public class HeaderImageLoader implements ImageLoader {

    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)                             //配置上下文
                .load(Uri.fromFile(new File(path)))      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                .error(R.mipmap.default_image)           //设置错误图片
                .placeholder(R.mipmap.default_image)     //设置占位图片
                .diskCacheStrategy(DiskCacheStrategy.ALL)//缓存全尺寸
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }

//    private Context appContext;
//
//    public HeaderImageLoader(Context appContext) {
//        this.appContext = appContext;
//    }
//
//    @Override
//    public void bindImage(ImageView imageView, Uri uri, int width, int height) {
//        Glide.with(appContext).load(uri).placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher).override(width, height).dontAnimate().into(imageView);
//    }
//
//    @Override
//    public void bindImage(ImageView imageView, Uri uri) {
//        Glide.with(appContext).load(uri).placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher).dontAnimate().into(imageView);
//    }
//
//    @Override
//    public ImageView createImageView(Context context) {
//        ImageView imageView = new ImageView(context);
//        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//        return imageView;
//    }
//
//    @Override
//    public ImageView createFakeImageView(Context context) {
//        return new ImageView(context);
//    }
}
