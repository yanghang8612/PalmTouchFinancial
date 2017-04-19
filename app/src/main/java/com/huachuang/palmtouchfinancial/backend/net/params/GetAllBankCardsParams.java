package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2017/4/4.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "BankCardManager/GetAllBankCards")
public class GetAllBankCardsParams extends RequestParams {

    private long userID;

    public GetAllBankCardsParams() {
        this.userID = UserManager.getUserID();
    }
}
