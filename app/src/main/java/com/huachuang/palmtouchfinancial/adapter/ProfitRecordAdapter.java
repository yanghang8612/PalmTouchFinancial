package com.huachuang.palmtouchfinancial.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.bean.ProfitRecord;
import com.huachuang.palmtouchfinancial.backend.bean.RecommendRecord;

import java.util.List;

/**
 * Created by Asuka on 2017/3/13.
 */

public class ProfitRecordAdapter extends BaseQuickAdapter<ProfitRecord, BaseViewHolder> {

    public ProfitRecordAdapter(List<ProfitRecord> records) {
        super(R.layout.item_profit_record, records);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProfitRecord item) {

    }
}
