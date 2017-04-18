package com.huachuang.palmtouchfinancial.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.adapter.MainMallAdapter;
import com.huachuang.palmtouchfinancial.loader.AdImageLoader;
import com.youth.banner.Banner;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.activity_main_mall)
public class MainMallActivity extends BaseSwipeActivity {

    public static final String TAG = MainMallActivity.class.getSimpleName();

    public static void actionStart(Context context) {
        Intent intent = new Intent(context, MainMallActivity.class);
        context.startActivity(intent);
    }

    private MainMallAdapter adapter;

    @ViewInject(R.id.main_mall_toolbar)
    private Toolbar toolbar;

    @ViewInject(R.id.main_mall_goods_list)
    private RecyclerView goodsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        View header = getLayoutInflater().inflate(R.layout.header_main_mall, (ViewGroup) goodsList.getParent(), false);
        Banner headerAD = (Banner) header.findViewById(R.id.mall_header_ad);
        List<Integer> imageList = new ArrayList<>();
        imageList.add(R.drawable.mall_ad_1);
        imageList.add(R.drawable.mall_ad_2);
        headerAD.setImages(imageList).setImageLoader(new AdImageLoader()).setDelayTime(3000).start();

        adapter = new MainMallAdapter(this);
        adapter.openLoadAnimation();
        adapter.addHeaderView(header);

        goodsList.setLayoutManager(new GridLayoutManager(this, 2));
        //goodsList.addItemDecoration(new MainMallGoodsDecoration(1, R.color.divider));
        goodsList.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
