package com.huachuang.palmtouchfinancial.backend.bean;

import java.util.Date;

/**
 * Created by Asuka on 2017/2/27.
 */

public class User {

    private long userId;
    private String userPhoneNumber;
    private String userPassword;
    private short userType;
    private boolean isVip;
    private String invitationCode;
    private long superiorUserId;
    private Date registerTime;
    private Date lastLoginTime;
    private boolean certificationState;
    private boolean debitCardState;
    private boolean headerState;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public short getUserType() {
        return userType;
    }

    public void setUserType(short userType) {
        this.userType = userType;
    }

    public boolean isVip() {
        return isVip;
    }

    public void setVip(boolean vip) {
        isVip = vip;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public long getSuperiorUserId() {
        return superiorUserId;
    }

    public void setSuperiorUserId(long superiorUserId) {
        this.superiorUserId = superiorUserId;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public boolean getCertificationState() {
        return certificationState;
    }

    public void setCertificationState(boolean certificationState) {
        this.certificationState = certificationState;
    }

    public boolean getDebitCardState() {
        return debitCardState;
    }

    public void setDebitCardState(boolean debitCardState) {
        this.debitCardState = debitCardState;
    }

    public boolean getHeaderState() {
        return headerState;
    }

    public void setHeaderState(boolean headerState) {
        this.headerState = headerState;
    }
}
