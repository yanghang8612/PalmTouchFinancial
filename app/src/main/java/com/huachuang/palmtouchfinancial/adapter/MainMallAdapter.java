package com.huachuang.palmtouchfinancial.adapter;

import android.content.Context;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.bean.MallGoods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asuka on 2017/3/24.
 */

public class MainMallAdapter extends BaseQuickAdapter<MallGoods, BaseViewHolder> {

    private static List<MallGoods> mallGoodsList = new ArrayList<>();

    static {
        mallGoodsList.add(new MallGoods("MPos_D180", 130, "goods_1", ""));
        mallGoodsList.add(new MallGoods("百富 S90", 600, "goods_2", ""));
        mallGoodsList.add(new MallGoods("百富 S910", 550, "goods_3", ""));
        mallGoodsList.add(new MallGoods("外币机", 320, "goods_4", ""));
        mallGoodsList.add(new MallGoods("智能POS机 K9", 1500, "goods_5", ""));
    }

    private Context context;

    public MainMallAdapter(Context context) {
        super(R.layout.item_mall_goods, mallGoodsList);
        this.context = context;
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, MallGoods mallGoods) {
        baseViewHolder.setImageResource(R.id.goods_image,
                context.getResources().getIdentifier(mallGoods.getImagePath(), "drawable", "com.huachuang.palmtouchfinancial"))
                .setText(R.id.goods_name, mallGoods.getName())
                .setText(R.id.goods_price, "￥" + mallGoods.getPrice());
        if (baseViewHolder.getLayoutPosition() % 2 == 1) {
            baseViewHolder.setVisible(R.id.goods_vertical, true);
        }
    }
}
