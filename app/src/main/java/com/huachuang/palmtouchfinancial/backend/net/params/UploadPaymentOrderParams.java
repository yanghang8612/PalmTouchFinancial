package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

import java.util.Date;

/**
 * Created by Asuka on 2017/4/17.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "Financial/UploadPaymentOrder")
public class UploadPaymentOrderParams extends RequestParams {

    private long userId;
    private String orderTransaction;
    private byte orderType;
    private double orderAmount;
    private byte orderState;
    private String paymentID;

    public UploadPaymentOrderParams(
            String orderTransaction,
            byte orderType,
            double orderAmount,
            byte orderState,
            String paymentID) {

        this.userId = UserManager.getUserID();
        this.orderTransaction = orderTransaction;
        this.orderType = orderType;
        this.orderAmount = orderAmount;
        this.orderState = orderState;
        this.paymentID = paymentID;
    }
}
