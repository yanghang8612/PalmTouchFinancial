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
    private char userSex;
    private String userIdentityCard;
    private String userAddress;


    public UpdateCertificationInfoParams(String userName, String userSpell, char userSex, String userIdentityCard, String userAddress) {
        this.id = UserManager.getCertificationInfo().getId();
        this.userID = UserManager.getUserID();
        this.userName = userName;
        this.userSpell = userSpell;
        this.userSex = userSex;
        this.userIdentityCard = userIdentityCard;
        this.userAddress = userAddress;
    }
}
