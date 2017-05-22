package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.backend.UserManager;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2017/4/17.
 */

@HttpRequest(
        host = GlobalParams.PAY_URL_HEAD,
        path = "cpay-acps-qrcode/qrcode")
public class WScanPayParams extends CommonPayHeaderParams {

    private String body;
    private String detail;
    private String attach;
    private String out_trade_no;
    private int total_fee;
    private String fee_type;
    private String spbill_create_ip;
    private String goods_tag;
    private String limit_pay;
    private String auth_code;

    public WScanPayParams(
            String body,
            String out_trade_no,
            int total_fee,
            String auth_code) {

        spbill_create_ip = CommonUtils.getIPAddress();
    }
}
