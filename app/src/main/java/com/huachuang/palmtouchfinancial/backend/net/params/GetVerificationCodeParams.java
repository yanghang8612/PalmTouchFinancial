package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

import java.net.URLEncoder;

/**
 * Created by Asuka on 2015/12/21.
 */

@HttpRequest(
        host = GlobalParams.YUNPIAN_URL_HEAD,
        path = "sms/tpl_single_send.json")
public class GetVerificationCodeParams extends RequestParams {
    private String apikey = GlobalParams.YUNPIAN_APP_ID;
    private String tpl_id = "1751388";
    private String tpl_value;
    private String mobile;

    public GetVerificationCodeParams(String mobile, String code) {
        this.mobile = mobile;
        this.tpl_value = "#code#=" + code + "&#hour#=5分钟";
    }
}
