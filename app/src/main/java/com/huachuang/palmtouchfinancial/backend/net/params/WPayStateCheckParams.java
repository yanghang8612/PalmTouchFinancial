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
public class WPayStateCheckParams extends CommonPayHeaderParams {

    private String transaction_id;
    private String out_trade_no;

    public WPayStateCheckParams(String out_trade_no) {
        this.trans_code = "W00002";
        this.out_trade_no = out_trade_no;
    }
}
