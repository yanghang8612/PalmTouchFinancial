package com.huachuang.palmtouchfinancial.backend.bean;

import java.util.Date;

/**
 * Created by Asuka on 2017/4/26.
 */

public class RecommendRecord {

    private String phoneNumber;
    private short type;
    private Date recommendTime;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public Date getRecommendTime() {
        return recommendTime;
    }

    public void setRecommendTime(Date recommendTime) {
        this.recommendTime = recommendTime;
    }
}
