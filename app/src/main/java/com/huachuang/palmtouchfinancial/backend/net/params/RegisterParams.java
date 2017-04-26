package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2017/3/30.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "UserManager/Register")
public class RegisterParams extends RequestParams {

    private String phoneNumber;
    private String password;
    private String identifyCode;
    private byte shareType;

    public RegisterParams(String phoneNumber, String password, String identifyCode) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.identifyCode = identifyCode;
        this.shareType = 0;
    }
}
