package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2015/12/21.
 */
@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "UserManage/Login")
public class LoginParams extends RequestParams {
    private String phoneNumber;
    private String password;

    public LoginParams(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }
}
