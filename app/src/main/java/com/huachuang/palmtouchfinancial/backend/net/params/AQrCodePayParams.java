package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.xutils.http.annotation.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.security.auth.Subject;

/**
 * Created by Asuka on 2017/5/22.
 */

@HttpRequest(
        host = GlobalParams.PAY_URL_HEAD,
        path = "cpay-acps-qrcode/qrcode")
public class AQrCodePayParams extends CommonPayHeaderParams {

    private String out_trade_no;
    private String seller_id;
    private String total_amount;
    private String discountable_amount;
    private String undiscountable_amount;
    private String buyer_logon_id;
    private String subject;
    private String body;
    private String goods_detail;
    private String operator_id;
    private String store_id;
    private String terminal_id;
    private String extend_params;
    private String timeout_express;
    private String royalty_info;
    private String sub_merchant;
    private String alipay_store_id;

    public AQrCodePayParams(
            String out_trade_no,
            int total_fee,
            String subject) {

        this.trans_code = "A00006";
        this.out_trade_no = out_trade_no;
        this.total_amount = String.valueOf(total_fee);
        this.undiscountable_amount = String.valueOf(total_fee);
        this.subject = subject;
        this.timeout_express = "30m";
    }
}
