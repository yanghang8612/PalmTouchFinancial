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
        path = "BankCardManager/AddBankCard")
public class AddBankCardParams extends RequestParams {

    private long userID;
    private String ownerName;
    private String cardNumber;
    private String cardType;
    private String bankName;
    private String phoneNumber;

    public AddBankCardParams(String ownerName, String cardNumber, String cardType, String bankName, String phoneNumber) {
        this.userID = UserManager.getUserID();
        this.ownerName = ownerName;
        this.cardNumber = cardNumber;
        this.cardType = cardType;
        this.bankName = bankName;
        this.phoneNumber = phoneNumber;
    }
}
