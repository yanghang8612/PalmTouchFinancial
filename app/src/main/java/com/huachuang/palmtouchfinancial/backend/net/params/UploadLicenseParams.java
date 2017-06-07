package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

import java.io.File;

/**
 * Created by Asuka on 2017/4/18.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "Picture/UploadLicense")
public class UploadLicenseParams extends RequestParams {
    private String phoneNumber;
    private File license;

    public UploadLicenseParams(String license) {
        this.phoneNumber = UserManager.getUserPhoneNumber();
        this.license = (license == null) ? null : new File(license);
    }
}
