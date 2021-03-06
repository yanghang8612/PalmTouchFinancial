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
        path = "UserInfo/UpdateDebitCard")
public class UpdateDebitCardParams extends RequestParams {

    private long id;
    private long userID;
    private String ownerName;
    private String cardNumber;
    private String cardType;
    private String headOffice;
    private String branch;
    private String province;

    public UpdateDebitCardParams(String ownerName, String cardNumber, String cardType, String headOffice, String branch, String province) {
        this.id = UserManager.getDebitCardInfo().getId();
        this.userID = UserManager.getUserID();
        this.ownerName = ownerName;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.headOffice = headOffice;
        this.branch = branch;
        this.province = province;
    }
}
