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

import javax.security.auth.Subject;

/**
 * Created by Asuka on 2017/5/22.
 */

@HttpRequest(
        host = GlobalParams.PAY_URL_HEAD,
        path = "cpay-acps-qrcode/qrcode")
public class AQrCodePayParams extends RequestParams {

    public AQrCodePayParams(
            String out_trade_no,
            int total_fee,
            String subject) {

        try {
            JSONObject content = new JSONObject();
            content.put("out_trade_no", out_trade_no);
            content.put("seller_id", "");
            content.put("total_amount", String.valueOf(total_fee));
            content.put("discountable_amount", "0");
            content.put("undiscountable_amount", String.valueOf(total_fee));
            content.put("buyer_logon_id", "");
            content.put("subject", subject);
            content.put("body", "");
            content.put("goods_detail", "");
            content.put("operator_id", "");
            content.put("store_id", "");
            content.put("terminal_id", "");
            content.put("extend_params", "");
            content.put("timeout_express", "30m");
            content.put("royalty_info", "");
            content.put("sub_merchant", "");
            content.put("alipay_store_id", "");

            JSONObject object = new JSONObject();
            object.put("header", CommonUtils.getCommonPayHeader("A00006"));
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
