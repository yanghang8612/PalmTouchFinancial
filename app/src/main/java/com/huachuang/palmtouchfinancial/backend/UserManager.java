package com.huachuang.palmtouchfinancial.backend;

import com.huachuang.palmtouchfinancial.backend.bean.User;
import com.huachuang.palmtouchfinancial.backend.bean.UserCertificationInfo;
import com.huachuang.palmtouchfinancial.backend.bean.UserDebitCard;
import com.huachuang.palmtouchfinancial.backend.bean.UserMobilePay;

/**
 * Created by Asuka on 2017/2/28.
 */

public class UserManager {

    private static User currentUser = null;
    private static UserCertificationInfo certificationInfo = null;
    private static UserDebitCard debitCardInfo = null;
    private static UserMobilePay mobilePay = null;

    private static String token;
    private static boolean loginState = false;

    private UserManager(){}

    public static void update() {

    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        UserManager.currentUser = currentUser;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        UserManager.token = token;
    }

    public static boolean getLoginState() {
        return loginState;
    }

    public static void setLoginState(boolean loginState) {
        UserManager.loginState = loginState;
    }

    public static long getUserID() {
        return (currentUser == null) ? -1 : currentUser.getUserId();
    }

    public static String getUserPhoneNumber() {
        return (currentUser == null) ? null : currentUser.getUserPhoneNumber();
    }

    public static UserCertificationInfo getCertificationInfo() {
        return certificationInfo;
    }

    public static void setCertificationInfo(UserCertificationInfo certificationInfo) {
        UserManager.certificationInfo = certificationInfo;
    }

    public static UserDebitCard getDebitCardInfo() {
        return debitCardInfo;
    }

    public static void setDebitCardInfo(UserDebitCard debitCardInfo) {
        UserManager.debitCardInfo = debitCardInfo;
    }

    public static UserMobilePay getMobilePay() {
        return mobilePay;
    }

    public static void setMobilePay(UserMobilePay mobilePay) {
        UserManager.mobilePay = mobilePay;
    }
}
