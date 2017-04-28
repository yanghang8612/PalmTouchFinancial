package com.huachuang.palmtouchfinancial.backend.bean;

import java.util.Date;

/**
 * Created by Asuka on 2017/4/26.
 */

public class ProfitRecord {

    private long id;
    private long userId;
    private byte type;
    private double amount;
    private Date time;

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

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
