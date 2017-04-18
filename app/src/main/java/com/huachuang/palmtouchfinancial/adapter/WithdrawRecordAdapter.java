package com.huachuang.palmtouchfinancial.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.bean.MallGoods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asuka on 2017/4/1.
 */

public class WithdrawRecordAdapter extends BaseQuickAdapter<MallGoods, BaseViewHolder> {

    private static List<MallGoods> recordList = new ArrayList<>();

    public WithdrawRecordAdapter() {
        super(R.layout.item_withdraw_record, recordList);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallGoods item) {

    }
}
