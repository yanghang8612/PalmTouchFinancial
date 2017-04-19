package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

import java.io.File;

/**
 * Created by Asuka on 2017/4/18.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "Picture/UploadDebitCard")
public class UploadDebitCardParams extends RequestParams {
    private String phoneNumber;
    private File front;
    private File back;

    public UploadDebitCardParams(String front, String back) {
        this.phoneNumber = UserManager.getUserPhoneNumber();
        this.front = (front == null) ? null : new File(front);
        this.back = (back ==  null) ? null : new File(back);
    }
}
