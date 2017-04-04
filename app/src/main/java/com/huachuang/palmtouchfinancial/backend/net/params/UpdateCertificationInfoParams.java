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
        path = "UserInfo/UpdateCertificationInfo")
public class UpdateCertificationInfoParams extends RequestParams {

    private long id;
    private long userID;
    private String userName;
    private String userSpell;
    private String userIdentityCard;
    private char userSex;

    public UpdateCertificationInfoParams(String userName, String userSpell, String userIdentityCard, char userSex) {
        this.id = UserManager.getCertificationInfo().getId();
        this.userID = UserManager.getUserID();
        this.userName = userName;
        this.userSpell = userSpell;
        this.userIdentityCard = userIdentityCard;
        this.userSex = userSex;
    }
}
