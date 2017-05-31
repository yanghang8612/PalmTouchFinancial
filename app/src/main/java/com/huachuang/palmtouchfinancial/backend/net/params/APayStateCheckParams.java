package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.util.CommonUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2017/5/22.
 */

@HttpRequest(
        host = GlobalParams.PAY_URL_HEAD,
        path = "cpay-acps-qrcode/qrcode")
public class APayStateCheckParams extends RequestParams {

    public APayStateCheckParams(String out_trade_no) {
        try {
            JSONObject content = new JSONObject();
            content.put("out_trade_no", out_trade_no);
            content.put("trade_no", "");

            JSONObject object = new JSONObject();
            object.put("header", CommonUtils.getCommonPayHeader("A00002"));
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
