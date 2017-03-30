package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2017/3/30.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "UserManager/VerifyInvitationCode")
public class VerifyInvitationCodeParams extends RequestParams {
    private String invitationCode;

    public VerifyInvitationCodeParams(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
