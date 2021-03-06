package com.huachuang.palmtouchfinancial.backend.net.params;

import com.huachuang.palmtouchfinancial.GlobalParams;
import com.huachuang.palmtouchfinancial.backend.UserManager;

import org.xutils.http.RequestParams;
import org.xutils.http.annotation.HttpRequest;

import java.io.File;

/**
 * Created by Asuka on 2017/4/4.
 */

@HttpRequest(
        host = GlobalParams.SERVER_URL_HEAD,
        path = "Picture/UploadHeader")
public class UploadHeaderParams extends RequestParams {
    private long userID;
    private File header;

    public UploadHeaderParams(File header) {
        this.userID = UserManager.getUserID();
        this.header = header;
    }
}
