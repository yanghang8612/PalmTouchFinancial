package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by Asuka on 2017/5/22.
 */

@HttpRequest(
        host = GlobalParams.PAY_URL_HEAD,
        path = "cpay-acps-qrcode/qrcode")
public class WQrCodePayParams extends RequestParams {

    public WQrCodePayParams(
            String body,
            String out_trade_no,
            int total_fee) {

        try {
            JSONObject content = new JSONObject();
            content.put("body", body);
            content.put("detail", "");
            content.put("attach", "");
            content.put("out_trade_no", out_trade_no);
            content.put("fee_type", "CNY");
            content.put("total_fee", total_fee);
            content.put("spbill_create_ip", CommonUtils.getIPAddress());

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
            content.put("time_start", fmt.format(calendar.getTime()));
            calendar.add(Calendar.MINUTE, 30);
            content.put("time_expire", fmt.format(calendar.getTime()));

            content.put("goods_tag", "");
            content.put("trade_type", "APP");
            content.put("product_id", "");
            content.put("limit_pay", "");
            content.put("openid", "");

            JSONObject object = new JSONObject();
            object.put("header", CommonUtils.getCommonPayHeader("W00006"));
            object.put("content", content);
            object.put("mac", "");
            String params = object.toString();
            setBodyContent(params);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        setAsJsonContent(true);
    }
}
