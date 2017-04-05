package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2017/3/30.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "UserManager/Register")
public class RegisterParams extends RequestParams {

    private String phoneNumber;
    private String invitationCode;
    private String recommenderID;
    private String password;

    public RegisterParams(String phoneNumber, String invitationCode, String recommenderID, String password) {
        this.phoneNumber = phoneNumber;
        this.invitationCode = invitationCode;
        this.recommenderID = recommenderID;
        this.password = password;
    }
}
