package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2017/6/12.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "UserManager/CommitFeedback")
public class CommitFeedback extends RequestParams {

    private long userID;
    private String message;

    public CommitFeedback(String message) {
        this.userID = UserManager.getUserID();
        this.message = message;
    }
}
