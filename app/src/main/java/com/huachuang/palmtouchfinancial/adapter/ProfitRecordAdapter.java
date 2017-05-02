package com.huachuang.palmtouchfinancial.adapter;

import android.os.LocaleList;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.huachuang.palmtouchfinancial.R;
import com.huachuang.palmtouchfinancial.backend.bean.ProfitRecord;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Asuka on 2017/3/13.
 */

public class ProfitRecordAdapter extends BaseQuickAdapter<ProfitRecord, BaseViewHolder> {

    public ProfitRecordAdapter(List<ProfitRecord> records) {
        super(R.layout.item_profit_record, records);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProfitRecord item) {
        helper.setText(R.id.profit_record_item_amount, (item.getAmount() > 0 ? "+ " : "- ") + Math.abs(item.getAmount()));
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd", Locale.getDefault());
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        Calendar time = Calendar.getInstance(TimeZone.getTimeZone("GMT+0"));
        time.setTime(item.getTime());
        Calendar current = Calendar.getInstance();
        int diff = current.get(Calendar.DAY_OF_YEAR) - time.get(Calendar.DAY_OF_YEAR);
        if (diff == 0) {
            helper.setText(R.id.profit_record_item_time_up, "今天");
            helper.setText(R.id.profit_record_item_time_down, timeFormat.format(item.getTime()));
        }
        else if (diff == 1) {
            helper.setText(R.id.profit_record_item_time_up, "昨天");
            helper.setText(R.id.profit_record_item_time_down, timeFormat.format(item.getTime()));
        }
        else {
            switch (time.get(Calendar.DAY_OF_WEEK)) {
                case Calendar.MONDAY:
                    helper.setText(R.id.profit_record_item_time_up, "周一");
                    break;
                case Calendar.TUESDAY:
                    helper.setText(R.id.profit_record_item_time_up, "周二");
                    break;
                case Calendar.WEDNESDAY:
                    helper.setText(R.id.profit_record_item_time_up, "周三");
                    break;
                case Calendar.THURSDAY:
                    helper.setText(R.id.profit_record_item_time_up, "周四");
                    break;
                case Calendar.FRIDAY:
                    helper.setText(R.id.profit_record_item_time_up, "周五");
                    break;
                case Calendar.SATURDAY:
                    helper.setText(R.id.profit_record_item_time_up, "周六");
                    break;
                case Calendar.SUNDAY:
                    helper.setText(R.id.profit_record_item_time_up, "周日");
                    break;
            }
            helper.setText(R.id.profit_record_item_time_down, dateFormat.format(item.getTime()));
        }
        switch (item.getType()) {
            case 1:
                helper.setText(R.id.profit_record_item_description, "分享有礼：一级注册用户");
                break;
            case 2:
                helper.setText(R.id.profit_record_item_description, "分享有礼：二级注册用户");
                break;
            case 3:
                helper.setText(R.id.profit_record_item_description, "分享有礼：三级注册用户");
                break;
        }
    }
}
