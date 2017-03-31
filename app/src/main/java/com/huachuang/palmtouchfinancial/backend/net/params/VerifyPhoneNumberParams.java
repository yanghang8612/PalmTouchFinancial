package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2017/3/30.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "UserManager/VerifyPhoneNumber")
public class VerifyPhoneNumberParams extends RequestParams {
    private String phoneNumber;

    public VerifyPhoneNumberParams(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
