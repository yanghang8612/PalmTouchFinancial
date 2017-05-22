package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.xutils.http.annotation.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Asuka on 2017/5/22.
 */

@HttpRequest(
        host = GlobalParams.PAY_URL_HEAD,
        path = "cpay-acps-qrcode/qrcode")
public class WQrCodePayParams extends CommonPayHeaderParams {

    private String body;
    private String detail;
    private String attach;
    private String out_trade_no;
    private String fee_type;
    private int total_fee;
    private String spbill_create_ip;
    private String time_start;
    private String time_expire;
    private String goods_tag;
    private String trade_type;
    private String product_id;
    private String limit_pay;
    private String openid;

    public WQrCodePayParams(
            String body,
            String out_trade_no,
            int total_fee) {

        this.trans_code = "W00006";
        this.body = body;
        this.out_trade_no = out_trade_no;
        this.total_fee = total_fee;
        this.spbill_create_ip = CommonUtils.getIPAddress();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        this.time_start = fmt.format(calendar.getTime());
        calendar.add(Calendar.MINUTE, 30);
        this.time_expire = fmt.format(calendar.getTime());
        this.trade_type = "APP";
        setAsJsonContent(true);
    }
}
