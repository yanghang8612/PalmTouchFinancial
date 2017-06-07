package com.huachuang.palmtouchfinancial.backend.bean;

/**
 * Created by Asuka on 2017/6/6.
 */

public class UserMobilePay {

    private long id;
    private long userId;
    private String mid;
    private String key;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getMid() {
        return mid;
    }

    public void setMid(String mid) {
        this.mid = mid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
