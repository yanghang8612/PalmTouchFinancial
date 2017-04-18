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
        path = "Picture/UploadIdentifyCard")
public class UploadIdentifyCardParams extends RequestParams {
    private String phoneNumber;
    private File front;
    private File back;
    private File handing;

    public UploadIdentifyCardParams(String front, String back, String handing) {
        this.phoneNumber = UserManager.getUserPhoneNumber();
        this.front = new File(front);
        this.back = new File(back);
        this.handing = new File(handing);
    }
}
