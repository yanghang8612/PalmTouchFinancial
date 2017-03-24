package com.huachuang.palmtouchfinancial.backend.bean;

/**
 * Created by Asuka on 2017/3/24.
 */

public class CreditCard {

    public String bankName;
    public String cardType;
    public String cardNumber;

    public CreditCard(String bankName, String cardType, String cardNumber) {
        this.bankName = bankName;
        this.cardType = cardType;
        this.cardNumber = cardNumber;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}
