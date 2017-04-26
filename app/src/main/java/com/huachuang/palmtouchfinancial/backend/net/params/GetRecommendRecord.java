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
        path = "UserManager/GetRecommendRecord")
public class GetRecommendRecord extends RequestParams {

    private long userID;

    public GetRecommendRecord() {
        this.userID = UserManager.getUserID();
    }
}
