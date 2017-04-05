package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2017/4/5.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "UserManager/GetSubUser")
public class GetSubUserParams extends RequestParams {

    private long userID;

    public GetSubUserParams() {
        this.userID = UserManager.getUserID();
    }
}
