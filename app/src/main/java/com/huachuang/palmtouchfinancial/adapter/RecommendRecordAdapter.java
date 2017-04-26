package com.huachuang.palmtouchfinancial.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.bean.RecommendRecord;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Asuka on 2017/3/24.
 */

public class RecommendRecordAdapter extends BaseQuickAdapter<RecommendRecord, BaseViewHolder> {

    public RecommendRecordAdapter(List<RecommendRecord> records) {
        super(R.layout.item_recommend_record, records);
    }

    @Override
    protected void convert(BaseViewHolder baseViewHolder, RecommendRecord record) {
        baseViewHolder.setText(R.id.recommend_record_item_phone_number, record.getPhoneNumber())
                .setText(R.id.recommend_record_item_time, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(record.getRecommendTime()));
        switch (record.getType()) {
            case 0:
                baseViewHolder.setImageResource(R.id.recommend_record_item_type_image, R.drawable.ic_app);
                baseViewHolder.setText(R.id.recommend_record_item_type, "通过APP客户端注册。");
                break;
            case 1:
                baseViewHolder.setImageResource(R.id.recommend_record_item_type_image, R.drawable.ic_wechat);
                baseViewHolder.setText(R.id.recommend_record_item_type, "通过微信好友分享注册。");
                break;
            case 2:
                baseViewHolder.setImageResource(R.id.recommend_record_item_type_image, R.drawable.ic_timeline);
                baseViewHolder.setText(R.id.recommend_record_item_type, "通过微信朋友圈分享注册。");
                break;
            case 3:
                baseViewHolder.setImageResource(R.id.recommend_record_item_type_image, R.drawable.ic_qq);
                baseViewHolder.setText(R.id.recommend_record_item_type, "通过QQ好友分享注册。");
                break;
            case 4:
                baseViewHolder.setImageResource(R.id.recommend_record_item_type_image, R.drawable.ic_qzone);
                baseViewHolder.setText(R.id.recommend_record_item_type, "通过QQ空间分享注册。");
                break;
            case 5:
                baseViewHolder.setImageResource(R.id.recommend_record_item_type_image, R.drawable.ic_qr_code);
                baseViewHolder.setText(R.id.recommend_record_item_type, "通过APP客户端扫码分享注册。");
                break;
        }
    }
}
