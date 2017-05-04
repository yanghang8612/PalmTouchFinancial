package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2017/4/17.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "Financial/ApplyCreditCard")
public class ApplyCreditCardParams extends RequestParams {

    private long userId;
    private int bankId;
    private String userName;
    private String userPhoneNumber;
    private String userCompany;

    public ApplyCreditCardParams(int bankId, String userName, String userPhoneNumber, String userCompany) {
        this.userId = UserManager.getUserID();
        this.bankId = bankId;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.userCompany = userCompany;
    }
}
