package com.huachuang.palmtouchfinancial.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.bean.WalletMallGoods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asuka on 2017/4/1.
 */

public class WithdrawRecordAdapter extends BaseQuickAdapter<WalletMallGoods, BaseViewHolder> {

    private static List<WalletMallGoods> recordList = new ArrayList<>();

    public WithdrawRecordAdapter() {
        super(R.layout.item_withdraw_record, recordList);
    }

    @Override
    protected void convert(BaseViewHolder helper, WalletMallGoods item) {

    }
}
