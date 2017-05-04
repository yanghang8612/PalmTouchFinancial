package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

/**
 * Created by Asuka on 2017/4/17.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "Financial/ApplyLoan")
public class ApplyLoanParams extends RequestParams {

    private long userId;
    private String houseAddress;
    private String housePropertyCard;
    private String houseLandSources;
    private String houseType;
    private String houseBuildYear;
    private String houseBuildArea;
    private boolean houseOwnedByOthers;
    private boolean houseIsMortgaged;
    private boolean houseBorrowerIsOwner;
    private String houseHandingTime;
    private String borrowerName;
    private String borrowerPhoneNumber;
    private String borrowerAmount;
    private String borrowerMarriage;
    private String borrowerAddress;
    private String borrowerDetailedAddress;

    public ApplyLoanParams(
            String houseAddress,
            String housePropertyCard,
            String houseLandSources,
            String houseType,
            String houseBuildYear,
            String houseBuildArea,
            boolean houseOwnedByOthers,
            boolean houseIsMortgaged,
            boolean houseBorrowerIsOwner,
            String houseHandingTime,
            String borrowerName,
            String borrowerPhoneNumber,
            String borrowerAmount,
            String borrowerMarriage,
            String borrowerAddress,
            String borrowerDetailedAddress) {

        this.userId = UserManager.getUserID();
        this.houseAddress = houseAddress;
        this.housePropertyCard = housePropertyCard;
        this.houseLandSources = houseLandSources;
        this.houseType = houseType;
        this.houseBuildYear = houseBuildYear;
        this.houseBuildArea = houseBuildArea;
        this.houseOwnedByOthers = houseOwnedByOthers;
        this.houseIsMortgaged = houseIsMortgaged;
        this.houseBorrowerIsOwner = houseBorrowerIsOwner;
        this.houseHandingTime = houseHandingTime;
        this.borrowerName = borrowerName;
        this.borrowerPhoneNumber = borrowerPhoneNumber;
        this.borrowerAmount = borrowerAmount;
        this.borrowerMarriage = borrowerMarriage;
        this.borrowerAddress = borrowerAddress;
        this.borrowerDetailedAddress = borrowerDetailedAddress;
    }
}
