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
}
