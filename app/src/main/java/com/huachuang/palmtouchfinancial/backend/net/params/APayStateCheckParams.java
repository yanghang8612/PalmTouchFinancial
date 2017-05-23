package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2017/5/22.
 */

@HttpRequest(
        host = GlobalParams.PAY_URL_HEAD,
        path = "cpay-acps-qrcode/qrcode")
public class APayStateCheckParams extends RequestParams {

    private String out_trade_no;
    private String trade_no;

    public APayStateCheckParams(String out_trade_no) {
        super("A00002");
        this.out_trade_no = out_trade_no;
    }
}
