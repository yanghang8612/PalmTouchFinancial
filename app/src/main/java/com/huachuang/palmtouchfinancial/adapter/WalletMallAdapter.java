package com.huachuang.palmtouchfinancial.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.bean.WalletMallGoods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asuka on 2017/3/24.
 */

public class WalletMallAdapter extends BaseQuickAdapter<WalletMallGoods, BaseViewHolder> {

    private static List<WalletMallGoods> goodsList = new ArrayList<>();

    static {
        goodsList.add(new WalletMallGoods());
        goodsList.add(new WalletMallGoods());
        goodsList.add(new WalletMallGoods());
    }

    public WalletMallAdapter() {
        super(R.layout.item_wallet_mall_goods, goodsList);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, WalletMallGoods goods) {
    }
}
