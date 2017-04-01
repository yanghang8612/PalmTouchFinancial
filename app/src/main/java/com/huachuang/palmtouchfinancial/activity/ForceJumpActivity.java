package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.fragment.FragmentFactory;
import com.huachuang.palmtouchfinancial.fragment.HomepageFragment;
import com.huachuang.palmtouchfinancial.loader.AdImageLoader;
import com.youth.banner.Banner;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_force_jump)
public class ForceJumpActivity extends BaseActivity {

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, ForceJumpActivity.class);
        context.startActivity(intent);
    }

    @ViewInject(R.id.force_jump_ads)
    private Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);

        List<Integer> images = new ArrayList<>();
        images.add(R.drawable.jump_1);
        images.add(R.drawable.jump_2);
        banner.setImages(images).setImageLoader(new AdImageLoader()).setDelayTime(1500).start();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((HomepageFragment) FragmentFactory.getInstanceByIndex(0)).startAdCarousel();
    }

    @Event(R.id.close)
    private void closeClicked(View view) {
        finish();
    }
}
