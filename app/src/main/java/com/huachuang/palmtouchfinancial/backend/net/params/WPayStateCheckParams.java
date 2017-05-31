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

/**
 * Created by Asuka on 2017/5/22.
 */

@HttpRequest(
        host = GlobalParams.PAY_URL_HEAD,
        path = "cpay-acps-qrcode/qrcode")
public class WPayStateCheckParams extends RequestParams {

    public WPayStateCheckParams(String out_trade_no) {
        try {
            JSONObject content = new JSONObject();
            content.put("transaction_id", "");
            content.put("out_trade_no", out_trade_no);

            JSONObject object = new JSONObject();
            object.put("header", CommonUtils.getCommonPayHeader("W00002"));
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
