package com.huachuang.palmtouchfinancial.backend.bean;

/**
 * Created by Asuka on 2017/2/27.
 */

public class User {

    enum UserLevel {USER, FIRST_AGENT, SECOND_AGENT, THIRD_AGENT}
    private String id;
    private String phoneNumber;
    private String name;
    private String password;
    private String gender;
    private UserLevel level;
    private String invitationCode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserLevel getLevel() {
        return level;
    }

    public void setLevel(UserLevel level) {
        this.level = level;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }
}
