package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2017/4/25.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "WalletManager/GetWalletBalanceRecords")
public class GetWalletBalanceRecords extends RequestParams {

    private long userID;

    public GetWalletBalanceRecords() {
        this.userID = UserManager.getUserID();
    }
}
